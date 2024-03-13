package com.apireserve.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apireserve.dto.UserPostDto;
import com.apireserve.entities.User;
import com.apireserve.entities.enums.UserLevel;
import com.apireserve.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Captor
	private ArgumentCaptor<User> userArgumentCaptor;
	
	@Captor
	private ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	@Nested
	class createUaer {
		
		@Test
		@DisplayName("Deve criar um usuário com sucesso")
		public void shouldCreateAUserWithSuccess() {
			
			//Arrange
			var user = new User(UUID.randomUUID(), "name", "email", "password", UserLevel.BRONZE ,0.0, null, null);
			doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
			var input = new UserPostDto("name", "email", "password", UserLevel.BRONZE);
			
			//Act
			var output = userService.createUser(input);
			
			//Assert
			assertNotNull(output);
			var userCaptured = userArgumentCaptor.getValue();
			assertEquals(input.name(), userCaptured.getName());
			assertEquals(input.email(), userCaptured.getEmail());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando algum erro ocorrer")
		public void shouldThrowExceptionWhenErrorOccurs() {
			doThrow(new RuntimeException()).when(userRepository).save(any());
			var input = new UserPostDto("name", "email", "password", UserLevel.BRONZE);
			
			//Act & Assert
			assertThrows(RuntimeException.class, () -> userService.createUser(input));
		}
	}
	
	@Nested
	class getUserById{
		
		@Test
		@DisplayName("Deve buscar um usuário com sucesso")
		public void shouldGetUserWithSuccess() {
			
			//Arrange
			var user = new User(UUID.randomUUID(), "name", "email",  "password", UserLevel.BRONZE ,0.0, null, null);
			doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
			
			//Act
			var output = userService.getUserById(user.getUserId().toString());
			
			//Assert
			assertNotNull(output);
			assertEquals(output.getUserId(), uuidArgumentCaptor.getValue());
		}
	}
	
}
