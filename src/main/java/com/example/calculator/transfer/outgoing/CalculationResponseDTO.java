package com.example.calculator.transfer.outgoing;

import com.fasterxml.jackson.annotation.JsonInclude;

// JSON Include is used to keep null fields in the serialized JSON

@JsonInclude(JsonInclude.Include.ALWAYS)
public record CalculationResponseDTO(Number result, String errorMessage, boolean success) {
}
