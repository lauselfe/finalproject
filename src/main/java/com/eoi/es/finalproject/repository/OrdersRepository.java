package com.eoi.es.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.Product;


@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer>{

	List<Order> findAll();
	
	List<Order> findByNameContainingIgnoreCase(String partialName);
	
	List<Order> findTop5ByOrderByTotalDesc();
	

}