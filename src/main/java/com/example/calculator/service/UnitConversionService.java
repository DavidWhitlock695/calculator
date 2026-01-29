package com.example.calculator.service;

import com.example.calculator.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.calculator.repository.UnitRepository;

/**
 * Service class for handling unit conversions.
 * Only conversions between the same category are allowed.
 * For now only affine transformations are supported.
 * Conversions are always handled via the base unit of the category.
 */

@Service
// This lombok annotation generates a constructor with required arguments (for all final fields)
@RequiredArgsConstructor
public class UnitConversionService implements UnitConversionServiceInterface {

  private final UnitRepository unitRepository;
  private static final String AFFINE_CONVERSION_TYPE_NAME = "affine";

  @Override
  public double convertUnitById(Long fromUnitId, Long toUnitId, double value) {
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
    // Convert 'value' from 'fromUnit' to base unit
    double valueInBaseUnit = (value * fromUnit.getConversionToBaseFactor())
            + fromUnit.getConversionToBaseOffset();
    // Convert and return from base unit to 'toUnit'
    return (valueInBaseUnit - toUnit.getConversionToBaseOffset())
            / toUnit.getConversionToBaseFactor();
  }

  private boolean isUnitAffine(Unit unit) {
    return unit.getConversionType().getName().equalsIgnoreCase(AFFINE_CONVERSION_TYPE_NAME);
  }
}
