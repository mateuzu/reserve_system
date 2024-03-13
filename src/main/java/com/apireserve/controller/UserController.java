package com.apireserve.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apireserve.dto.UserPostDto;
import com.apireserve.dto.UserResponseDto;
import com.apireserve.dto.UserUpdateDto;
import com.apireserve.entities.User;
import com.apireserve.entities.enums.UserLevel;
import com.apireserve.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<Void> postUser(@RequestBody @Valid UserPostDto userPostDto){
		var userId = userService.createUser(userPostDto);
		return ResponseEntity.created(URI.create("api/v1/users/" + userId.toString())).build();
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
		var user = userService.getUserById(userId);
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> listAllUsers(){
		return ResponseEntity.ok().body(userService.listAll());
	}
	
	@GetMapping("/level/{userLevel}")
	public ResponseEntity<List<User>> listAllUsersByUserLevel(@PathVariable("userLevel") UserLevel userLevel){
		var list = userService.listAllByUserLevel(userLevel);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/summary/{userId}")
	public ResponseEntity<UserResponseDto> userSummary(@PathVariable("userId") String userId){
		var user = userService.userSummary(userId);
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserUpdateDto> updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UserUpdateDto updateDto){
		var user = userService.updateUser(userId, updateDto);
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId){
		userService.deleteById(userId);
		return ResponseEntity.noContent().build();
	}
		
}
