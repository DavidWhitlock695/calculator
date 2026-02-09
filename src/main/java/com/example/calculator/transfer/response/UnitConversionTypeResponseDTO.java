package com.example.calculator.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public record UnitConversionTypeResponseDTO(
        Long id,
        String name,
        String description
) {}
