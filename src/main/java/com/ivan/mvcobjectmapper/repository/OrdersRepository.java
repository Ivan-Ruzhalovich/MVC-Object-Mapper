package com.ivan.mvcobjectmapper.repository;

import com.ivan.mvcobjectmapper.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order,Long> {
}
