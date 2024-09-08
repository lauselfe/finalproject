package com.eoi.es.finalproject.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eoi.es.finalproject.dto.UserDto;
import com.eoi.es.finalproject.service.UserServiceImpl;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/marketplace/usuarios")
public class UserController {

	@Autowired
	UserServiceImpl userService; 
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable String id) {	
		
		Integer userId = Integer.parseInt(id); 
		return new ResponseEntity<UserDto>(userService.findUserById(userId),HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> findAllUsers() {	
		
		return new ResponseEntity<List<UserDto>>(userService.findAllUsers(), HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user, BindingResult result) {
	    if (result.hasErrors()) {
	        System.out.println("Hay campos incorrectos");
	        System.out.println("Errores: " + result.getAllErrors());
	        return new ResponseEntity<UserDto>(user, HttpStatus.BAD_REQUEST);
	    } else {
	        System.out.println("Datos recibidos: " + user);
	        UserDto userDto = userService.createUser(user);
	        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
	    }
	
		
}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto user, @PathVariable String id,BindingResult result) {

		
		if(id.equals(user.getId())||result.hasErrors()) {
			System.out.println("hay campos incorrectos");
			System.out.println("errores: " + result.getAllErrors());		
			
			return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
		}
		else {

			UserDto userDto = userService.updateUser(user);
			return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
		}
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<UserDto> userLogin(@RequestParam String name, @RequestParam String password){
		UserDto user = userService.userLogin(name, password); 
		return new ResponseEntity<UserDto>(user, HttpStatus.OK); 
	}
	
}
