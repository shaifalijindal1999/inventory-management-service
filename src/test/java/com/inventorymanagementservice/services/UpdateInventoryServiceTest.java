package com.inventorymanagementservice.services;

import com.inventorymanagementservice.models.ProductModels.ProductInfo;
import com.inventorymanagementservice.models.request.SubmitOrderRequest;
import com.inventorymanagementservice.models.response.UpdateInventoryResponse;
import com.inventorymanagementservice.repositories.ProductDataRepository;
import com.inventorymanagementservice.schema.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UpdateInventoryServiceTest {

    @Mock
    ProductDataRepository productDataRepository;

    @InjectMocks
    UpdateInventoryService updateInventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateInventory_ValidInputWithAvailableInventory() {
        // Arrange
        SubmitOrderRequest request = createValidSubmitOrderRequest();

        // Mock the repository to return available inventory
        when(productDataRepository.existsById(anyString())).thenReturn(true);
        when(productDataRepository.findById(anyString())).thenReturn(Optional.of(createProductData()));

        // Act
        UpdateInventoryResponse response = updateInventoryService.updateInventory(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getSuccessResponse());
        assertEquals("Update complete", response.getSuccessResponse().getMessage());
    }

    @Test
    void testUpdateInventory_ValidInputWithInsufficientInventory() {
        // Arrange
        SubmitOrderRequest request = createValidSubmitOrderRequest();

        // Mock the repository to return insufficient inventory
        when(productDataRepository.existsById(anyString())).thenReturn(true);
        when(productDataRepository.findById(anyString())).thenReturn(Optional.of(createProductData()));
        // Requested quantity exceeds available inventory
        request.getProductList().get(0).setRequestedQuantity(30);

        // Act
        UpdateInventoryResponse response = updateInventoryService.updateInventory(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getErrorResponse());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getErrorResponse().getCode());
        assertEquals("id1 is out of stock", response.getErrorResponse().getFailReason());
    }

    @Test
    void testUpdateInventory_NonExistentProduct() {
        // Arrange
        SubmitOrderRequest request = createValidSubmitOrderRequest();

        // Mock the repository to return non-existent product
        when(productDataRepository.existsById(anyString())).thenReturn(false);

        // Act
        UpdateInventoryResponse response = updateInventoryService.updateInventory(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getErrorResponse());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getErrorResponse().getCode());
        assertEquals("id1 not found in inventory", response.getErrorResponse().getFailReason());
    }
    // Helper methods to create test data
    private SubmitOrderRequest createValidSubmitOrderRequest() {
        List<ProductInfo> productList = new ArrayList<>();
        ProductInfo mockProductInfo = ProductInfo.builder()
                .setId("id1")
                .setName("Product-1")
                .setRequestedQuantity(10)
                .build();
        productList.add(mockProductInfo);
        SubmitOrderRequest request = new SubmitOrderRequest();
        request.setProductList(productList);
        return request;
    }

    private ProductData createProductData() {
        ProductData mockData = new ProductData();
        mockData.setId("id1");
        mockData.setName("Product-1");
        mockData.setQuantity(20);
        return mockData;
    }
}