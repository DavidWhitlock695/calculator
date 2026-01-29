package com.example.calculator.controller;

import com.example.calculator.transfer.outgoing.CalculationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.transfer.incoming.BinaryOperationDTO;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

  private final CalculatorService calculatorService = new CalculatorService();

  @PostMapping("/binaryOperation")
  public ResponseEntity<CalculationResponseDTO>performBinaryOperation(@RequestBody BinaryOperationDTO request){
    try {
      BigDecimal result = calculatorService.performBinaryOperation(request);
      return ResponseEntity.ok(new CalculationResponseDTO(
          result,
          null,
          true
      ));
    }
    catch (IllegalArgumentException e){
      return ResponseEntity.badRequest().body(new CalculationResponseDTO(
          null,
          e.getMessage(),
          false
      ));
    }
    catch (Exception e) {
      return ResponseEntity.internalServerError().body(new CalculationResponseDTO(
          null,
          "An unexpected error occurred",
          false
      ));
    }
  }

  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
