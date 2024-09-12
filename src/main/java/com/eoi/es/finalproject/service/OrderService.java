package com.eoi.es.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.CreateOrderDto;
import com.eoi.es.finalproject.dto.OrderDto;

@Service
public interface OrderService {
	public OrderDto findOrdersById(Integer id); 
	
	public List<OrderDto> findOrderByPartialName(String partialName); 
	
	public void deleteOrderById(Integer id); 
	
	
	public OrderDto updateOrder(OrderDto dto);

	public OrderDto createOrder(CreateOrderDto createOrderDto);

	
}
