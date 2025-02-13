package com.ivan.mvcobjectmapper.repository;

import com.ivan.mvcobjectmapper.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByDescription(String description);
}
