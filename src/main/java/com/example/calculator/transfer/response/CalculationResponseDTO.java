package com.example.calculator.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

// JSON Include is used to keep null fields in the serialized JSON

@JsonInclude(JsonInclude.Include.ALWAYS)
public record CalculationResponseDTO(BigDecimal result) {
}
