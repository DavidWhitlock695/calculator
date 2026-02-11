package com.example.calculator.mapper;

import com.example.calculator.entity.UnitConversionType;
import com.example.calculator.transfer.request.UnitConversionTypeRequestDTO;
import com.example.calculator.transfer.response.UnitConversionTypeResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitConversionTypeMapper {

  public UnitConversionType toEntity(UnitConversionTypeRequestDTO dto){
    UnitConversionType unitConversionType = new UnitConversionType();
    updateEntityFromDTO(unitConversionType, dto);
    return unitConversionType;
  }

  public UnitConversionTypeResponseDTO toDTO(UnitConversionType entity){
    return new UnitConversionTypeResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
    );
  }

  public void updateEntityFromDTO(UnitConversionType existingEntity,
                                UnitConversionTypeRequestDTO dto){
    existingEntity.setName(dto.name());
    existingEntity.setDescription(dto.description());
  }

  public List<UnitConversionTypeResponseDTO> toDTOList(List<UnitConversionType> entities){
    return entities.stream()
            .map(this::toDTO)
            .toList();
  }
}
