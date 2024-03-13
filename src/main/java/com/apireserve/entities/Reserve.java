package com.apireserve.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_reserve")
public class Reserve implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID reserveId;
	
	@ManyToOne
	@JsonIgnoreProperties("reserves")
	private User user;
	
	@Column(name = "start-date")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime startDate;
	
	@Column(name = "end-date")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime endDate;
	
	@Column(name = "days")
	private Integer days;
	
	@OneToOne(mappedBy = "reserve", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("reserve")
	private Room room;
	
	@Column(name = "total-price")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00", locale = "pt-BR")
	private Double totalPrice;
	
	public Reserve() {
		// TODO Auto-generated constructor stub
	}

	public Reserve(UUID reserveId, User user, LocalDateTime startDate, LocalDateTime endDate, Integer days, Room room,
			Double totalPrice) {
		this.reserveId = reserveId;
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.room = room;
		this.totalPrice = totalPrice;
	}
	
	public UUID getReserveId() {
		return reserveId;
	}

	public void setReserveId(UUID reserveId) {
		this.reserveId = reserveId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
