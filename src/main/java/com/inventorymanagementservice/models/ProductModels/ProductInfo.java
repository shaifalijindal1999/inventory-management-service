package com.inventorymanagementservice.models.ProductModels;

import com.inventorymanagementservice.models.ErrorResponse;
import java.util.HashMap;


public class ProductInfo {

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    ErrorResponse errorResponse;

    private String id;
    private String name;
    private String description;
    private float price;

    private int quantity;

    public int getRequestedQuantity() {
        return quantity;
    }

    public void setRequestedQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    private HashMap<String, String> attributes;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public static class ProductInnerClass {
        ProductInfo productInfo = new ProductInfo();

        public ProductInnerClass setId(String id) {
            this.productInfo.setId(id);
            return this;
        }

        public ProductInnerClass setName(String name) {
            this.productInfo.setName(name);
            return this;
        }

        public ProductInnerClass setPrice(float price) {
            this.productInfo.setPrice(price);
            return this;
        }

        public ProductInnerClass setRequestedQuantity(int requestedQuantity) {
            this.productInfo.setRequestedQuantity(requestedQuantity);
            return this;
        }

        public ProductInfo build() {
            return productInfo;
        }

    }
    public static ProductInnerClass builder() {
        return new ProductInnerClass();
    }
}
