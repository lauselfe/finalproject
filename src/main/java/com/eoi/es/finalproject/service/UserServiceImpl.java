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
public class UserServiceImpl implements UserService{

	@Autowired
	UsersRepository usersRepository; 
	@Override
	public List<UserDto> findAllUsers() {
		List<UserDto> dtos= new ArrayList<UserDto>();		
		List<User> entities=usersRepository.findAll();
				
		for (User user : entities) {
			UserDto dto= new UserDto();
			dto.setId(user.getId());
			dto.setName(user.getName());
			dto.setPassword(user.getPassword());
			
			dtos.add(dto);
		}
				
		return dtos;
	}

	@Override
	public UserDto createUser(UserDto dto) {
		if (usersRepository.existsByName(dto.getName())) {
	        throw new RuntimeException("User with name " + dto.getName() + " already exists.");
	    }

	    // Mapeo de UserDto a User
	    User user = new User();
	    user.setName(dto.getName());
	    user.setPassword(dto.getPassword());

	    // Guardar el usuario en la base de datos
	    User savedUser = usersRepository.save(user);

	    // Mapeo de User (guardado) a UserDto
	    UserDto resultDto = new UserDto();
	    resultDto.setId(savedUser.getId()); 
	    resultDto.setName(savedUser.getName());
	    resultDto.setPassword(savedUser.getPassword());

	    return resultDto;
	}

	@Override
	public UserDto findUserById(Integer id) {
		 UserDto dto= new UserDto();		 
		 User entity = usersRepository.findById(id).get();
	
		 BeanUtils.copyProperties(entity,dto);		 
		 
		 return dto;	
	}

	@Override
	public UserDto updateUser(UserDto dto) {
		// Verificar si el usuario existe
	    User existingUser = usersRepository.findById(dto.getId())
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    // Actualizar los campos del usuario
	    existingUser.setName(dto.getName());
	    existingUser.setPassword(dto.getPassword());

	    // Guardar el usuario actualizado
	    User updatedUser = usersRepository.save(existingUser);

	    // Devolver el DTO actualizado
	    UserDto resultDto = new UserDto();
	    resultDto.setId(updatedUser.getId());
	    resultDto.setName(updatedUser.getName());
	    resultDto.setPassword(updatedUser.getPassword());

	    return resultDto;
	}

	@Override
	public UserDto userLogin(String name, String password) {
		// TODO Auto-generated method stub
		UserDto dto = new UserDto(); 		
		User entity=usersRepository.findByName(name);
		
		if( entity.getPassword().equals(password)) {
			BeanUtils.copyProperties(entity,dto);
			return dto; 
		}else {
			return null; 
		}
		
	}

}
