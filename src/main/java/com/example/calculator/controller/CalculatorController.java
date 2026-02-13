package com.example.calculator.controller;

import com.example.calculator.transfer.response.CalculationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.transfer.request.BinaryOperationRequestDTO;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

  private final CalculatorService calculatorService = new CalculatorService();

  @PostMapping("/binaryOperation")
  public ResponseEntity<CalculationResponseDTO>performBinaryOperation(@RequestBody BinaryOperationRequestDTO request){
      BigDecimal result = calculatorService.performBinaryOperation(request);
      return ResponseEntity.ok(new CalculationResponseDTO(result));
  }

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
