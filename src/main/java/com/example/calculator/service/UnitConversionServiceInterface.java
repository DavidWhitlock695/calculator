package com.example.calculator.service;

import java.math.BigDecimal;

public interface UnitConversionServiceInterface {
  BigDecimal convertUnitById(Long fromUnitId, Long toUnitId, BigDecimal value);
  // Later add by symbol/name
}
