package com.example.calculator.controller;

import com.example.calculator.service.UnitCategoryService;
import com.example.calculator.transfer.request.UnitCategoryRequestDTO;
import com.example.calculator.transfer.response.UnitCategoryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit-categories")
@RequiredArgsConstructor
public class UnitCategoryController {

  private final UnitCategoryService unitCategoryService;

  @PostMapping("/create-unit-category")
  public ResponseEntity<UnitCategoryResponseDTO> createUnitCategory(@Valid @RequestBody UnitCategoryRequestDTO unitCategoryRequestDTO) {
      UnitCategoryResponseDTO responseDTO = unitCategoryService.create(unitCategoryRequestDTO);
      return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UnitCategoryResponseDTO> getUnitCategoryById(@PathVariable Long id) {
    UnitCategoryResponseDTO responseDTO = unitCategoryService.findById(id);
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UnitCategoryResponseDTO> updateUnitCategory(@PathVariable Long id, @Valid @RequestBody UnitCategoryRequestDTO unitCategoryRequestDTO) {
    UnitCategoryResponseDTO responseDTO = unitCategoryService.updateById(id, unitCategoryRequestDTO);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/all")
  public ResponseEntity<List<UnitCategoryResponseDTO>> getAllUnitCategories() {
    List<UnitCategoryResponseDTO> responseDTOs = unitCategoryService.findAll();
    return ResponseEntity.ok(responseDTOs);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUnitCategory(@PathVariable Long id) {
    unitCategoryService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
