package com.example.calculator.transfer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnitRequestDTO(
  @NotBlank String name,
  @NotBlank String symbol,
  @NotNull Long categoryId,
  @NotNull Boolean isBaseUnit,
  @NotNull Long conversionTypeId,
  @NotNull java.math.BigDecimal conversionToBaseFactor,
  @NotNull java.math.BigDecimal conversionToBaseOffset,
  String notes
) {}
