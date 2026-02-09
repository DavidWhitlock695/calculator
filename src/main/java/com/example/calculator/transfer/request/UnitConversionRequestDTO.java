package com.example.calculator.transfer.request;

import java.math.BigDecimal;

public record UnitConversionRequestDTO(BigDecimal value, Long fromUnitId, Long toUnitId) {}
