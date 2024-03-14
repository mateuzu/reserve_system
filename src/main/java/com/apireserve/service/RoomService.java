package com.apireserve.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apireserve.dto.RoomPostDto;
import com.apireserve.dto.RoomUpdateDto;
import com.apireserve.entities.Room;
import com.apireserve.entities.enums.Status;
import com.apireserve.exceptions.ObjectNotFoundException;
import com.apireserve.repository.RoomRepository;

@Service
public class RoomService {

	final RoomRepository roomRepository;
	
	@Autowired
	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}
	
	public UUID createRoom(RoomPostDto roomPostDto) {
		var room = Room.fromDto(roomPostDto);
		return roomRepository.save(room).getRoomId();
	}
	
	public Room getRoomById(String roomId) {
		var room = roomRepository.findById(UUID.fromString(roomId)).orElseThrow(() -> new ObjectNotFoundException("Sala com Id informado não foi encontrada"));
		return room;
	}
	
	public List<Room> listAll(){
		var list = roomRepository.findAll();
		
		return list;
	}
	
	public List<Room> listAllAvailable(){
		var list = roomRepository.findAllByStatus(Status.AVAILABLE);
		
		if(list.isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma sala disponível");
		}
		
		return list;
	}
	
	public RoomUpdateDto updateRoom (String roomId, RoomUpdateDto roomUpdateDto) {
		var room = roomRepository.findById(UUID.fromString(roomId)).orElseThrow(() -> new ObjectNotFoundException("Sala com Id informado não foi encontrada"));
		
		if(roomUpdateDto.name() != null) {
			room.setName(roomUpdateDto.name());
		}
		
		if(roomUpdateDto.description() != null) {
			room.setDescription(roomUpdateDto.description());
		}
		
		if(roomUpdateDto.price() != null) {
			room.setPrice(roomUpdateDto.price());
		}
		
		roomRepository.save(room);
		
		return roomUpdateDto;
	}
	
	public void deleteById(String roomId) {
		var id = UUID.fromString(roomId);
		
		if(!roomRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		roomRepository.deleteById(id);
	}
	
}
