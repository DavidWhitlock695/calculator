package com.example.calculator.transfer.incoming;

import java.math.BigDecimal;

public record UnitConversionDTO(BigDecimal value, Long fromUnitId, Long toUnitId) {}
