package com.example.calculator.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.ALWAYS)
public record UnitResponseDTO(
        Long id,
        String name,
        String symbol,
        Long categoryId,
        Boolean isBaseUnit,
        Long conversionTypeId,
        BigDecimal conversionToBaseFactor,
        BigDecimal conversionToBaseOffset,
        String notes) {}
