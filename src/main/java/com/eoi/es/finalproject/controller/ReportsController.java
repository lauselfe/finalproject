package com.eoi.es.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.dto.ProductDto;
import com.eoi.es.finalproject.dto.UserDto;
import com.eoi.es.finalproject.service.ReportsServiceImpl;

@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportsServiceImpl reportService; 
	@GetMapping(value ="/articulosmasvendidos")
	public ResponseEntity<List<ProductDto>> bestsellers(){
		return new ResponseEntity<List<ProductDto>>(reportService.bestSellers(), HttpStatus.OK);  
	}
	
	@GetMapping(value ="/mejorespedidos")
	public ResponseEntity<List<OrderDto>> topOrders(){
		return new ResponseEntity<List<OrderDto>>(reportService.topOrders(), HttpStatus.OK); 
	}
	
	@GetMapping(value ="/mejoresusuarios")
	public ResponseEntity<List<UserDto>> topUsers(){
		return new ResponseEntity<List<UserDto>>(reportService.topUsers(), HttpStatus.OK); 
	}
	
	
	
}
