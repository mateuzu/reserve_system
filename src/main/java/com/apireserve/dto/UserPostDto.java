package com.apireserve.dto;

import com.apireserve.entities.enums.UserLevel;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPostDto(
		@NotBlank(message = "O nome do usuário não pode estar vazio") String name, 
		@NotBlank(message = "O email do usuário não pode estar vazio") @Email(message = "Email no formato inválido") String email,
		@NotBlank(message = "A senha do usuário não pode estar vazia") String password,
		@Enumerated(EnumType.STRING) UserLevel userLevel) {

}
