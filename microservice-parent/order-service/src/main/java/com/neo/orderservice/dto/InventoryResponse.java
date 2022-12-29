package com.neo.orderservice.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventoryResponse {
    private String skuCode;
    private Boolean isInStock;
}
