package com.eoi.es.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.CreateOrderDto;
import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.dto.OrderItemDto;
import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.OrderItem;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.repository.OrdersRepository;
import com.eoi.es.finalproject.repository.ProductsRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrdersRepository ordersRepository; 
    
    @Autowired
    ProductsRepository productsRepository;

    @Override
    public OrderDto findOrdersById(Integer id) {
        // Obtener la entidad Order desde la base de datos
        Order entity = ordersRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        // Convertir Order a OrderDto
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDate(entity.getDate());

        // Convertir OrderItems a OrderItemDto
        List<OrderItemDto> orderItemDtos = entity.getOrderItems().stream()
            .map(orderItem -> new OrderItemDto(orderItem.getProduct().getId(), orderItem.getQuantity()))
            .collect(Collectors.toList());

        dto.setOrderItems(orderItemDtos);

        return dto;
    }

    @Override
    public List<OrderDto> findOrderByPartialName(String partialName) {
        List<Order> entities = ordersRepository.findByNameContainingIgnoreCase(partialName);
        return entities.stream()
            .map(this::convertOrderToDto)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderById(Integer id) {
        ordersRepository.deleteById(id);
    }

    @Override
    public OrderDto createOrder(CreateOrderDto dto) {
        // Crear una nueva instancia de Order
        Order order = new Order();
        order.setName(dto.getName());
        order.setDate(dto.getDate());
        order.setUserId(dto.getUserId());

        // Convertir List<OrderItemDto> a List<OrderItem>
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : dto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            Product product = productsRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        // Establecer la lista de OrderItems en la Order
        order.setOrderItems(orderItems);

        // Guardar la Order en el repositorio
        Order savedOrder = ordersRepository.save(order);

        // Convertir la Order guardada a OrderDto y devolver
        return convertOrderToDto(savedOrder);
    }

    @Override
    public OrderDto updateOrder(OrderDto dto) {
        Order existingOrder = ordersRepository.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + dto.getId()));

        existingOrder.setName(dto.getName());
        existingOrder.setDate(dto.getDate());

        // Convertir List<OrderItemDto> a List<OrderItem>
        List<OrderItem> updatedOrderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : dto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            Product product = productsRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setOrder(existingOrder);
            updatedOrderItems.add(orderItem);
        }

        // Establecer la lista de OrderItems actualizada en la Order
        existingOrder.setOrderItems(updatedOrderItems);

        // Guardar la Order actualizada en el repositorio
        Order updatedOrder = ordersRepository.save(existingOrder);

        // Convertir la Order actualizada a OrderDto y devolver
        return convertOrderToDto(updatedOrder);
    }

    private OrderDto convertOrderToDto(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
            .map(orderItem -> new OrderItemDto(orderItem.getProduct().getId(), orderItem.getQuantity()))
            .collect(Collectors.toList());

        return new OrderDto(
            order.getId(),
            order.getName(),
            order.getDate(),
            orderItemDtos
        );
    }
}
