package com.eoi.es.finalproject.service;

import java.util.List;

import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.dto.ProductDto;
import com.eoi.es.finalproject.dto.UserDto;

public interface ReportsService {

	public List<ProductDto> bestSellers(); 
	public List<OrderDto> topOrders(); 
	public List<UserDto> topUsers(); 
	
}
