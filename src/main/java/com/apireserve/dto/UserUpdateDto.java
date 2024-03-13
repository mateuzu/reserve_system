package com.apireserve.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto (
		@NotBlank(message = "O nome do usuário não pode estar vazio") String name,
		String password){

}
