package com.example.calculator.service;

import org.springframework.stereotype.Service;
import com.example.calculator.transfer.incoming.BinaryOperationDTO;
import com.example.calculator.domain.BinaryOperator;

@Service
public class CalculatorService {

  public Number performBinaryOperation(BinaryOperationDTO request) {
    Number operandOne = request.operandOne();
    Number operandTwo = request.operandTwo();
    BinaryOperator operator;
    try {
      operator = BinaryOperator.valueOf(request.operator());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid operator: " + request.operator());
    }
    return switch (operator) {
      case ADD -> operandOne.doubleValue() + operandTwo.doubleValue();
      case SUBTRACT -> operandOne.doubleValue() - operandTwo.doubleValue();
      case MULTIPLY -> operandOne.doubleValue() * operandTwo.doubleValue();
      case DIVIDE -> {
        if (operandTwo.doubleValue() == 0) {
          throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        yield operandOne.doubleValue() / operandTwo.doubleValue();
      }
    };
  }

}
