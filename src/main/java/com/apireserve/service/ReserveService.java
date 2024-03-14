package com.apireserve.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apireserve.dto.CreateReserveDto;
import com.apireserve.dto.ReserveResponseDto;
import com.apireserve.entities.Reserve;
import com.apireserve.entities.Room;
import com.apireserve.entities.enums.Status;
import com.apireserve.exceptions.ObjectNotFoundException;
import com.apireserve.repository.ReserveRepository;
import com.apireserve.repository.RoomRepository;
import com.apireserve.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReserveService {

	private ReserveRepository reserveRepository;
	private UserRepository userRepository;
	private RoomRepository roomRepository;
	
	@Autowired
	public ReserveService(ReserveRepository reserveRepository, UserRepository userRepository, RoomRepository roomRepository) {
		this.reserveRepository = reserveRepository;
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
	}
	
	@Transactional
	public ReserveResponseDto createReserve(String userId, CreateReserveDto createReserveDto) {
		var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ObjectNotFoundException("Usuário com Id informado não foi encontrado"));
		var room = roomRepository.findById(UUID.fromString(createReserveDto.roomCredentials())).orElseThrow(() -> new ObjectNotFoundException("Sala com Id informado não foi encontrado"));
		
		if(!user.getEmail().equals(createReserveDto.userEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail incorreto! Usuário: " + user.getName(), null);
		}
		
		if(room.getStatus() == Status.UNAVAILABLE) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A sala informada já está reservada", null);
		}
		
		double totalPrice = getTotal(room, createReserveDto);
		
		var reserve = new Reserve(
				UUID.randomUUID(), 
				user, 
				LocalDateTime.now(), 
				LocalDateTime.now().plusDays(createReserveDto.reservedDays()), 
				createReserveDto.reservedDays(), 
				room, 
				totalPrice);
		
		if(Room.associateReserve(room, reserve)) {
			roomRepository.save(room);
		}
		
		reserveRepository.save(reserve);
				
		return new ReserveResponseDto(user.getName(), room.getName(), reserve.getEndDate(), reserve.getTotalPrice());
	}
	
	public Reserve getReserveById(String reserveId) {
		var reserve = reserveRepository.findById(UUID.fromString(reserveId)).orElseThrow(() -> new ObjectNotFoundException("Reserva com Id informado não foi encontrada"));
		checkExpiredReservation(reserve);
		return reserve;
	}
	
	public List<Reserve> listAll(){
		var list = reserveRepository.findAll();
		list.forEach(this::checkExpiredReservation);
		return list;
	}
	
	public List<ReserveResponseDto> listAllByUser(String userId){
		var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ObjectNotFoundException("Usuário com Id informado não foi encontrado"));
		var list = user.getReserves().stream().map(reserve -> new ReserveResponseDto(user.getName(), reserve.getRoom().getName(), reserve.getEndDate(), reserve.getTotalPrice())).toList();
		return list;
	}
	
	public void deleteById(String reserveId) {
		var id = UUID.fromString(reserveId);
		
		if(!reserveRepository.existsById(id)) {
			throw new ObjectNotFoundException("Reserva com Id informado não foi encontrada");
		}
		
		reserveRepository.deleteById(id);
	}
	
	private Double getTotal(Room room, CreateReserveDto createReserveDto) {
		return room.getPrice() * createReserveDto.reservedDays();
	}
	
	private Boolean checkExpiredReservation(Reserve reserve) {
		if(reserve.getEndDate().isBefore(LocalDateTime.now())) {
			var room = reserve.getRoom();
			room.setStatus(Status.AVAILABLE);
			room.setCheckIn(null);
			room.setReserve(null);
			roomRepository.save(room);
			reserveRepository.save(reserve);
			return true;
		}
		return false;
	}
	
}
