package com.example.calculator.repository;

import com.example.calculator.entity.ConversionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring Boot already provides basic CRUD operations through Jpa Repository
// We've just added the search by name (which is an exact match) so that we can look up without
// an ID

@Repository
public interface ConversionTypeRepository extends JpaRepository<ConversionType, Long> {
  Optional<ConversionType> findByName(String name);
}
