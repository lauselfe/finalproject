package com.eoi.es.finalproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eoi.es.finalproject.dto.ProductDto;

import com.eoi.es.finalproject.service.ProductServiceImpl;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/marketplace/articulos")
public class ProductController {

	@Autowired
	ProductServiceImpl productService; 
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@RequestParam String id) {	
		Integer productId = Integer.parseInt(id);
		return new ResponseEntity<ProductDto>(productService.findProductById(productId),HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/{nombreparcial}/nombre")
	public ResponseEntity<List<ProductDto>> findByPartialName(@RequestParam String nombreparcial){
		
		List<ProductDto> products = productService.findProductsByPartialName(nombreparcial); 	
		return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDto product, BindingResult result){
		if(result.hasErrors()) {
			System.out.println("hay campos incorrectos");
			System.out.println("errores: " + result.getAllErrors());		
			
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		else {

			productService.createProduct(product);
			return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
		}
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody ProductDto product, @PathVariable String id,BindingResult result) {

		Integer productId = Integer.parseInt(id);
		if(!productId.equals(product.getId())||result.hasErrors()) {
			System.out.println("hay campos incorrectos");
			System.out.println("errores: " + result.getAllErrors());		
			
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		else {

			ProductDto productDto = productService.updateProduct(product);
			return new ResponseEntity<ProductDto>(productDto, HttpStatus.ACCEPTED);
		}
	}
	
}
