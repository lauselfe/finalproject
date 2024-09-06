package com.eoi.es.finalproject.dto;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

	@JsonProperty(value = "id")
	@NotNull
	private Integer id;
	
	@JsonProperty(value = "nombre")
	@NotNull
	private String name;
	
	@JsonProperty(value = "precio")
	private Double price;
	
	@JsonProperty(value = "cantidad")
	private Integer stock;
		
}
