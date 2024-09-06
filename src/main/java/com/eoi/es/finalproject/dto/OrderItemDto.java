package com.eoi.es.finalproject.dto;

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
public class OrderItemDto {
	@JsonProperty(value = "id")
    private Integer productId;

    @JsonProperty(value = "cantidad")
    private Integer quantity;
}
