package com.example.calculator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// For now we'll only support affine conversions (scaling and offset).
// Later we could try supporting logarithmic conversions (e.g., decibels) hence this separate entity.

@Entity
@Table(name = "conversion_types")
@Getter
@Setter
public class ConversionType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
  //I want to enforce a description as these are concepts that users should understand
  @Column(nullable = false)
  private String description;
}
