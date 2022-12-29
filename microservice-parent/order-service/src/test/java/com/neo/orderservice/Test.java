package com.neo.orderservice;

import com.neo.orderservice.dto.InventoryResponse;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Test {
    public static void main(String[] args) {
        String [] skuCodeArr = {"A", "B", "C"};
        InventoryResponse[] skuCodeInStockArr = {new InventoryResponse("A", false), new InventoryResponse("B", true), new InventoryResponse("C", false
        )};
        Boolean rs = Arrays.stream(skuCodeInStockArr).allMatch(inventoryResponse -> Boolean.FALSE.equals(inventoryResponse.getIsInStock()));
        System.out.println(rs);
    }
}
