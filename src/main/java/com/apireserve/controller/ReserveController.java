package com.apireserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apireserve.dto.CreateReserveDto;
import com.apireserve.dto.ReserveResponseDto;
import com.apireserve.entities.Reserve;
import com.apireserve.service.ReserveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reserve")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReserveController {

	private ReserveService reserveService;
	
	@Autowired
	public ReserveController(ReserveService reserveService) {
		this.reserveService = reserveService;
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<ReserveResponseDto> postReserve(@PathVariable("userId")String userId, @RequestBody @Valid CreateReserveDto createReserveDto){
		var reserve = reserveService.createReserve(userId, createReserveDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(reserve);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Reserve>> listAllReserves(){
		return ResponseEntity.ok().body(reserveService.listAll());
	}
	
	@DeleteMapping("/delete/{reserveId}")
	public ResponseEntity<Void> deleteReserveById(@PathVariable("reserveId") String reserveId){
		reserveService.deleteById(reserveId);
		return ResponseEntity.noContent().build();
	}
}
