package com.example.calculator.service;

import com.example.calculator.exception.BusinessRuleTypes;
import com.example.calculator.exception.BusinessRuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.calculator.transfer.request.BinaryOperationRequestDTO;
import com.example.calculator.domain.BinaryOperator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

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
      throw new BusinessRuleViolationException(BusinessRuleTypes.INVALID_OPERATOR, Map.of(
              "Invalid operator: ", request.operator()));
    }
    return switch (operator) {
      case ADD -> operandOne.add(operandTwo);
      case SUBTRACT -> operandOne.subtract(operandTwo);
      case MULTIPLY -> operandOne.multiply(operandTwo);
      case DIVIDE -> {
        if (operandTwo.doubleValue() == 0) {
          throw new BusinessRuleViolationException(BusinessRuleTypes.DIVISION_BY_ZERO);
        }
        yield operandOne.divide(operandTwo, SCALE, RoundingMode.HALF_UP);
      }
    };
  }

}
