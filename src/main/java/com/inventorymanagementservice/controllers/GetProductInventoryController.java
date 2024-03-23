package com.inventorymanagementservice.controllers;

import com.inventorymanagementservice.models.ProductModels.ProductInfo;
import com.inventorymanagementservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProductInventoryController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/{productId}")
    private ResponseEntity<ProductInfo>
    getProductQuote(@PathVariable("productId") String productId,
                    @RequestParam(value = "quantity",
                            required = false, defaultValue = "1") int quantity) {

        ProductInfo productDataResponse =
                productService.getProductQuote(productId, quantity);

        return ResponseEntity.status(HttpStatus.OK).body(productDataResponse);
    }
}
