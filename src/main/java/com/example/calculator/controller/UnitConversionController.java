package com.example.calculator.controller;

import com.example.calculator.service.UnitConversionService;
import com.example.calculator.transfer.request.UnitConversionRequestDTO;
import com.example.calculator.transfer.response.CalculationResponseDTO;
import jakarta.validation.Valid;
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
  public ResponseEntity<CalculationResponseDTO> convertUnitById(@Valid @RequestBody UnitConversionRequestDTO unitConversionRequestDTO) {
    try{
      BigDecimal result = unitConversionService.convertUnitById(
              unitConversionRequestDTO.fromUnitId(),
              unitConversionRequestDTO.toUnitId(),
              unitConversionRequestDTO.value()
      );
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
    catch (IllegalStateException e){
      return ResponseEntity.status(409).body(new CalculationResponseDTO(
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
