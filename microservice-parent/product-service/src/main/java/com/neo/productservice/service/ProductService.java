package com.neo.productservice.service;

import com.neo.productservice.dto.ProductDto;
import com.neo.productservice.dto.ProductResponse;
import com.neo.productservice.model.Product;
import com.neo.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getName());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        return  productList.stream().map(product ->mapToProductResponse(product)).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
