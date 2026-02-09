package com.example.calculator.validation;

import com.example.calculator.service.UnitCategoryService;
import com.example.calculator.service.UnitConversionTypeService;
import com.example.calculator.service.UnitService;
import com.example.calculator.transfer.request.UnitRequestDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnitValidator {
  // Basic data validation is already done by the DTO
  // Steps for validating a unit creation request:
  // 1. Ensure name isn't taken
  // 2. Ensure symbol isn't taken
  // 3. Ensure categoryId exists
  // 4. If isBaseUnit is true, ensure no other base unit exists for the category
  // 5. Ensure conversionTypeId exists
  // 6. Ensure conversionToBaseFactor is a positive number
  // 7. If notes are present ensure they're not an empty string

  private final UnitService unitService;
  private final UnitCategoryService unitCategoryService;
  private final UnitConversionTypeService unitConversionTypeService;

  public boolean validateNewUnitRequest(UnitRequestDTO unitRequestDTO) {
    // Implement the validation logic here, possibly by calling the service layer to check for existing units and categories.
    return true; // Placeholder return value
  }

}
