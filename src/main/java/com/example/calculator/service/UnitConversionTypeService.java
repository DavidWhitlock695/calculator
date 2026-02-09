package com.example.calculator.service;

import com.example.calculator.entity.UnitConversionType;
import com.example.calculator.repository.UnitConversionTypeRepository;
import com.example.calculator.transfer.request.UnitConversionTypeRequestDTO;
import com.example.calculator.transfer.response.UnitConversionTypeResponseDTO;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitConversionTypeService implements EntityServiceInterface<UnitConversionTypeRequestDTO, UnitConversionTypeResponseDTO, Long> {

  private final UnitConversionTypeRepository unitConversionTypeRepository;

  @Override
  public UnitConversionTypeResponseDTO create(UnitConversionTypeRequestDTO unitConversionTypeRequestDTO) {
    ensureNameIsUnique(unitConversionTypeRequestDTO.name());
    UnitConversionType unitConversionType = new UnitConversionType();
    // It's a simple object so mapping will occur in service layer directly.
    unitConversionType.setName(unitConversionTypeRequestDTO.name());
    unitConversionType.setDescription(unitConversionTypeRequestDTO.description());
    UnitConversionType savedConversionType = unitConversionTypeRepository.save(unitConversionType);
    return convertFromEntityToDTO(savedConversionType);
  }


  @Override
  public UnitConversionTypeResponseDTO findById(Long conversionTypeId) {
    UnitConversionType unitConversionType = getConversionTypeByIdOrThrow(conversionTypeId);
    return convertFromEntityToDTO(unitConversionType);
  }

  @Override
  public UnitConversionTypeResponseDTO updateById(Long conversionTypeId,
                                                  UnitConversionTypeRequestDTO unitConversionTypeRequestDTO) {
    UnitConversionType existingConversionType = getConversionTypeByIdOrThrow(conversionTypeId);
    // It's a simple object so mapping will occur in service layer directly.
    existingConversionType.setName(unitConversionTypeRequestDTO.name());
    existingConversionType.setDescription(unitConversionTypeRequestDTO.description());
    UnitConversionType updatedConversionType = unitConversionTypeRepository.save(existingConversionType);
    return convertFromEntityToDTO(updatedConversionType);
  }

  @Override
  public boolean deleteById(Long conversionTypeId) {
    // TODO: This should be a custom exception.
    if (unitConversionTypeRepository.existsById(conversionTypeId)){
      unitConversionTypeRepository.deleteById(conversionTypeId);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<UnitConversionTypeResponseDTO> findAll() {
    List<UnitConversionType> conversionTypes = unitConversionTypeRepository.findAll();
    return conversionTypes.stream()
        .map(this::convertFromEntityToDTO)
        .toList();
  }

  private UnitConversionType getConversionTypeByIdOrThrow(Long conversionTypeId) {
    return unitConversionTypeRepository.findById(conversionTypeId)
            .orElseThrow(() -> new IllegalArgumentException("ConversionType with id " + conversionTypeId + " does not exist."));
  }

  private void ensureNameIsUnique(String name) {
    if (unitConversionTypeRepository.existsByName(name)) {
      throw new EntityExistsException("ConversionType with name " + name + " already exists.");
    }
  }

  private UnitConversionTypeResponseDTO convertFromEntityToDTO(UnitConversionType unitConversionType) {
    return new UnitConversionTypeResponseDTO(
            unitConversionType.getId(),
            unitConversionType.getName(),
            unitConversionType.getDescription()
    );
  }
}
