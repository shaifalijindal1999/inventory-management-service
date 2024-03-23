package com.inventorymanagementservice.models.request;

import com.inventorymanagementservice.models.ProductModels.ProductInfo;
import com.inventorymanagementservice.models.UserModels.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Data
@Component
public class SubmitOrderRequest {

    @NotNull(message = "Product list cannot be null")
    @NotEmpty(message = "Product list cannot be empty")
    List<ProductInfo> productList;
    User user;

    public void setProductList(List<ProductInfo> productList) {
        this.productList = productList;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
