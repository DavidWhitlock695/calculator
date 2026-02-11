package com.example.calculator.mapper;

/*
  * This class is intended to be used for mapping between Unit entity and its corresponding DTOs.
  * While for simple mapping the work could be done in the service layer, dedicated mappers are
  * easier to maintain long term, support more complex relationships (e.g. unit with nested
  * category and conversion type) and can be tested in isolation.
 */

import com.example.calculator.entity.Unit;
import com.example.calculator.entity.UnitCategory;
import com.example.calculator.entity.UnitConversionType;
import com.example.calculator.repository.UnitCategoryRepository;
import com.example.calculator.repository.UnitConversionTypeRepository;
import com.example.calculator.transfer.request.UnitRequestDTO;
import com.example.calculator.transfer.response.UnitResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

//Needs to be a component to be injected into the service layer.
@Component
@RequiredArgsConstructor
public class UnitMapper {

  private final UnitCategoryRepository unitCategoryRepository;
  private final UnitConversionTypeRepository unitConversionTypeRepository;
  
  public Unit toEntity(UnitRequestDTO dto) {
    Unit newEntity = new Unit();
    updateEntityFromDTO(newEntity, dto);
    return newEntity;
  }

  public void updateEntityFromDTO(Unit existingEntity, UnitRequestDTO dto) {
    // For simplicity, we will update all fields. In a real application, we might want to support partial updates.
    existingEntity.setName(dto.name());
    existingEntity.setSymbol(dto.symbol());
    existingEntity.setBaseUnit(dto.isBaseUnit());
    existingEntity.setConversionToBaseFactor(dto.conversionToBaseFactor());
    existingEntity.setConversionToBaseOffset(dto.conversionToBaseOffset());
    existingEntity.setNotes(dto.notes());

    // Don't need to check nulls because it's taken care of by the @Valid and @NotNull annotations
    UnitCategory category = unitCategoryRepository.findById(dto.categoryId())
            .orElseThrow(() -> new EntityNotFoundException(
                    "Unit category with ID " + dto.categoryId() + " not found"));
    existingEntity.setUnitCategory(category);

    UnitConversionType conversionType = unitConversionTypeRepository.findById(dto.conversionTypeId())
            .orElseThrow(() -> new EntityNotFoundException(
                    "Unit conversion type with ID " + dto.conversionTypeId() + " not found"));
    existingEntity.setUnitConversionType(conversionType);
  }

  public UnitResponseDTO toDTO(Unit entity) {
    return new UnitResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getSymbol(),
            entity.getUnitCategory().getId(),
            entity.isBaseUnit(),
            entity.getUnitConversionType().getId(),
            entity.getConversionToBaseFactor(),
            entity.getConversionToBaseOffset(),
            entity.getNotes()
    );
  }

  public List<UnitResponseDTO> toDTOList(List<Unit> entities) {
    return entities.stream()
            .map(this::toDTO)
            .toList();
  }
}
