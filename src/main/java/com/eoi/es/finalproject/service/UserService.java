package com.eoi.es.finalproject.service;

import java.util.List;

import com.eoi.es.finalproject.dto.UserDto;


public interface UserService {

	public List<UserDto> findAllUsers(); 
	
	public UserDto findUserById(Integer id); 
	
	public UserDto createUser(UserDto dto); 
	
	public UserDto updateUser(UserDto dto); 
	
	public UserDto userLogin(String name, String password); 
	
	
}
