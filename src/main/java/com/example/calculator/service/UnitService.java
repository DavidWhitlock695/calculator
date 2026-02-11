package com.example.calculator.service;

import com.example.calculator.entity.Unit;
import com.example.calculator.mapper.UnitMapper;
import com.example.calculator.repository.UnitRepository;
import com.example.calculator.transfer.request.UnitRequestDTO;
import com.example.calculator.transfer.response.UnitResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// Note: service layer handles business logic but validation of relationships is done in mappers

@Service
@RequiredArgsConstructor
public class UnitService implements EntityServiceInterface<UnitRequestDTO, UnitResponseDTO, Long> {

  private final UnitRepository unitRepository;
  private final UnitMapper unitMapper;

  @Override
  public UnitResponseDTO create(UnitRequestDTO unitRequestDTO) {
    validateNewUnitRequestDTO(unitRequestDTO);
    Unit newUnit = unitMapper.toEntity(unitRequestDTO);
    Unit savedUnit = unitRepository.save(newUnit);
    return unitMapper.toDTO(savedUnit);
  }

  @Override
  public UnitResponseDTO findById(Long unitId) {
    Unit unit = getUnitByIdOrThrow(unitId);
    return unitMapper.toDTO(unit);
  }

  @Override
  public UnitResponseDTO updateById(Long unitId, UnitRequestDTO dto) {
    Unit existingUnit = getUnitByIdOrThrow(unitId);
    validateUpdateUnitRequestDTO(unitId, dto);
    unitMapper.updateEntityFromDTO(existingUnit, dto);
    Unit updatedUnit = unitRepository.save(existingUnit);
    return unitMapper.toDTO(updatedUnit);
  }

  @Override
  public boolean deleteById(Long unitId) {
    if (unitRepository.existsById(unitId)) {
      unitRepository.deleteById(unitId);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<UnitResponseDTO> findAll() {
    List<Unit> units = unitRepository.findAll();
    return unitMapper.toDTOList(units);
  }

  private Unit getUnitByIdOrThrow(Long unitId) {
    return unitRepository.findById(unitId)
        .orElseThrow(() -> new EntityNotFoundException("Unit with id " + unitId + " not found"));
  }

  private void ensureNameIsUnique(String name) {
    if (unitRepository.findByName(name).isPresent()) {
      throw new IllegalArgumentException("Unit with name " + name + " already exists");
    }
  }

  private void ensureSymbolIsUnique(String symbol) {
    if (unitRepository.findBySymbol(symbol).isPresent()) {
      throw new IllegalArgumentException("Unit with symbol " + symbol + " already exists");
    }
  }

  // Public for access by conversion service - is this reasonable?
  public boolean isBaseUnitAlreadyPresentInCategory(Long categoryId) {
    return unitRepository.existsByUnitCategoryIdAndIsBaseUnitTrue(categoryId);
  }

  private void validateNewUnitRequestDTO(UnitRequestDTO unitRequestDTO) {
    // ensure the unit name is unique,
    ensureNameIsUnique(unitRequestDTO.name());
    // ensure the unit symbol is unique,
    ensureSymbolIsUnique(unitRequestDTO.symbol());
    // ensure if the unit is marked as base unit, there is no other base unit in the same category,
    if (unitRequestDTO.isBaseUnit() && isBaseUnitAlreadyPresentInCategory(unitRequestDTO.categoryId())) {
      throw new IllegalArgumentException("A base unit already exists in this category.");
    }
    //If creating a new base unit, factor and offset must be 1 and 0 respectively, otherwise throw an exception.
    if (unitRequestDTO.isBaseUnit()) {
      validateBaseUnitHasCorrectConversion(unitRequestDTO);
    }
  }
  private void validateBaseUnitHasCorrectConversion(UnitRequestDTO dto) {
    if (dto.conversionToBaseFactor().compareTo(BigDecimal.ONE) != 0){
      throw new IllegalArgumentException("Base unit must have conversion factor of 1.");
    }
    if (dto.conversionToBaseOffset().compareTo(BigDecimal.ZERO) != 0) {
      throw new IllegalArgumentException("Base unit must have conversion offset of 0.");
    }
  }

  private void validateUpdateUnitRequestDTO(Long unitId, UnitRequestDTO dto) {
    Unit existingUnit = getUnitByIdOrThrow(unitId);
    // If the name is being updated, ensure the new name is unique
    if (!existingUnit.getName().equals(dto.name())) {
      ensureNameIsUnique(dto.name());
    }
    // If the symbol is being updated, ensure the new symbol is unique
    if (!existingUnit.getSymbol().equals(dto.symbol())) {
      ensureSymbolIsUnique(dto.symbol());
    }
    // If the base unit status is being updated to true, ensure there is no other base unit in the same category
    if (!existingUnit.isBaseUnit() && dto.isBaseUnit() && isBaseUnitAlreadyPresentInCategory(dto.categoryId())) {
      throw new IllegalArgumentException("A base unit already exists in this category.");
    }
    // If the base unit remains true, but the category is being changed, ensure there is no other base unit in the new category
    if (existingUnit.isBaseUnit() && dto.isBaseUnit()
            && !existingUnit.getUnitCategory().getId().equals(dto.categoryId())
            && isBaseUnitAlreadyPresentInCategory(dto.categoryId())) {
      throw new IllegalArgumentException("A base unit already exists in the new category.");
    }
    //If creating a new base unit, factor and offset must be 1 and 0 respectively, otherwise throw an exception.
    if (dto.isBaseUnit()) {
      validateBaseUnitHasCorrectConversion(dto);
    }
    // Note: we don't check unit no longer being a base. This is handled during conversion time.
  }
}
