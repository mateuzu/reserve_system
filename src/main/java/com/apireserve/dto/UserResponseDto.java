package com.apireserve.dto;

import java.util.List;

import com.apireserve.entities.enums.UserLevel;
import com.fasterxml.jackson.annotation.JsonFormat;

public record UserResponseDto(
		String name,
		String email,
		Integer reservedDays,
		List<ReserveResponseDto> reserves,
		UserLevel level,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00", locale = "pt-BR")Double discount,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00", locale = "pt-BR")Double totalUserPrice) {

}
