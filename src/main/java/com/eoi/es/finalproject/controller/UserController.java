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
		UserDto user = userService.findUserById(userId);
		ResponseEntity<UserDto> response;

		if (user.getId() != null) {
			response = new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;

	}

	@GetMapping
	public ResponseEntity<List<UserDto>> findAllUsers() {
		ResponseEntity<List<UserDto>> response;
		List<UserDto> users = userService.findAllUsers();
		if (users.isEmpty()) {
			response = new ResponseEntity<List<UserDto>>(users, HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
		}
		return response;

	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user, BindingResult result) {

		ResponseEntity<UserDto> response;
		if (result.hasErrors()) {
			System.out.println("Errores: " + result.getAllErrors());
			response = new ResponseEntity<UserDto>(user, HttpStatus.BAD_REQUEST);
		} else {
			UserDto userDto = userService.createUser(user);
			response = new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
		}

		return response;

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto user, @PathVariable String id, BindingResult result) {
		Integer userId = Integer.parseInt(id);
		ResponseEntity<?> response;
		if (!userId.equals(user.getId()) || result.hasErrors()) {
			System.out.println("errores: " + result.getAllErrors());
			response = new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
		} else {
			UserDto userDto = userService.updateUser(user);
			response = new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
		}
		return response;
	}

	@PostMapping(value = "/login")
	public ResponseEntity<UserDto> userLogin(@RequestParam String name, @RequestParam String password) {
		ResponseEntity<UserDto> response;
		UserDto user = userService.userLogin(name, password);
		if (user != null) {
			response = new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return response;
	}

}
