package com.example.calculator.service;

import com.example.calculator.entity.UnitConversionType;
import com.example.calculator.mapper.UnitConversionTypeMapper;
import com.example.calculator.repository.UnitConversionTypeRepository;
import com.example.calculator.transfer.request.UnitConversionTypeRequestDTO;
import com.example.calculator.transfer.response.UnitConversionTypeResponseDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitConversionTypeService implements EntityServiceInterface<UnitConversionTypeRequestDTO, UnitConversionTypeResponseDTO, Long> {

  private final UnitConversionTypeRepository unitConversionTypeRepository;
  private final UnitConversionTypeMapper unitConversionTypeMapper;

  @Override
  public UnitConversionTypeResponseDTO create(UnitConversionTypeRequestDTO unitConversionTypeRequestDTO) {
    ensureNameIsUnique(unitConversionTypeRequestDTO.name());
    UnitConversionType newConversionType = unitConversionTypeMapper.toEntity(unitConversionTypeRequestDTO);
    UnitConversionType savedConversionType = unitConversionTypeRepository.save(newConversionType);
    return unitConversionTypeMapper.toDTO(savedConversionType);
  }


  @Override
  public UnitConversionTypeResponseDTO findById(Long conversionTypeId) {
    UnitConversionType unitConversionType = getConversionTypeByIdOrThrow(conversionTypeId);
    return unitConversionTypeMapper.toDTO(unitConversionType);
  }

  @Override
  public UnitConversionTypeResponseDTO updateById(Long conversionTypeId,
                                                  UnitConversionTypeRequestDTO unitConversionTypeRequestDTO) {
    UnitConversionType existingConversionType = getConversionTypeByIdOrThrow(conversionTypeId);
    if (!existingConversionType.getName().equals(unitConversionTypeRequestDTO.name())) {
      ensureNameIsUnique(unitConversionTypeRequestDTO.name());
    }
    // Mapper logic never changes the ID for safe updating of the entity.
    unitConversionTypeMapper.updateEntityFromDTO(existingConversionType, unitConversionTypeRequestDTO);
    UnitConversionType updatedConversionType = unitConversionTypeRepository.save(existingConversionType);
    return unitConversionTypeMapper.toDTO(updatedConversionType);
  }

  @Override
  public boolean deleteById(Long conversionTypeId) {
    getConversionTypeByIdOrThrow(conversionTypeId);
    unitConversionTypeRepository.deleteById(conversionTypeId);
    return true;
  }

  @Override
  public List<UnitConversionTypeResponseDTO> findAll() {
    List<UnitConversionType> conversionTypes = unitConversionTypeRepository.findAll();
    return unitConversionTypeMapper.toDTOList(conversionTypes);
  }

  private UnitConversionType getConversionTypeByIdOrThrow(Long conversionTypeId) {
    return unitConversionTypeRepository.findById(conversionTypeId)
            .orElseThrow(() -> new EntityNotFoundException("ConversionType with id " + conversionTypeId + " " +
                    "does not exist."));
  }

  private void ensureNameIsUnique(String name) {
    if (unitConversionTypeRepository.existsByName(name)) {
      throw new EntityExistsException("ConversionType with name " + name + " already exists.");
    }
  }
}
