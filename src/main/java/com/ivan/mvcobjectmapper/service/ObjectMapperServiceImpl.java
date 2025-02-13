package com.ivan.mvcobjectmapper.service;

import com.ivan.mvcobjectmapper.entity.Order;
import com.ivan.mvcobjectmapper.entity.Product;
import com.ivan.mvcobjectmapper.repository.OrdersRepository;
import com.ivan.mvcobjectmapper.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectMapperServiceImpl implements ObjectMapperService{

    private final ProductsRepository productsRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public ObjectMapperServiceImpl(ProductsRepository productsRepository
            ,OrdersRepository ordersRepository) {
        this.productsRepository = productsRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<Product> getAllProductsFromMagazine() {
        return productsRepository.findAll();
    }

    @Override
    public Product getInformationAboutProduct(Long id) {
        return productsRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Продукт не найден!"));
    }

    @Override
    public void createAndAddNewProduct(Product product) {
        Optional<Product> newProduct = productsRepository
                .findByDescription(product.getDescription());
        if(newProduct.isEmpty())
            productsRepository.save(product);
        else throw  new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Такой товар уже существует!");
    }

    @Override
    public void deleteProduct(Long id) {
        if (productsRepository.existsById(id))
            productsRepository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Товар не найден");
    }

    @Override
    public void updateProduct(Product product) {
        if(productsRepository.existsById(product.getProductId()))
            productsRepository.save(product);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Такого товара нет!");
    }

    @Override
    public void registerNewOrder(Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Product product:order.getProducts()){
            totalPrice.add(product.getPrice());
        }
        order.setTotalPrice(totalPrice);
        if(order.getOrderId()==null)
            ordersRepository.save(order);
        else if(order.getOrderId()!=null && !ordersRepository.existsById(order.getOrderId()))
            ordersRepository.save(order);
        else throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Такой заказ уже существует!");
    }

    @Override
    public Order getOrder(Long id) {
        Order order =  ordersRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Заказ не найден!"));
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Product product:order.getProducts()){

            totalPrice.add(productsRepository.findById(product.getProductId()).get().getPrice());
        }
        order.setTotalPrice(totalPrice);
        return order;
    }
}
