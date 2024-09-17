package com.eoi.es.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eoi.es.finalproject.dto.UserDto;
import com.eoi.es.finalproject.entity.User;
import com.eoi.es.finalproject.repository.UsersRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UsersRepository usersRepository;

	@Override
	public List<UserDto> findAllUsers() {
		List<User> entities = usersRepository.findAll();
		List<UserDto> dtos = convertEntitiesToDtos(entities);

		return dtos;
	}

	@Override
	public UserDto createUser(UserDto dto) {
		if (usersRepository.existsByName(dto.getName())) {
			throw new RuntimeException("User with name " + dto.getName() + " already exists.");
		}

		User user = convertDtoToEntityNewUser(dto);
		User savedUser = usersRepository.save(user);
		UserDto resultDto = convertEntityToDto(savedUser);
		
		return resultDto;
	}

	@Override
	public UserDto findUserById(Integer id) {

		User entity = usersRepository.findById(id).orElse(new User());
		UserDto dto = convertEntityToDto(entity);

		return dto;
	}

	@Override
	public UserDto updateUser(UserDto dto) {

		User existingUser = usersRepository.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		existingUser = convertDtoToEntity(dto);
		User updatedUser = usersRepository.save(existingUser);
		UserDto resultDto = convertEntityToDto(updatedUser);

		return resultDto;
	}

	@Override
	public UserDto userLogin(String name, String password) {

		User entity = usersRepository.findByName(name);
		if (entity.getPassword().equals(password)) {
			UserDto dto = convertEntityToDto(entity);
			return dto;
		} else {
			return null;
		}

	}

	private List<UserDto> convertEntitiesToDtos(List<User> users) {
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user : users) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(user, dto);
			dtos.add(dto);
		}

		return dtos;

	}

	private User convertDtoToEntity(UserDto dto) {

		User user = new User();
		BeanUtils.copyProperties(dto, user);

		return user;
	}
	
	private User convertDtoToEntityNewUser(UserDto dto) {

		User user = new User();
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		user.setMoneyExpended(0.0);

		return user;
	}

	private UserDto convertEntityToDto(User entity) {

		UserDto dto = new UserDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

}
