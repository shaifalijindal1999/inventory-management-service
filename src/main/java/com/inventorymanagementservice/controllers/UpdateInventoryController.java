package com.inventorymanagementservice.controllers;

import com.inventorymanagementservice.models.request.SubmitOrderRequest;
import com.inventorymanagementservice.models.response.UpdateInventoryResponse;
import com.inventorymanagementservice.services.UpdateInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateInventoryController {

    @Autowired
    UpdateInventoryService updateInventoryService;

    @PatchMapping("/update")
    private ResponseEntity<UpdateInventoryResponse> updateInventory(@RequestBody SubmitOrderRequest submitOrderRequest) {

        UpdateInventoryResponse updateInventoryResponse =
                updateInventoryService.updateInventory(submitOrderRequest);

        return ResponseEntity.ok(updateInventoryResponse);

    }
}
