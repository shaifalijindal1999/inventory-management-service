package com.inventorymanagementservice.repositories;

import com.inventorymanagementservice.schema.ProductData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductDataRepository extends CrudRepository<ProductData, String> {

}
