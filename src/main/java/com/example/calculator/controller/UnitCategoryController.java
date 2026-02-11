package com.example.calculator.controller;

import com.example.calculator.service.UnitCategoryService;
import com.example.calculator.transfer.request.UnitCategoryRequestDTO;
import com.example.calculator.transfer.response.UnitCategoryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unit-categories")
@RequiredArgsConstructor
public class UnitCategoryController {

  private final UnitCategoryService unitCategoryService;

  @PostMapping("/create-unit-category")
  public ResponseEntity<UnitCategoryResponseDTO> createUnitCategory(@Valid @RequestBody UnitCategoryRequestDTO unitCategoryRequestDTO) {
    try {
      UnitCategoryResponseDTO responseDTO = unitCategoryService.create(unitCategoryRequestDTO);
      return ResponseEntity.ok(responseDTO);
      // TODO: Specific exceptions to be caught properly here
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
