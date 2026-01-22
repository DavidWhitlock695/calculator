// Validator class
package com.example.calculator.validation;

import com.example.calculator.transfer.incoming.NumberListDTO;

public class NumberListValidator {
  public static void validate(NumberListDTO dto) {
    if (dto.numbers() == null) throw new IllegalArgumentException("numbers must not be null");
    if (dto.numbers().isEmpty()) throw new IllegalArgumentException("numbers list must not be empty");
    if (dto.numbers().size() > 12) throw new IllegalArgumentException("numbers list must not exceed 12 elements");
    if (dto.numbers().contains(null)) throw new IllegalArgumentException("numbers list must not contain null elements");
  }
}
