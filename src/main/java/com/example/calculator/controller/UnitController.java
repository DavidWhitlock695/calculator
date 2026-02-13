package com.example.calculator.controller;

import com.example.calculator.service.UnitService;
import com.example.calculator.transfer.request.UnitRequestDTO;
import com.example.calculator.transfer.response.UnitResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

  private final UnitService unitService;
  // Crud operations for units

  // Notice the use of the Valid annotation.
  // This ensures that the incoming request body is validated against the constraints defined in the Unit entity (e.g., @NotNull, @Size, etc.). If the validation fails,
  // Spring will automatically return a 400 Bad Request response with details about the validation errors.
  // It includes nested validation for UnitCategory and UnitConversionType.
  // Annotations like NotNull are only active when the Valid annotation is present on the method parameter.

  @PostMapping("/create")
  public ResponseEntity<UnitResponseDTO> createUnit(@Valid @RequestBody UnitRequestDTO newUnit){
    UnitResponseDTO responseDTO = unitService.create(newUnit);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UnitResponseDTO> getUnitById(@PathVariable Long id) {
    UnitResponseDTO responseDTO = unitService.findById(id);
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UnitResponseDTO> updateUnit(@PathVariable Long id, @Valid @RequestBody UnitRequestDTO updatedUnit) {
    UnitResponseDTO responseDTO = unitService.updateById(id, updatedUnit);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
    unitService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/all")
  public ResponseEntity<List<UnitResponseDTO>> getAllUnits() {
    List<UnitResponseDTO> responseDTOs = unitService.findAll();
    return ResponseEntity.ok(responseDTOs);
  }

}
