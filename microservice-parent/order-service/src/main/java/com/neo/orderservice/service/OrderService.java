package com.neo.orderservice.service;

import com.neo.orderservice.dto.InventoryResponse;
import com.neo.orderservice.dto.OrderLineItemDto;
import com.neo.orderservice.dto.OrderRequest;
import com.neo.orderservice.model.Order;
import com.neo.orderservice.model.OrderLineItems;
import com.neo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemDtoList()
                .stream().map(orderLineItemDto -> mapFromDto(orderLineItemDto)).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        if (checkInStock(orderRequest)) {
            orderRepository.save(order);
            return "Order Place Successfully !!!";
        }
        else return "Product is not in stock, please try again later !!!";
    }

    private OrderLineItems mapFromDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        return orderLineItems;
    }


    /**
     * check if product in stock first:
     * + get list skuCode
     * + call API to get SkuCode in Stock
     */
    private boolean checkInStock(OrderRequest orderRequest){
        List<String> skuCodeList = orderRequest.getOrderLineItemDtoList().stream()
                .map(orderLineItemDto -> orderLineItemDto.getSkuCode())
                .collect(Collectors.toList());

        InventoryResponse[] inventoryResponsesArr = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse [].class)
                .block();
        for (InventoryResponse item: inventoryResponsesArr){
            System.out.println(item.toString());
        }

        return Arrays.stream(inventoryResponsesArr).allMatch(inventoryResponse -> Boolean.TRUE.equals( inventoryResponse.getIsInStock()));
    }

}
