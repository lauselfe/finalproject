package com.eoi.es.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.ProductDto;
import com.eoi.es.finalproject.dto.UserDto;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.ProductsRepository;



@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductsRepository productsRepository; 
	
	@Override
	public List<ProductDto> findProductsByPartialName(String partialName) {
		// TODO Auto-generated method stub
		List<ProductDto> dtos= new ArrayList<ProductDto>();		
		List<Product> entities= productsRepository.findByNameContainingIgnoreCase(partialName);
				
		for (Product product : entities) {
			ProductDto dto= new ProductDto();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setStock(product.getStock());
			dto.setPrice(product.getPrice());
			
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public ProductDto findProductById(Integer id) {
		// TODO Auto-generated method stub
		ProductDto dto= new ProductDto();		 
		 Product entity = productsRepository.findById(Integer.valueOf(id)).get();
	
		 BeanUtils.copyProperties(entity,dto);		 
		 
		 return dto;
	
	}

	@Override
	public void createProduct(ProductDto dto) {
		// TODO Auto-generated method stub
		Product entity= new Product();
		entity.setName(dto.getName());
		entity.setStock(dto.getStock());
		entity.setPrice(dto.getPrice());
		
		productsRepository.save(entity);
		
		 
	}

	@Override
	public ProductDto updateProduct(ProductDto dto) {
		// TODO Auto-generated method stub
		// Verificar si el usuario existe
	    Product existingProduct = productsRepository.findById(dto.getId())
	        .orElseThrow(() -> new RuntimeException("Product not found"));

	    // Actualizar los campos del usuario
	    existingProduct.setName(dto.getName());
	    existingProduct.setPrice(dto.getPrice());
	    existingProduct.setStock(dto.getStock());

	    // Guardar el usuario actualizado
	    Product updatedProduct = productsRepository.save(existingProduct);

	    // Devolver el DTO actualizado
	    ProductDto resultDto = new ProductDto();
	    resultDto.setId(updatedProduct.getId());
	    resultDto.setName(updatedProduct.getName());
	    

	    return resultDto;
		
		
	}

}
