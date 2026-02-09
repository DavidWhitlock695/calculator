package com.example.calculator.repository;

import com.example.calculator.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
  Optional<Unit> findByName(String name);

  boolean existsByUnitCategoryIdAndIsBaseUnitTrue(Long categoryId);
}
