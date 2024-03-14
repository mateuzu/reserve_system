package com.apireserve.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apireserve.dto.ReserveResponseDto;
import com.apireserve.dto.UserPostDto;
import com.apireserve.dto.UserResponseDto;
import com.apireserve.dto.UserUpdateDto;
import com.apireserve.entities.User;
import com.apireserve.entities.enums.UserLevel;
import com.apireserve.exceptions.ObjectNotFoundException;
import com.apireserve.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UUID createUser(UserPostDto userPostDto) {
		var user = fromDto(userPostDto);
		var emailExists = userRepository.findByEmailContainingIgnoreCase(user.getEmail());
		
		if(emailExists.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado: " + emailExists.get().getName(), null);
		}
		
		return userRepository.save(user).getUserId();
	}
	
	public User getUserById(String id) {
		var user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ObjectNotFoundException("Usuário com Id informado não foi encontrado"));
		return user;
	}
	
	public List<User> listAll(){
		return userRepository.findAll();
	}
	
	public List<User> listAllByUserLevel(UserLevel userLevel){
		var list = userRepository.findAllByUserLevel(userLevel);
		
		if(list.isEmpty()) {
			throw new ObjectNotFoundException("Nenhum usuário com o status " + userLevel.toString() + " localizado");
		}
		
		return list;
	}
	
	public UserUpdateDto updateUser(String userId, UserUpdateDto userUpdateDto) {
		var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ObjectNotFoundException("Usuário com Id informado não foi encontrado"));
		
		if(userUpdateDto.name() != null) {
			user.setName(userUpdateDto.name());
		}
		
		if(userUpdateDto.password() != null || !userUpdateDto.password().equals("")) {
			user.setPassword(userUpdateDto.password());
		}
				
		userRepository.save(user);
		
		return userUpdateDto;
	}
	
	public void deleteById(String userId) {
		var id = UUID.fromString(userId);
		
		if(!userRepository.existsById(id)) {
			throw new ObjectNotFoundException("Usuário com Id informado não foi encontrado");
		}
		
		userRepository.deleteById(id);
	}
	
	@Transactional
	public UserResponseDto userSummary(String userId) {
		var user = userRepository.findById(UUID.fromString(userId)).get();
		var amount = user.getReserves().stream().map(reservePrice -> reservePrice.getTotalPrice()).reduce((x, y) -> x + y).get();
		
		var discount = 0.0;
		var userTotalPrice = 0.0;
		
		var reservedDays = user.getReserves().stream().map(reserveDays -> reserveDays.getDays()).reduce((x, y) -> x + y).get();
		
		var reserves = user.getReserves().stream().map(reserve -> new ReserveResponseDto(reserve.getUser().getName(), reserve.getRoom().getName(), reserve.getEndDate(), reserve.getTotalPrice())).toList();
		
		if(user.getUserLevel() == UserLevel.BRONZE) {
			discount = 5.0/100.0;
			userTotalPrice = amount - (amount * discount);
		} else if (user.getUserLevel() == UserLevel.SILVER) {
			discount = 10.0/100.0;
			userTotalPrice = amount - (amount * discount);
		} else {
			discount = 15.0/100.0;
			userTotalPrice = amount - (amount * discount);
		}
		
		user.setTotalPriceUser(userTotalPrice);
		userRepository.save(user);
		
		var userResponse = new UserResponseDto(
				user.getName(), 
				user.getEmail(), 
				reservedDays,
				reserves,
				user.getUserLevel(),
				discount,
				userTotalPrice
				);
		
		return userResponse;
	}
	
	private User fromDto(UserPostDto userPostDto) {
		var user = new User(UUID.randomUUID(), userPostDto.name(), userPostDto.email(), userPostDto.password(), userPostDto.userLevel(), 0.0, null, null);
		return user;
	}
}
