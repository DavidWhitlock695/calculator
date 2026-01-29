package com.example.calculator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
  Represents a measurement unit with conversion details to other units within the same category.
  Only one unit per category should be marked as the base unit.
  All conversions will proceed via the base unit of the category.
  For now only affine conversions are supported (scaling and offset).
  Later we could try supporting logarithmic conversions (e.g., decibels).

  Note that imports do not have to be declared if they are in the same package.

  Notice that we enforce both optional=false and nullable=false on the ManyToOne relationships
  to ensure that these associations are always present. One enforces this at the JPA level, the other at the database schema level.
 **/

@Entity
@Table(name = "units")
@Getter
@Setter
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
  @Column(unique = true, nullable = false)
  private String symbol;
  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private UnitCategory unitCategory;
  @Column(nullable = false)
  private boolean isBaseUnit;
  @ManyToOne(optional = false)
  @JoinColumn(name = "conversion_type_id", nullable = false)
  private ConversionType conversionType;
  @Column(nullable = false)
  private BigDecimal conversionToBaseFactor;
  @Column(nullable = false)
  private BigDecimal conversionToBaseOffset;
  private String notes;
}
