package com.example.calculator.service;

import com.example.calculator.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.calculator.repository.UnitRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service class for handling unit conversions.
 * Only conversions between the same category are allowed.
 * For now only affine transformations are supported.
 * Conversions are always handled via the base unit of the category.
 * <p>
 * Precision Note:
 * As encountered during testing, BigDecimal arithmetic requires careful handling
 * of scale and rounding.
 * This implementation uses a fixed scale and HALF_UP rounding mode for division operations.
 * There is also a level of precision loss due to the level of precision stored for factors and offsets in the database.
 * In addition, conversion of very large to small units and vice versa may lead to precision loss
 * (e.g. light years to millimetres)
 */

@Service
// This lombok annotation generates a constructor with required arguments (for all final fields)
@RequiredArgsConstructor
public class UnitConversionService implements UnitConversionServiceInterface {

  private final UnitRepository unitRepository;
  private static final String AFFINE_CONVERSION_TYPE_NAME = "affine";
  private static final int SCALE = 10;

  @Override
  public BigDecimal convertUnitById(Long fromUnitId, Long toUnitId, BigDecimal value) {
      // Get each unit from repository
    Unit fromUnit =
            unitRepository.findById(fromUnitId).orElseThrow(() -> new IllegalArgumentException(
                    "From unit not found: " + fromUnitId));
    Unit toUnit = unitRepository.findById(toUnitId).orElseThrow(() -> new IllegalArgumentException(
            "To unit not found: " + toUnitId));
    // If the units aren't the same category, throw an exception
    if (!fromUnit.getUnitCategory().getId().equals(toUnit.getUnitCategory().getId())) {
        throw new IllegalArgumentException("Units are of different categories.");
    }
    // If either unit uses a non-affine conversion type, throw an exception
    if (!isUnitAffine(fromUnit) || !isUnitAffine(toUnit)) {
        throw new IllegalArgumentException("One or both units use a non-affine conversion type.");
    }
    // Check passed value is not null
    if (value == null) {
        throw new IllegalArgumentException("Value cannot be null.");
    }
    // Convert 'value' from 'fromUnit' to base unit
    BigDecimal valueInBaseUnit = value
            .multiply(fromUnit.getConversionToBaseFactor())
            .add(fromUnit.getConversionToBaseOffset());
    // Convert and return from base unit to 'toUnit'
    return (valueInBaseUnit
            .subtract(toUnit.getConversionToBaseOffset()))
            .divide(toUnit.getConversionToBaseFactor(),SCALE, RoundingMode.HALF_UP );
  }

  private boolean isUnitAffine(Unit unit) {
    return unit.getConversionType().getName().equalsIgnoreCase(AFFINE_CONVERSION_TYPE_NAME);
  }
}
