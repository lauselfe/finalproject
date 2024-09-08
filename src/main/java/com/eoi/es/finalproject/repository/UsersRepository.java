package com.eoi.es.finalproject.repository;

import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>{

	List<User> findAll();
	
	User findByName(String name);

	boolean existsByName(String name); 
	
	 List<User> findTop5ByOrderByMoneyExpendedDesc();
	

}