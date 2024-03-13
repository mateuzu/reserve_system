package com.apireserve.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.apireserve.entities.enums.UserLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	
	@Column(name = "name")
	private String name;
	
	@Schema(example = "email@email.com")
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("user")
	private List<Reserve> reserves;
	
	@Column(name = "user-level")
	@Enumerated(EnumType.STRING)
	private UserLevel userLevel;
	
	@Column(name = "total-price-user")
	@JsonIgnore
	private Double totalPriceUser;
		
	@CreationTimestamp
	@JsonIgnore
	private Instant creationTimestamp;
	
	@UpdateTimestamp
	@JsonIgnore
	private Instant updateTimestamp;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(UUID userId, String name, String email, String password, UserLevel userLevel, Double totalPriceUser, Instant creationTimestamp, Instant updateTimestamp) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userLevel = userLevel;
		this.totalPriceUser = totalPriceUser;
		this.creationTimestamp = creationTimestamp;
		this.updateTimestamp = updateTimestamp;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Reserve> getReserves() {
		return reserves;
	}

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public Double getTotalPriceUser() {
		return totalPriceUser;
	}

	public void setTotalPriceUser(Double totalPriceUser) {
		this.totalPriceUser = totalPriceUser;
	}
	
	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Instant getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Instant updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	
}