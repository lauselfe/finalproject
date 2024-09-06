package com.eoi.es.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoi.es.finalproject.entity.Product;



@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer>{

	List<Product> findAll();
	
	List<Product> findByNameContainingIgnoreCase(String partialName);
	
	

}