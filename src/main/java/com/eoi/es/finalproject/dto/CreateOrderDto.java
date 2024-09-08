package com.eoi.es.finalproject.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CreateOrderDto {

	 @JsonProperty(value = "id")
	    private Integer id;
	 
	 	@NotNull
	    @JsonProperty(value = "nombre")
	    private String name;
	    
	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    @JsonProperty(value = "fecha")
	    private LocalDate date;
		@NotNull
	    @JsonProperty(value = "idusuario")
	    private Integer userId;

	    @JsonProperty(value = "articulos")
	    private List<OrderItemDto> orderItems; 
}