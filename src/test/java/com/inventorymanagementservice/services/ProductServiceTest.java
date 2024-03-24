package com.inventorymanagementservice.services;

import com.inventorymanagementservice.models.ProductModels.ProductInfo;
import com.inventorymanagementservice.schema.ProductData;
import com.inventorymanagementservice.repositories.ProductDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    @Mock
    ProductDataRepository mockProductDataRepository;

    ProductService productService;
    String mockProductId = "mockProductId";
    String mockProductName = "mockProductName";
    int mockRequestedQuantity = 1;

    @BeforeEach
    void setUp() {
        mockProductDataRepository = mock(ProductDataRepository.class);
        productService = new ProductService();
        productService.productDataRepository = mockProductDataRepository;
    }

    @Test
    void getProductQuote_validProductId_ProductQuoteResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        ProductData mockProductData = createMockProductDataFromDatabase();
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(mockProductData);

        // Act
        ProductInfo response = productService.getProductQuote(mockProductId, mockRequestedQuantity);

        // Assert
        assertEquals(response.getPrice(), mockProductData.getPrice());
        assertEquals(response.getId(), mockProductData.getId());
        assertEquals(response.getName(), mockProductData.getName());
        assertEquals(response.getRequestedQuantity(), mockRequestedQuantity);
    }

    @Test
    void getProductQuote_noProductIdFound_FailureNoProductFoundResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(null);

        // Act
        ProductInfo response = productService.getProductQuote(mockProductId, mockRequestedQuantity);

        // Assert
        assertEquals(response.getErrorResponse().getFailReason(), "No product found with given id!");
        assertEquals(response.getErrorResponse().getCode(), HttpStatus.BAD_REQUEST.name());
    }

    @Test
    void getProductQuote_lessQuantity_FailureNoProductAvailableResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        ProductData mockProductData = createMockProductDataFromDatabase();
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(mockProductData);

        // Act
        ProductInfo response = productService.getProductQuote(mockProductId, 5);

        // Assert
        assertInstanceOf(ProductInfo.class, response);
        assertEquals(response.getErrorResponse().getFailReason(), "Requested quantity of the product is not available in the stock!");
        assertEquals(response.getErrorResponse().getCode(), HttpStatus.OK.name());
    }

    @Test
    void getProductQuote_invalidProductId_FailureInvalidRequestResponse() {

        // Arrange
        Optional<ProductData> mockOptional = mock(Optional.class);
        when(mockProductDataRepository.findById(mockProductId)).thenReturn(mockOptional);
        when(mockOptional.orElse(null)).thenReturn(null);

        // Act
        ProductInfo response = productService.getProductQuote("", 1);

        // Assert
        assertEquals(response.getErrorResponse().getFailReason(), "Invalid request");
        assertEquals(response.getErrorResponse().getCode(), HttpStatus.BAD_REQUEST.name());
    }

    private ProductData createMockProductDataFromDatabase() {
        ProductData mockProductData = new ProductData();
        mockProductData.setId(mockProductId);
        mockProductData.setName(mockProductName);
        mockProductData.setPrice(10);
        mockProductData.setQuantity(1);
        return mockProductData;
    }
}