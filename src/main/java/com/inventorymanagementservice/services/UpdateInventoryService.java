package com.inventorymanagementservice.services;

import com.inventorymanagementservice.models.ErrorResponse;
import com.inventorymanagementservice.models.ProductModels.ProductInfo;
import com.inventorymanagementservice.models.request.SubmitOrderRequest;
import com.inventorymanagementservice.models.response.SuccessResponse;
import com.inventorymanagementservice.models.response.UpdateInventoryResponse;
import com.inventorymanagementservice.repositories.ProductDataRepository;
import com.inventorymanagementservice.schema.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UpdateInventoryService {

    @Autowired
    ProductDataRepository productDataRepository;
    public UpdateInventoryResponse updateInventory(SubmitOrderRequest request) {

        // Validate request parameters
        for (ProductInfo product : request.getProductList()) {
            if (!productDataRepository.existsById(product.getId())) {
                return new UpdateInventoryResponse(
                        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                product.getId() + " not found in inventory"));
            }
            if (productDataRepository.findById(product.getId()).get().getQuantity() < product.getRequestedQuantity())
            {
                return new UpdateInventoryResponse(
                        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                product.getId() + " is out of stock"));
            }


        }
        for (ProductInfo product : request.getProductList()) {
            ProductData currRecord = productDataRepository.findById(product.getId()).get();
            int oldQty = currRecord.getQuantity();
            currRecord.setQuantity(oldQty - product.getRequestedQuantity());
            productDataRepository.save(currRecord);
        }

        return new UpdateInventoryResponse(new SuccessResponse("Update complete"));

    }
}
