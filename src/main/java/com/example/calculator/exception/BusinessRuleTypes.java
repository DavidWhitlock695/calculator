package com.example.calculator.exception;

import lombok.Getter;

@Getter
public enum BusinessRuleTypes {
  // Conversion rules
  DIVISION_BY_ZERO("Division by zero is not allowed."),
  INVALID_OPERATOR("Invalid operator. Supported operators are: +, -, *, /."),
  INVALID_CONVERSION_UNIT_CATEGORIES_COMBINATION("Conversion can only occur between units of the " +
          "same category."),
  INVALID_CONVERSION_TYPE("One or more units in the conversion request require unsupported " +
          "conversion types. Currently only affine conversions are supported."),

  // Unit creation rules - note I'm not including rules like an existing entity
  // (covered by EntityAlreadyExistsException)
  BASE_UNIT_MUST_HAVE_FACTOR_OF_ONE("A base unit must have a conversion factor of 1."),
  BASE_UNIT_MUST_HAVE_OFFSET_OF_ZERO("A base unit must have a conversion offset of 0.");

  private final String message;

BusinessRuleTypes(String message) { this.message = message; }
}
