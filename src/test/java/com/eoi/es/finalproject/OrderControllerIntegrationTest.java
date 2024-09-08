package com.eoi.es.finalproject;

import com.eoi.es.finalproject.dto.OrderDto;
import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.OrderItem;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.OrdersRepository;
import com.eoi.es.finalproject.repository.ProductsRepository;
import com.eoi.es.finalproject.repository.UsersRepository;
import com.eoi.es.finalproject.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrdersRepository ordersRepository;
    
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository usersRepository;
    
    @MockBean
    private OrderService orderService; 

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
    	   ordersRepository.deleteAll();
           productsRepository.deleteAll();
           usersRepository.deleteAll();
    }

 
    @Test
    void shouldReturnOrderById() throws Exception {
        // Crear y guardar un usuario de prueba
        User user = new User();
        user.setName("Test User");
        user = usersRepository.save(user);

        // Crear y guardar productos de prueba
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

        // Crear una orden de prueba
        Order order = new Order();
        order.setName("Pedido 1");
        order.setDate(LocalDate.of(2022, 9, 2));  // Fecha en el formato esperado
        order.setUserId(user.getId());

        // Crear y agregar OrderItems a la orden
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(4);
        orderItem1.setOrder(order);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(3);
        orderItem2.setOrder(order);

        order.setOrderItems(Arrays.asList(orderItem1, orderItem2));

        // Guardar la orden en la base de datos
        order = ordersRepository.save(order);

        // Realizar la petición GET al endpoint
        mockMvc.perform(get("/marketplace/pedidos/{id}", order.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.nombre").value(order.getName()))
                .andExpect(jsonPath("$.fecha[0]").value(2022))  // Validar el año
                .andExpect(jsonPath("$.fecha[1]").value(9))     // Validar el mes
                .andExpect(jsonPath("$.fecha[2]").value(2))     // Validar el día
                .andExpect(jsonPath("$.articulos[0].id").value(product1.getId()))  // Validar el primer producto
                .andExpect(jsonPath("$.articulos[0].cantidad").value(4))
                .andExpect(jsonPath("$.articulos[1].id").value(product2.getId()))  // Validar el segundo producto
                .andExpect(jsonPath("$.articulos[1].cantidad").value(3));
    }


    @Test
    void shouldDeleteOrderById() throws Exception {
        // Crear y guardar un usuario de prueba
        User user = new User();
        user.setName("Test User");
        user = usersRepository.save(user);

        // Crear y guardar productos de prueba
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

        // Crear y guardar una orden de prueba
        Order order = new Order();
        order.setName("Pedido 1");
        order.setDate(LocalDate.of(2022, 9, 2));  // Fecha en el formato esperado
        order.setUserId(user.getId());

        // Crear y agregar OrderItems a la orden
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(4);
        orderItem1.setOrder(order);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(3);
        orderItem2.setOrder(order);

        order.setOrderItems(Arrays.asList(orderItem1, orderItem2));

        // Guardar la orden en la base de datos
        order = ordersRepository.save(order);

        // Verificar que la orden se haya guardado
        assertThat(ordersRepository.findById(order.getId())).isPresent();

        // Realizar la petición DELETE al endpoint
        mockMvc.perform(delete("/marketplace/pedidos/{id}", order.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar que la orden haya sido eliminada
        assertThat(ordersRepository.findById(order.getId())).isEmpty();
    }

 


   
}

