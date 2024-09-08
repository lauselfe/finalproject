package com.eoi.es.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.eoi.es.finalproject.entity.OrderItem;

@Repository
public  interface OrdersItemRepository extends JpaRepository<OrderItem, Integer> {
	List<OrderItem> findAll();
}
