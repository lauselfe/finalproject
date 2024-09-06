package com.eoi.es.finalproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eoi.es.finalproject.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>{

	List<User> findAll();
	
	User findByName(String name);

	boolean existsByName(String name); 

}