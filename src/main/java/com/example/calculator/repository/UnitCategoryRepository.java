package com.example.calculator.repository;

import com.example.calculator.entity.UnitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitCategoryRepository extends JpaRepository<UnitCategory, Long> {
  boolean existsByName(String name);
}
