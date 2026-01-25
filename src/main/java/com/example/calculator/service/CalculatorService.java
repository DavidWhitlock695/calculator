package com.example.calculator.service;

import org.springframework.stereotype.Service;
import com.example.calculator.transfer.incoming.BinaryOperationDTO;
import com.example.calculator.domain.BinaryOperator;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

  public BigDecimal performBinaryOperation(BinaryOperationDTO request) {
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
        yield operandOne.divide(operandTwo, 10, RoundingMode.HALF_UP);
      }
    };
  }

}
