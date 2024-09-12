package com.eoi.es.finalproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.eoi.es.finalproject.controller.OrderController;
import com.eoi.es.finalproject.dto.CreateOrderDto;
import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.dto.OrderItemDto;
import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.OrderItem;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.OrdersRepository;
import com.eoi.es.finalproject.repository.ProductsRepository;
import com.eoi.es.finalproject.repository.UsersRepository;
import com.eoi.es.finalproject.service.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UsersRepository usersRepository; 
    
    @Autowired
    private ProductsRepository productsRepository; 
    
    @Autowired
    private OrdersRepository ordersRepository; 

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        // Datos de prueba
    	Order order = setupOrder();
        assertNotNull(order.getId());

        int orderId = order.getId();
        
        List<OrderItem> orderItems = order.getOrderItems();
        Integer itemId1 = orderItems.get(0).getId();
        Integer itemId2 = orderItems.get(1).getId();
        Integer quantity1 = orderItems.get(0).getQuantity();
        Integer quantity2 = orderItems.get(1).getQuantity();
   
        mockMvc.perform(get("/marketplace/pedidos/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.nombre").value(order.getName()))
                .andExpect(jsonPath("$.fecha").value("2022-09-02"))
                .andExpect(jsonPath("$.articulos.length()").value(2)) 
                .andExpect(jsonPath("$.articulos[0].id").value(itemId1)) 
                .andExpect(jsonPath("$.articulos[0].cantidad").value(quantity1)) 
                .andExpect(jsonPath("$.articulos[1].id").value(itemId2)) 
                .andExpect(jsonPath("$.articulos[1].cantidad").value(quantity2)); 
    }
    
    @Test
    void testFindByPartialName() throws Exception {
    	Order order = setupOrder(); 
    	String partial = "3"; 
    	
    	// Mapea el Order a OrderDto usando Lombok @Builder
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .name(order.getName())
                .date(order.getDate())
                .orderItems(order.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDto(orderItem.getId(), orderItem.getQuantity())) // Asume un constructor en OrderItemDto
                        .collect(Collectors.toList()))
                .build();

        // Configura el mock del servicio para devolver el DTO
        when(orderService.findOrderByPartialName(partial))
            .thenReturn(Collections.singletonList(orderDto));
    	
    	  List<OrderItem> orderItems = order.getOrderItems();
          Integer itemId1 = orderItems.get(0).getId();
          Integer itemId2 = orderItems.get(1).getId();
          Integer quantity1 = orderItems.get(0).getQuantity();
          Integer quantity2 = orderItems.get(1).getQuantity();
          
          mockMvc.perform(get("/marketplace/pedidos/{nombreparcial}/nombre", partial))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[0].id").value(order.getId()))
         .andExpect(jsonPath("$[0].nombre").value(order.getName()))
         .andExpect(jsonPath("$[0].fecha").value("2022-09-02"))
         .andExpect(jsonPath("$[0].articulos.length()").value(2))
         .andExpect(jsonPath("$[0].articulos[0].id").value(itemId1))
         .andExpect(jsonPath("$[0].articulos[0].cantidad").value(quantity1))
         .andExpect(jsonPath("$[0].articulos[1].id").value(itemId2))
         .andExpect(jsonPath("$[0].articulos[1].cantidad").value(quantity2));
       
          MvcResult result = mockMvc.perform(get("/marketplace/pedidos/{nombreparcial}/nombre", partial))
        		    .andExpect(status().isOk())
        		    .andReturn();

        		System.out.println(result.getResponse().getContentAsString());
    }
    
    @Test
    void testDeleteOrder() throws Exception{
    	// Datos de prueba
    	 Order order = setupOrder();
    	    assertNotNull(order.getId());

    	    int orderId = order.getId();

    	 
    	    doNothing().when(orderService).deleteOrderById(orderId);  

    	 
    	    mockMvc.perform(delete("/marketplace/pedidos/{id}", orderId))
    	            .andExpect(status().isOk());

    }
    
    @Test
    void createOrder_ValidInput_ShouldReturnCreated() throws Exception{
    	Order order = setupOrder(); 
    	 CreateOrderDto orderDto = CreateOrderDto.builder()
                 .id(order.getId())
                 .name(order.getName())
                 .date(order.getDate())
                 .userId(order.getUserId())
                 .orderItems(order.getOrderItems().stream()
                         .map(orderItem -> new OrderItemDto(orderItem.getId(), orderItem.getQuantity())) 
                         .collect(Collectors.toList()))
                 .build();
    	 
    	//  when(orderService.createOrder(any(CreateOrderDto.class))).thenReturn(orderDto);
    	  
    	  
    	  mockMvc.perform(post("/marketplace/pedidos"))
          .andExpect(status().isOk()); 
         
    	  

    }
    
    
    private Order setupOrder() {
        User user = new User();
        user.setName("Test User");
        user = usersRepository.save(user);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(20.0);
        product1.setStock(100);
        product1 = productsRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(15.0);
        product2.setStock(50);
        product2 = productsRepository.save(product2);

        Order order = new Order();
        order.setName("Pedido 3");
        order.setDate(LocalDate.of(2022, 9, 2));
        order.setUserId(user.getId());

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(4);
        orderItem1.setOrder(order);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(3);
        orderItem2.setOrder(order);

        order.setOrderItems(Arrays.asList(orderItem1, orderItem2));

        return ordersRepository.save(order);
    }

}
