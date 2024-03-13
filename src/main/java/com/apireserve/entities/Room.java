package com.apireserve.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.apireserve.dto.RoomPostDto;
import com.apireserve.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_room")
public class Room implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID roomId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "check-in")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime checkIn;
	
	@OneToOne
	@JsonIgnoreProperties("room")
	private Reserve reserve;
	
	public Room() {
		// TODO Auto-generated constructor stub
	}
	
	public Room(UUID roomId, String name, String description, Double price, Status status,
		LocalDateTime checkIn) {
	this.roomId = roomId;
	this.name = name;
	this.description = description;
	this.price = price;
	this.status = status;
	this.checkIn = checkIn;
	}

	public UUID getRoomId() {
		return roomId;
	}

	public void setRoomId(UUID roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}
	
	public Reserve getReserve() {
		return reserve;
	}

	public void setReserve(Reserve reserve) {
		this.reserve = reserve;
	}

	public static Room fromDto(RoomPostDto roomPostDto) {
		var room = new Room(UUID.randomUUID(), roomPostDto.name(), roomPostDto.description(), roomPostDto.price() ,Status.AVAILABLE, null);
		return room;
	}
	
	public static boolean associateReserve(Room room, Reserve reserve) {
		room.setStatus(Status.UNAVAILABLE);
		room.setCheckIn(LocalDateTime.now());
		room.setReserve(reserve);
		return true;
	}
}
