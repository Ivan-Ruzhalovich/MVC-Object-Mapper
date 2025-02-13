package com.ivan.mvcobjectmapper.service;

import com.ivan.mvcobjectmapper.entity.Order;
import com.ivan.mvcobjectmapper.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ObjectMapperService {
    List<Product> getAllProductsFromMagazine();
    Product getInformationAboutProduct(Long id);
    void createAndAddNewProduct(Product product);
    void deleteProduct (Long id);
    void updateProduct (Product product);
    void registerNewOrder (Order order);
    Order getOrder (Long id);
}
