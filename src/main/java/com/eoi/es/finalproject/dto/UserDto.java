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
public class UserDto {

	@JsonProperty("id")
	@NotNull
	private Integer id;
	
	@JsonProperty("nombre")
	@NotNull
	private String name;
	
	@JsonProperty("password")
	private String password;
	
//	@JsonGetter("password")
//	public String getCensoredPassword() {
//	return (password != null && !password.isEmpty()) ? "****" : null;
//	}
}
	
