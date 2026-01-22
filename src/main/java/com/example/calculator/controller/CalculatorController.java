package com.example.calculator.controller;

import com.example.calculator.transfer.outgoing.CalculationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.calculator.transfer.incoming.NumberListDTO;
import com.example.calculator.validation.NumberListValidator;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

  @PostMapping("/add")
  public ResponseEntity<CalculationResponseDTO>add(@RequestBody NumberListDTO numberList){
    try {
      NumberListValidator.validate(numberList);
      double sum = numberList.numbers().stream()
          .mapToDouble(Number::doubleValue)
          .sum();
      return ResponseEntity.ok(new CalculationResponseDTO(
          sum,
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
      return ResponseEntity.badRequest().body(new CalculationResponseDTO(
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
