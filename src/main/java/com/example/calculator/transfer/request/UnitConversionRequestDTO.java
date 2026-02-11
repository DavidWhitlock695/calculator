package com.example.calculator.transfer.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UnitConversionRequestDTO(@NotNull BigDecimal value,@NotNull Long fromUnitId,
                                       @NotNull Long toUnitId) {}
