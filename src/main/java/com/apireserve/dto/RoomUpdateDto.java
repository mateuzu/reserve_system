package com.apireserve.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RoomUpdateDto(
		@NotBlank(message = "O nome da sala não pode ficar vazio") String name, 
		@Size(max = 50, message = "Descrição excedeu o limite de caracteres") String description,
		@Positive(message = "O preço da sala deve ser maior que zero") Double price) {

}
