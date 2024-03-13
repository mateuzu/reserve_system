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

import com.apireserve.dto.RoomPostDto;
import com.apireserve.dto.RoomUpdateDto;
import com.apireserve.entities.Room;
import com.apireserve.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/room")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {

	private RoomService roomService;
	
	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}
	
	@PostMapping
	public ResponseEntity<Void> postRoom(@RequestBody @Valid RoomPostDto roomPostDto) {
		var roomId = roomService.createRoom(roomPostDto);
		return ResponseEntity.created(URI.create("api/v1/room/" + roomId.toString())).build();
	}
	
	@GetMapping("/get/{roomId}")
	public ResponseEntity<Room> getRoomById(@PathVariable("roomId") String roomId){
		var room = roomService.getRoomById(roomId);
		return ResponseEntity.ok().body(room);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Room>> listAllRooms(){
		return ResponseEntity.ok().body(roomService.listAll());
	}
	
	@GetMapping("/available")
	public ResponseEntity<List<Room>> listAllRoomsAvailable(){
		var list = roomService.listAllAvailable();
		return ResponseEntity.ok().body(list);
	}
	
	@PutMapping("/update/{roomId}")
	public ResponseEntity<RoomUpdateDto> updateRoom(@PathVariable("roomId") String roomId, @RequestBody @Valid RoomUpdateDto roomUpdateDto){
		var room = roomService.updateRoom(roomId, roomUpdateDto);
		return ResponseEntity.ok().body(room);
	}
	
	@DeleteMapping("/delete/{roomId}")
	public ResponseEntity<Void> deleteRoomById(@PathVariable("roomId") String roomId){
		roomService.deleteById(roomId);
		return ResponseEntity.noContent().build();
	}
}
