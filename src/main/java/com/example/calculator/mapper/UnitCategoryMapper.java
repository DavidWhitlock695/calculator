package com.example.calculator.mapper;

import com.example.calculator.entity.UnitCategory;
import com.example.calculator.transfer.request.UnitCategoryRequestDTO;
import com.example.calculator.transfer.response.UnitCategoryResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitCategoryMapper {

  public UnitCategory toEntity(UnitCategoryRequestDTO dto) {
    UnitCategory unitCategory = new UnitCategory();
    updateEntityFromDTO(unitCategory, dto);
    return unitCategory;
  }

  public UnitCategoryResponseDTO toDTO(UnitCategory entity) {
    return new UnitCategoryResponseDTO(
            entity.getId(),
            entity.getName()
    );
  }

  public void updateEntityFromDTO(UnitCategory existingEntity, UnitCategoryRequestDTO dto) {
    existingEntity.setName(dto.name());
  }

  public List<UnitCategoryResponseDTO> toDTOList(List<UnitCategory> entities) {
    return entities.stream()
            .map(this::toDTO)
            .toList();
  }
}
