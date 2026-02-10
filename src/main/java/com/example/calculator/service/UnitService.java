package com.example.calculator.service;

import com.example.calculator.entity.Unit;
import com.example.calculator.repository.UnitRepository;
import com.example.calculator.transfer.request.UnitRequestDTO;
import com.example.calculator.transfer.response.UnitResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UnitService implements EntityServiceInterface<UnitRequestDTO, UnitResponseDTO, Long> {

  private final UnitRepository unitRepository;
  private final UnitConversionTypeService unitConversionTypeService;
  private final UnitCategoryService unitCategoryService;

  @Override
  public UnitResponseDTO create(UnitRequestDTO unitRequestDTO) {
    validateUnitRequestDTO(unitRequestDTO);
    Unit newUnit = mapFromDTOToEntity(unitRequestDTO);
    Unit savedUnit = unitRepository.save(unit);
    return convertFromEntityToDTO(savedUnit);
  }

  @Override
  public UnitResponseDTO findById(Long unitId) {
    Unit unit = getUnitByIdOrThrow(unitId);
    return mapFromEntityToDTO(unit);
  }

  @Override
  public UnitResponseDTO updateById(Long unitId, UnitRequestDTO dto) {
    Unit existingUnit = getUnitByIdOrThrow(unitId);
    validateUnitRequestDTO(dto);

    Unit updatedUnit = unitRepository.save(existingUnit);
    return mapFromEntityToDTO(updatedUnit);
  }

  @Override
  public boolean deleteById(Long aLong) {
    return false;
  }

  @Override
  public List<UnitResponseDTO> findAll() {
    return List.of();
  }

  private Unit getUnitByIdOrThrow(Long unitId) {
    return unitRepository.findById(unitId)
        .orElseThrow(() -> new IllegalArgumentException("Unit with id " + unitId + " not found"));
  }

  private void ensureNameIsUnique(String name) {
    if (unitRepository.findByName(name).isPresent()) {
      throw new IllegalArgumentException("Unit with name " + name + " already exists");
    }
  }

  private void ensureSymbolIsUnique(String symbol) {
    if (unitRepository.findByName(symbol).isPresent()) {
      throw new IllegalArgumentException("Unit with symbol " + symbol + " already exists");
    }
  }

  // Public for access by conversion service - is this reasonable?
  public boolean isBaseUnitAlreadyPresentInCategory(Long categoryId) {
    return unitRepository.existsByUnitCategoryIdAndIsBaseUnitTrue(categoryId);
  }

  private UnitResponseDTO mapFromEntityToDTO(Unit unit) {
    return new UnitResponseDTO(
        unit.getId(),
        unit.getName(),
        unit.getSymbol(),
        unit.getUnitCategory().getId(),
        unit.isBaseUnit(),
        unit.getUnitConversionType().getId(),
        unit.getConversionToBaseFactor(),
        unit.getConversionToBaseOffset(),
        unit.getNotes()
    );
  }

  private Unit mapFromDTOToEntity(UnitRequestDTO unitRequestDTO) {
    Unit unit = new Unit();
    unit.setName(unitRequestDTO.name());
    unit.setSymbol(unitRequestDTO.symbol());
    unit.setBaseUnit(unitRequestDTO.isBaseUnit());
    unit.setConversionToBaseFactor(unitRequestDTO.conversionToBaseFactor());
    unit.setConversionToBaseOffset(unitRequestDTO.conversionToBaseOffset());
    unit.setNotes(unitRequestDTO.notes());
    // set category and conversion type using the services to ensure they exist
    unit.setUnitCategory(unitCategoryService.findById(unitRequestDTO.categoryId()));
    unit.setUnitConversionType(unitConversionTypeService.getConversionTypeByIdOrThrow(unitRequestDTO.conversionTypeId()));
    return unit;
  }

  private void validateUnitRequestDTO(UnitRequestDTO unitRequestDTO) {
    // ensure conversion type and category exist,
    unitConversionTypeService.findById(unitRequestDTO.conversionTypeId());
    unitCategoryService.findById(unitRequestDTO.categoryId());
    // ensure the unit name is unique,
    ensureNameIsUnique(unitRequestDTO.name());
    // ensure the unit symbol is unique,
    ensureSymbolIsUnique(unitRequestDTO.symbol());
    // ensure if the unit is marked as base unit, there is no other base unit in the same category,
    if (unitRequestDTO.isBaseUnit() && isBaseUnitAlreadyPresentInCategory(unitRequestDTO.categoryId())) {
      throw new IllegalArgumentException("A base unit already exists in this category.");
    }
  }
}
