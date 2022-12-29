package com.neo.orderservice.controller;

import com.neo.orderservice.dto.OrderRequest;
import com.neo.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder (@RequestBody OrderRequest orderRequest){
         return orderService.placeOrder(orderRequest);

    }
}
