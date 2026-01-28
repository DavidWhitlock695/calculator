package com.example.calculator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Represents a category of measurement units (e.g., Length, Weight, Temperature) but also
// complex units like pressure or density. Conversions should only occur between units of the same category.

@Entity
@Table(name = "unit_categories")
@Getter
@Setter
public class UnitCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
}
