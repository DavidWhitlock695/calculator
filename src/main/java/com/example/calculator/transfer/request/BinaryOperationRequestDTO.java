package com.example.calculator.transfer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BinaryOperationRequestDTO(@NotNull BigDecimal operandOne, @NotNull BigDecimal operandTwo,
                                        @NotBlank String operator) {}
