package com.eoi.es.finalproject.dto;

import java.time.LocalDate;
import java.util.List;

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
public class OrderDto {

	@JsonProperty(value = "id")
	@NotNull
	private Integer id;
	
	@JsonProperty(value = "nombre")
	@NotNull
	private String name;
	
	@JsonProperty(value = "fecha")
	private LocalDate date;
	
	@JsonProperty(value = "articulos")
	private List<OrderItemDto> orderItems;

	
}
