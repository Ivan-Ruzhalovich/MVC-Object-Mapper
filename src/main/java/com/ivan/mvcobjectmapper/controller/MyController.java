package com.ivan.mvcobjectmapper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ivan.mvcobjectmapper.entity.Order;
import com.ivan.mvcobjectmapper.entity.Product;
import com.ivan.mvcobjectmapper.service.ObjectMapperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/magazine")
public class MyController {


    private final ObjectMapperServiceImpl service;
    private final ObjectMapper objectMapper;
    @Autowired
    public MyController(ObjectMapperServiceImpl service,ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping("/products")
    public ResponseEntity<List<String>> getAllProductsFromMagazine() {
        List<String> jsonList = service.getAllProductsFromMagazine().stream().map((product) -> {
            try {
                return objectMapper.writeValueAsString(product);
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка при сериализации списка продуктов!");
            }
        }).toList();
        return new ResponseEntity<>(jsonList,HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public String getInformationAboutProduct(@PathVariable Long id)  {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(service.getInformationAboutProduct(id));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка при сериализации информации о продукте!");
        }

    }

    @PostMapping("/products/new")
    public ResponseEntity<String> createAndAddNewProduct(@RequestBody String product) {
        try {
            service.createAndAddNewProduct(objectMapper.readValue(product,Product.class));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка при десериализации нового продукта!");
        }
        return new ResponseEntity<>("Продукт успешно добавлен!",HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<String> updateProduct(@RequestBody String product)  {
        try {
            service.updateProduct(objectMapper.readValue(product,Product.class));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка при десериализации продукта при обновлении информации о продукте!");
        }
        return new ResponseEntity<>("Информация о продукте изменена!",HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return new ResponseEntity<>("Продукт удален!",HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Long id){
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(service.getOrder(id)),HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка при сериализации информации заказа");
        }
    }

    @PostMapping("/orders/new")
    public ResponseEntity<String> registerNewOrder(@RequestBody String order){
        try {
            service.registerNewOrder(objectMapper.readValue(order, Order.class));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка при десериализации нового заказа!");
        }
        return new ResponseEntity<>("Заказ успешно создан!",HttpStatus.OK);
    }


}
