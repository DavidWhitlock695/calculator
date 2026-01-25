package com.example.calculator.transfer.incoming;

import java.math.BigDecimal;

public record BinaryOperationDTO(BigDecimal operandOne, BigDecimal operandTwo, String operator) {}
