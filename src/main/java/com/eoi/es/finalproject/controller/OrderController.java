package com.eoi.es.finalproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eoi.es.finalproject.dto.CreateOrderDto;
import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.service.OrderServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/marketplace/pedidos")
public class OrderController {

	@Autowired 
	OrderServiceImpl orderService; 
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDto> findById(@RequestParam String id) {	
		Integer orderId = Integer.parseInt(id);
		return new ResponseEntity<OrderDto>(orderService.findOrdersById(orderId),HttpStatus.OK);
		
	}
	
	@GetMapping(value="/{nombreparcial}/nombre")
	public ResponseEntity<List<OrderDto>> findByPartialName(@RequestParam String nombreparcial){
		return new ResponseEntity<List<OrderDto>>(orderService.findOrderByPartialName(nombreparcial), HttpStatus.OK); 
	}
	
	@DeleteMapping(value = "/{id}")	
	public ResponseEntity<?> deleteOrder(@PathVariable String id) {
		Integer orderId = Integer.parseInt(id);
		orderService.deleteOrderById(orderId);
		return new ResponseEntity<String>(HttpStatus.OK); 
	}
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto, BindingResult result) {
	    if (result.hasErrors()) {
	        System.out.println("Campos incorrectos");
	        System.out.println("Errores: " + result.getAllErrors());
	        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	    } else {
	        OrderDto createdOrder = orderService.createOrder(createOrderDto);
	        return new ResponseEntity<OrderDto>(createdOrder, HttpStatus.CREATED);
	    }
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody OrderDto order, @PathVariable Integer id,BindingResult result) {

		
		if(id.equals(order.getId())||result.hasErrors()) {
			System.out.println("hay campos incorrectos");
			System.out.println("errores: " + result.getAllErrors());		
			
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		else {

			OrderDto orderDto = orderService.updateOrder(order);
			return new ResponseEntity<OrderDto>(orderDto, HttpStatus.ACCEPTED);
		}
	}
}
