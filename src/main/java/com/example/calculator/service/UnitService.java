package com.example.calculator.service;

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
    // ensure conversion type and category exist,
    // ensure the unit name is unique,
    //ensure the unit symbol is unique,
    // ensure if the unit is marked as base unit, there is no other base unit in the same category,
    // note we won't check there's at least one base unit - this is a separate concern
  }

  @Override
  public UnitResponseDTO findById(Long aLong) {
    return null;
  }

  @Override
  public UnitResponseDTO updateById(Long aLong, UnitRequestDTO dto) {
    return null;
  }

  @Override
  public boolean deleteById(Long aLong) {
    return false;
  }

  @Override
  public List<UnitResponseDTO> findAll() {
    return List.of();
  }

  public boolean isBaseUnitAlreadyPresentInCategory(Long categoryId) {
    return unitRepository.existsByUnitCategoryIdAndIsBaseUnitTrue(categoryId);
  }
}
