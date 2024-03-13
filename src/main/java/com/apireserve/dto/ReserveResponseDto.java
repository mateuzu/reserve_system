package com.apireserve.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ReserveResponseDto(
		String userName, 
		String roomName, 
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime endDate,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00", locale = "pt-BR") Double price) {

}
