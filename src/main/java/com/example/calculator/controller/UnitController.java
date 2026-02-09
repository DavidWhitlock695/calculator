package com.example.calculator.controller;

import com.example.calculator.entity.Unit;
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

  @GetMapping("/create")
  public Unit createUnit(@RequestBody Unit newUnit){
    // Validate
    // Pass to service layer
    return newUnit; // Placeholder response
  }
}
