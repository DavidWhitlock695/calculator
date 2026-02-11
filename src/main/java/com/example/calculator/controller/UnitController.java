package com.example.calculator.controller;

import com.example.calculator.entity.Unit;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {
  // Crud operations for units

  // Notice the use of the Valid annotation.
  // This ensures that the incoming request body is validated against the constraints defined in the Unit entity (e.g., @NotNull, @Size, etc.). If the validation fails,
  // Spring will automatically return a 400 Bad Request response with details about the validation errors.
  // It includes nested validation for UnitCategory and UnitConversionType.
  // Annotations like NotNull are only active when the Valid annotation is present on the method parameter.

  @GetMapping("/create")
  public Unit createUnit(@Valid @RequestBody Unit newUnit){
    // Validate
    // Pass to service layer
    return newUnit; // Placeholder response
  }
}
