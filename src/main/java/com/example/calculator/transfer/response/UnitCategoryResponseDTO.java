package com.example.calculator.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public record UnitCategoryResponseDTO(
        Long id,
        String name
) {}

