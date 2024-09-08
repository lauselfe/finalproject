package com.eoi.es.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.ProductDto;
import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.dto.OrderItemDto;
import com.eoi.es.finalproject.dto.UserDto;
import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.OrderItem;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.OrdersRepository;
import com.eoi.es.finalproject.repository.ProductsRepository;
import com.eoi.es.finalproject.repository.UsersRepository;

@Service
public class ReportsServiceImpl implements ReportsService{

	@Autowired
	ProductsRepository productsRepository; 
	
	@Autowired
	OrdersRepository ordersRepository; 
	
	@Autowired
	UsersRepository usersRepository; 
	
	@Override
	public List<ProductDto> bestSellers() {
		// Obtener los 5 productos más vendidos
	    List<Product> top5 = productsRepository.findTop5ByOrderBySalesDesc();
	    
	    // Convertir la lista de productos a una lista de ProductDto
	    List<ProductDto> top5Dto = new ArrayList<>();
	    for (Product product : top5) {
	        ProductDto productDto = new ProductDto();
	        BeanUtils.copyProperties(product, productDto);
	        top5Dto.add(productDto);
	    }
	    
	    return top5Dto;
	}

	@Override
	public List<OrderDto> topOrders() {
		// TODO Auto-generated method stub
		List<Order> top5 = ordersRepository.findTop5ByOrderByTotalDesc();
		 List<OrderDto> top5Dto = new ArrayList<>();
		 
		 for (Order order : top5) {
			    List<OrderItemDto> itemsDto = new ArrayList<>(); // Mover dentro del ciclo de órdenes
			    for (OrderItem item : order.getOrderItems()) {
			        OrderItemDto itemDto = new OrderItemDto();
			        itemDto.setProductId(item.getProduct().getId()); // ID del Producto
			        itemDto.setQuantity(item.getQuantity());
			        itemsDto.add(itemDto);
			    }
			    OrderDto orderDto = new OrderDto();
			    BeanUtils.copyProperties(order, orderDto);
			    orderDto.setOrderItems(itemsDto);
			    top5Dto.add(orderDto);
			}
		return top5Dto;
	}

	@Override
	public List<UserDto> topUsers() {
		// TODO Auto-generated method stub
		List<User> top5 = usersRepository.findTop5ByOrderByMoneyExpendedDesc();
		 List<UserDto> top5Dto = new ArrayList<>();
		    for (User user : top5) {
		        UserDto userDto = new UserDto();
		        BeanUtils.copyProperties(user,userDto);
		        top5Dto.add(userDto);
		    }
		return top5Dto;
		
		
	}

}
