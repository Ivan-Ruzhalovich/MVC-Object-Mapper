package com.ivan.mvcobjectmapper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "productid")
    Long productId;

    @Column (name = "name")
    @NotBlank
    String name;

    @Column (name = "description")
    @NotBlank
    String description;

    @Column(name = "price")
    @Positive
    BigDecimal price;

    @Column(name = "quantityinstock")
    Long quantityInStock;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Long quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
