package com.neo.orderservice.service;

import com.neo.orderservice.dto.OrderLineItemDto;
import com.neo.orderservice.dto.OrderRequest;
import com.neo.orderservice.model.Order;
import com.neo.orderservice.model.OrderLineItems;
import com.neo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemDtoList()
                .stream().map(orderLineItemDto -> mapFromDto(orderLineItemDto)).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);

        orderRepository.save(order);
    }

    private OrderLineItems mapFromDto(OrderLineItemDto orderLineItemDto){
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        return orderLineItems;
    }

}
