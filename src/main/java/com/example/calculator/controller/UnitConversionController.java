package com.example.calculator.controller;

import com.example.calculator.service.UnitConversionService;
import com.example.calculator.transfer.incoming.UnitConversionDTO;
import com.example.calculator.transfer.outgoing.CalculationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/unitConversion")
@RequiredArgsConstructor
public class UnitConversionController {

  private final UnitConversionService unitConversionService;

  @PostMapping("/convertUnitById")
  public ResponseEntity<CalculationResponseDTO> convertUnitById(@RequestBody UnitConversionDTO unitConversionDTO) {
    try{
      BigDecimal result = unitConversionService.convertUnitById(
              unitConversionDTO.fromUnitId(),
              unitConversionDTO.toUnitId(),
              unitConversionDTO.value()
      );
      System.out.println(result);
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

}
