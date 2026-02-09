package com.example.calculator.transfer.request;

import jakarta.validation.constraints.NotBlank;

public record UnitCategoryRequestDTO(
        @NotBlank String name
) {}

