package com.apireserve.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateReserveDto(
		@Email(message = "Email no formato inválido") String userEmail, 
		@NotBlank(message = "O identificador da sala não pode estar vazio") String roomCredentials, 
		@Positive Integer reservedDays) {

}
