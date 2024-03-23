package com.inventorymanagementservice.services;

import com.inventorymanagementservice.schema.ProductData;
import com.inventorymanagementservice.models.ProductModels.ProductInfo;
import com.inventorymanagementservice.models.ErrorResponse;
import com.inventorymanagementservice.repositories.ProductDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductDataRepository productDataRepository;
    public ProductInfo getProductQuote(String id, int requestedQuantity) {

        // Validate request parameters
        if (id == null || id.isEmpty() || requestedQuantity < 0) {
            ProductInfo product = new ProductInfo();
            product.setErrorResponse(new ErrorResponse("500","Invalid request"));
        }

        // Fetch product from Database
        Optional<ProductData> productDataFromDb = productDataRepository.findById(id);

        ProductData productData = productDataFromDb.orElse(null);

        if (productData == null) {
            ProductInfo product = new ProductInfo();
            product.setErrorResponse(new ErrorResponse("500","No product found"));
            return product;
        }

        if (productData.getQuantity() < requestedQuantity) {
            ProductInfo product = new ProductInfo();
            product.setErrorResponse(new ErrorResponse("500","Product unavailable"));
            return product;
        }


        return ProductInfo.
                builder()
                .setId(id)
                .setName(productData.getName())
                .setPrice(productData.getPrice())
                .setRequestedQuantity(requestedQuantity)
                .build();
    }
}
