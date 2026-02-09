package com.example.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.calculator.transfer.request.BinaryOperationRequestDTO;
import com.example.calculator.domain.BinaryOperator;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CalculatorService {

  private static final int SCALE = 10;

  public BigDecimal performBinaryOperation(BinaryOperationRequestDTO request) {
    BigDecimal operandOne = request.operandOne();
    BigDecimal operandTwo = request.operandTwo();
    BinaryOperator operator;
    try {
      operator = BinaryOperator.valueOf(request.operator());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid operator: " + request.operator());
    }
    return switch (operator) {
      case ADD -> operandOne.add(operandTwo);
      case SUBTRACT -> operandOne.subtract(operandTwo);
      case MULTIPLY -> operandOne.multiply(operandTwo);
      case DIVIDE -> {
        if (operandTwo.doubleValue() == 0) {
          throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        yield operandOne.divide(operandTwo, SCALE, RoundingMode.HALF_UP);
      }
    };
  }

}
