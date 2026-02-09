package com.example.calculator.transfer.request;

import jakarta.validation.constraints.NotBlank;

public record UnitConversionTypeRequestDTO(
        @NotBlank String name,
        @NotBlank String description
) {}

