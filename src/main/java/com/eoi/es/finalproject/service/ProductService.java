package com.eoi.es.finalproject.service;

import java.util.List;

import com.eoi.es.finalproject.dto.ProductDto;


public interface ProductService {
	
	public List<ProductDto> findProductsByPartialName(String partialName); 
	
	public ProductDto findProductById(Integer id); 
	
	public ProductDto createProduct(ProductDto dto); 
	
	public ProductDto updateProduct(ProductDto dto); 

}
