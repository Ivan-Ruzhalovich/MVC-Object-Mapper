package com.ivan.mvcobjectmapper.repository;

import com.ivan.mvcobjectmapper.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
