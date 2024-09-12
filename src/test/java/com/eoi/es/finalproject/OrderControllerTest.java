package com.eoi.es.finalproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.eoi.es.finalproject.controller.OrderController;
import com.eoi.es.finalproject.dto.OrderDto;
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
    	    order.setName("Pedido 1");
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

    	    order = ordersRepository.save(order);
    	    assertNotNull(order.getId());

    	    int orderId = order.getId();
    	    List<OrderItem> items = order.getOrderItems(); 
        // Configuración del mock
       // when(orderService.findOrdersById(anyInt())).thenReturn(orderDto);

        // Realiza la petición GET y verifica la respuesta
        mockMvc.perform(get("/marketplace/pedidos/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.nombre").value(order.getName()))
                .andExpect(jsonPath("$.fecha").value("2022-09-02"))
                .andExpect(jsonPath("$.articulos.length()").value(2)) 
                .andExpect(jsonPath("$.articulos[0].id").value(3))
                .andExpect(jsonPath("$.articulos[0].cantidad").value(4))
                .andExpect(jsonPath("$.articulos[1].id").value(4))
                .andExpect(jsonPath("$.articulos[1].cantidad").value(3));
    }
}
