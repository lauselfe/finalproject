package com.eoi.es.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.CreateOrderDto;
import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.dto.OrderItemDto;
import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.OrderItem;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.OrdersItemRepository;
import com.eoi.es.finalproject.repository.OrdersRepository;
import com.eoi.es.finalproject.repository.ProductsRepository;
import com.eoi.es.finalproject.repository.UsersRepository;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrdersRepository ordersRepository; 
    
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    OrdersItemRepository ordersItemRepository;
    
    @Autowired 
    UsersRepository usersRepository; 
    
    @Override
    public OrderDto findOrdersById(Integer id) {
    	
        Order entity = findExistingOrder(id); 
        
        OrderDto dto = convertOrderToDto(entity); 
    
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
    	
    	Order savedOrder = saveNewOrder(dto); 
        return convertOrderToDto(savedOrder);
    }


    @Override
    public OrderDto updateOrder(OrderDto dto) {
    	
        Order existingOrder = findExistingOrder(dto.getId());
        User user = findOrderUser(existingOrder.getUserId());

        double oldTotal = existingOrder.getTotal();
        updateOrderDetails(existingOrder, dto);

        List<OrderItem> updatedOrderItems = processOrderItems(dto, existingOrder);  
        double newTotal = calculateOrderTotal(updatedOrderItems);

        updateOrderItems(existingOrder, updatedOrderItems);
        updateUserTotalSpent(user, oldTotal, newTotal);

        usersRepository.save(user);
        Order updatedOrder = ordersRepository.save(existingOrder);

        return convertOrderToDto(updatedOrder);
        
    }

    // Encuentra una orden existente por ID
    private Order findExistingOrder(Integer orderId) {
    	
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

  

    // Actualiza los detalles de la orden (nombre, fecha)
    private void updateOrderDetails(Order existingOrder, OrderDto dto) {
        existingOrder.setName(dto.getName());
        existingOrder.setDate(dto.getDate());
    }

    private List<OrderItem> processOrderItems(OrderDto dto, Order existingOrder) {
        Map<Integer, OrderItem> existingOrderItemsMap = mapExistingOrderItems(existingOrder);
        List<OrderItem> updatedOrderItems = new ArrayList<>();

        for (OrderItemDto orderItemDto : dto.getOrderItems()) {
            Product product = findProduct(orderItemDto.getProductId());
            int newQuantity = orderItemDto.getQuantity();

       
            OrderItem updatedItem = updateOrCreateOrderItem(existingOrderItemsMap, product, newQuantity, existingOrder);
            updatedOrderItems.add(updatedItem);
        }

        handleRemovedOrderItems(existingOrderItemsMap, updatedOrderItems);

        return updatedOrderItems;
    }
    
 // Actualiza o crea un nuevo OrderItem, asociando el OrderItem con su Order
    private OrderItem updateOrCreateOrderItem(Map<Integer, OrderItem> existingItems, Product product, int newQuantity, Order existingOrder) {
        OrderItem orderItem;
        OrderItem existingOrderItem = existingItems.get(product.getId());

        if (existingOrderItem != null) {
            adjustStockAndSalesForExistingItem(existingOrderItem, product, newQuantity);
            existingOrderItem.setQuantity(newQuantity);
            orderItem = existingOrderItem;
        } else {
            orderItem = createNewOrderItem(product, newQuantity);
        }

        // Aquí se establece la relación entre OrderItem y Order
        orderItem.setOrder(existingOrder);  
        return orderItem;
    }

    // Crea un mapa de los items existentes en la orden
    private Map<Integer, OrderItem> mapExistingOrderItems(Order existingOrder) {
        return existingOrder.getOrderItems().stream()
                .collect(Collectors.toMap(item -> item.getProduct().getId(), item -> item));
    }

    // Encuentra el producto por ID
    private Product findProduct(Integer productId) {
        return productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }



    // Ajusta el stock y ventas para un item existente
    private void adjustStockAndSalesForExistingItem(OrderItem existingOrderItem, Product product, int newQuantity) {
        int oldQuantity = existingOrderItem.getQuantity();
        int quantityDifference = newQuantity - oldQuantity;

        if (quantityDifference > 0) {
            int adjustedQuantity = Math.min(quantityDifference, product.getStock());
            product.setStock(product.getStock() - adjustedQuantity);
            product.setSales(product.getSales() + adjustedQuantity);
        } else {
            product.setStock(product.getStock() - quantityDifference); 
            product.setSales(product.getSales() + quantityDifference);
        }
    }

 // Crea un nuevo OrderItem
    private OrderItem createNewOrderItem(Product product, int quantity) {
        OrderItem orderItem = new OrderItem();
        int adjustedQuantity = Math.min(quantity, product.getStock());

        product.setStock(product.getStock() - adjustedQuantity);
        product.setSales(product.getSales() + adjustedQuantity);

        orderItem.setQuantity(adjustedQuantity);
        orderItem.setProduct(product);

      
        return orderItem;
    }

    // Maneja los OrderItems que ya no están en el nuevo pedido
    private void handleRemovedOrderItems(Map<Integer, OrderItem> existingItems, List<OrderItem> updatedItems) {
        for (OrderItem oldItem : existingItems.values()) {
            boolean itemStillExists = updatedItems.stream()
                    .anyMatch(newItem -> newItem.getProduct().getId().equals(oldItem.getProduct().getId()));

            if (!itemStillExists) {
                Product product = oldItem.getProduct();
                product.setStock(product.getStock() + oldItem.getQuantity());
                product.setSales(product.getSales() - oldItem.getQuantity());
            }
        }
    }

    // Calcula el total de la orden
    private double calculateOrderTotal(List<OrderItem> updatedOrderItems) {
        return updatedOrderItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Actualiza los items de la orden
    private void updateOrderItems(Order existingOrder, List<OrderItem> updatedOrderItems) {
        existingOrder.getOrderItems().clear();
        existingOrder.getOrderItems().addAll(updatedOrderItems);
    }

    // Actualiza el total de dinero gastado del usuario
    private void updateUserTotalSpent(User user, double oldTotal, double newTotal) {
        user.setMoneyExpended(user.getMoneyExpended() + (newTotal - oldTotal));
    }


    private OrderDto convertOrderToDto(Order order) {
    	 // Convertir la lista de OrderItems de la entidad Order a OrderItemDto
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(item -> {
            return OrderItemDto.builder()
                .productId(item.getProduct().getId()) 
                .quantity(item.getQuantity())
                .build();
        }).collect(Collectors.toList());

     
        return OrderDto.builder()
            .id(order.getId())
            .name(order.getName())
            .date(order.getDate())
            .orderItems(orderItemDtos)
            .build();
    }
    
 
    private User findOrderUser(Integer userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
    private Order saveNewOrder(CreateOrderDto dto) {
    	Order order = new Order();
        order.setName(dto.getName());
        order.setDate(dto.getDate());
        order.setUserId(dto.getUserId());
        
        User user = findOrderUser(dto.getUserId());

        List<OrderItem> orderItems = createOrderItems(dto.getOrderItems(), order);
        
        order.setTotal(calculateOrderTotal(orderItems)); 
        order.setOrderItems(orderItems);

        user.setMoneyExpended(user.getMoneyExpended() + calculateOrderTotal(orderItems));
        usersRepository.save(user);
        
        Order savedOrder = ordersRepository.save(order);
    	
    	return savedOrder;
    	
    }

	private List<OrderItem> createOrderItems(List<OrderItemDto> orderItemsDto, Order order) {
		List<OrderItem> orderItems = new ArrayList<>(); 
		for (OrderItemDto orderItemDto : orderItemsDto) {
        	
            OrderItem orderItem = new OrderItem();
            Product product = findProduct(orderItemDto.getProductId());
            int quantity = orderItemDto.getQuantity();

            updateProductStockSales(product, quantity); 
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(order);
            orderItems.add(orderItem);         
        }
		
		return orderItems;
	}
	
	private void updateProductStockSales(Product product, int sales) {
		 if (product.getStock() < sales) {
             sales = product.getStock(); 
         }
         product.setStock(product.getStock() - sales);
         product.setSales(product.getSales() + sales);

	}
}
