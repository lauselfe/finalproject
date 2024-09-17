package com.eoi.es.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.ProductDto;
import com.eoi.es.finalproject.entity.Product;
import com.eoi.es.finalproject.repository.ProductsRepository;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductsRepository productsRepository;

	@Override
	public List<ProductDto> findProductsByPartialName(String partialName) {

		List<Product> entities = productsRepository.findByNameContainingIgnoreCase(partialName);
		List<ProductDto> dtos = convertEntitiesToDtos(entities);

		return dtos;
	}

	@Override
	public ProductDto findProductById(Integer id) {

		Product entity = productsRepository.findById(id).orElse(new Product());
		ProductDto dto = convertEntityToDto(entity);

		return dto;
	}

	@Override
	public ProductDto createProduct(ProductDto dto) {

		Product entity = convertDtoToNewEntity(dto);
		productsRepository.save(entity);
		ProductDto resultDto = convertEntityToDto(entity); 
		return resultDto; 

	}

	@Override
	public ProductDto updateProduct(ProductDto dto) {

		Product existingProduct = productsRepository.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("Product not found"));
		existingProduct = convertDtoToEntity(dto);
		Product updatedProduct = productsRepository.save(existingProduct);
		ProductDto resultDto = convertEntityToDto(updatedProduct);

		return resultDto;

	}

	private List<ProductDto> convertEntitiesToDtos(List<Product> entities) {
		List<ProductDto> dtos = new ArrayList<ProductDto>();

		for (Product entity : entities) {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}

		return dtos;
	}

	private ProductDto convertEntityToDto(Product entity) {
		ProductDto dto = new ProductDto();

		BeanUtils.copyProperties(entity, dto);

		return dto;

	}

	private Product convertDtoToEntity(ProductDto dto) {
		Product entity = new Product();

		BeanUtils.copyProperties(dto, entity);

		return entity;

	}
	
	private Product convertDtoToNewEntity(ProductDto dto) {
		Product entity = new Product();

		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setStock(dto.getStock());
		entity.setSales(0);
		return entity;

	}

}
