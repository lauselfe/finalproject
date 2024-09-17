package com.eoi.es.finalproject;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eoi.es.finalproject.entity.Order;
import com.eoi.es.finalproject.entity.OrderItem;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.OrdersRepository;
import com.eoi.es.finalproject.repository.ProductsRepository;
import com.eoi.es.finalproject.repository.UsersRepository;
import com.eoi.es.finalproject.service.OrderService;
import com.eoi.es.finalproject.service.OrderServiceImpl;

@SpringBootTest

@RunWith(SpringRunner.class)
public class OrderServiceTest {

	@TestConfiguration
	static class OrderServiceImplTestContextConfiguration{
		@Bean
		public OrderService orderService() {
			return new OrderServiceImpl(); 
		}
	}
	
	@Autowired
	private OrderService orderService; 
	
	@MockBean
	private OrdersRepository ordersRepository;
	
	@MockBean
	private UsersRepository usersRepository;
	
	@MockBean
	private ProductsRepository productsRepository;
	
	@Before
	public void setUp() {
	
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
	}
}
