package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryRequestDto;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * type1: /api/inventory/item1,item2
     * type2: /api/inventory?sku-code=item1&sku-code=item2&sku-code=item3
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCodeList){
        return inventoryService.isInStock(skuCodeList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInventory(@RequestBody InventoryRequestDto inventoryRequestDto){
        inventoryService.createInventory(inventoryRequestDto);
        return "Inventory is created !!!";
    }
}
