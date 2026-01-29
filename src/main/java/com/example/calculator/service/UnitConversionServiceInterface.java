package com.example.calculator.service;

public interface UnitConversionServiceInterface {
  double convertUnitById(Long fromUnitId, Long toUnitId, double value);
  // Later add by symbol/name
}
