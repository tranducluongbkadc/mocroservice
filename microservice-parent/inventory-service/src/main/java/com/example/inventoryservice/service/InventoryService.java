package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryRequestDto;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCodeList){
            List<String> skuCodeInStockList = inventoryRepository.findBySkuCodeIn(skuCodeList).stream().map(inventory -> inventory.getSkuCode()).collect(Collectors.toList());
            List<InventoryResponse> inventoryResponseList = new ArrayList<>();
            for (String skuCode: skuCodeList){
                if(skuCodeInStockList.contains(skuCode)) inventoryResponseList.add(new InventoryResponse(skuCode, true));
                else inventoryResponseList.add(new InventoryResponse(skuCode, false));
            }
            return inventoryResponseList;

//        return inventoryRepository.findBySkuCodeIn(skuCodeList).stream()
//                .map(inventory ->
//                    InventoryResponse.builder()
//                            .skuCode(inventory.getSkuCode())
//                            .isInStock(inventory.getQuantity() >0)
//                            .build()
//                ).collect(Collectors.toList());
    }

    public void createInventory(InventoryRequestDto inventoryRequestDto){
        inventoryRepository.save(mapFromDto(inventoryRequestDto));
    }

    private Inventory mapFromDto(InventoryRequestDto inventoryRequestDto){
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequestDto.getSkuCode());
        inventory.setQuantity(inventoryRequestDto.getQuantity());
        return inventory;
    }
}
