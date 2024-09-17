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
	public ResponseEntity<ProductDto> findById(@PathVariable String id) {
		Integer productId = Integer.parseInt(id);
		ProductDto product = productService.findProductById(productId);
		ResponseEntity<ProductDto> response;

		if (product.getId() != null) {
			response = new ResponseEntity<ProductDto>(product, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;

	}

	@GetMapping(value = "/{nombreparcial}/nombre")
	public ResponseEntity<List<ProductDto>> findByPartialName(@PathVariable String nombreparcial) {
		ResponseEntity<List<ProductDto>> response;
		if (nombreparcial == null || nombreparcial.trim().isEmpty()) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<ProductDto> products = productService.findProductsByPartialName(nombreparcial);
		response = new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);

		return response;

	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid ProductDto product, BindingResult result) {
		ResponseEntity<?> response;

		if (result.hasErrors()) {
			System.out.println("errores: " + result.getAllErrors());
			response = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} else {
			ProductDto dto = productService.createProduct(product);
			response = new ResponseEntity<ProductDto>(dto, HttpStatus.CREATED);
		}
		return response;

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody @Valid ProductDto product, @PathVariable String id,
			BindingResult result) {

		ResponseEntity<?> response;
		Integer productId = Integer.parseInt(id);

		if (!productId.equals(product.getId()) || result.hasErrors()) {

			System.out.println("errores: " + result.getAllErrors());
			response = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} else {

			ProductDto productDto = productService.updateProduct(product);
			response = new ResponseEntity<ProductDto>(productDto, HttpStatus.ACCEPTED);
		}

		return response;
	}

}
