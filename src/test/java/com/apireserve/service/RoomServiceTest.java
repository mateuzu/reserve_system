package com.apireserve.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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

import com.apireserve.dto.RoomPostDto;
import com.apireserve.entities.Room;
import com.apireserve.entities.enums.Status;
import com.apireserve.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

	@Mock
	private RoomRepository roomRepository;
	
	@InjectMocks
	private RoomService roomService;
	
	@Captor
	private ArgumentCaptor<Room> roomArgumentCaptor;
	
	@Nested
	class saveRoom{
		
		@Test
		@DisplayName("Deve criar uma sala com sucesso")
		public void shouldSaveARoomWithSuccess() {
			
			//Arrange
			var room = new Room(UUID.randomUUID(), "name", "description", 100.00, Status.UNAVAILABLE, null);
			doReturn(room).when(roomRepository).save(roomArgumentCaptor.capture());
			var input = new RoomPostDto("name", "description",100.00);
			
			//Act
			var output = roomService.createRoom(input);
			
			//Assert
			assertNotNull(output);
			var roomCaptured = roomArgumentCaptor.getValue();
			assertEquals(input.name(), roomCaptured.getName());
			assertEquals(input.description(), roomCaptured.getDescription());
		}
		
		@Test
		@DisplayName("Deve lançar uma exceção quando ocorrer algum erro")
		public void shouldThrowExceptionWhenErrorOccurs() {
		
			//Arrange
			doThrow(new RuntimeException()).when(roomRepository).save(any());
			var input = new RoomPostDto("name", "description", 10.00);
			
			//Act & Arrange
			assertThrows(RuntimeException.class, () -> roomService.createRoom(input));
		}
	}
}
