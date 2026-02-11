package com.example.calculator.service;

import com.example.calculator.entity.UnitCategory;
import com.example.calculator.mapper.UnitCategoryMapper;
import com.example.calculator.repository.UnitCategoryRepository;
import com.example.calculator.transfer.request.UnitCategoryRequestDTO;
import com.example.calculator.transfer.response.UnitCategoryResponseDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitCategoryService implements EntityServiceInterface<UnitCategoryRequestDTO,
        UnitCategoryResponseDTO, Long> {

  private final UnitCategoryMapper unitCategoryMapper;
  private final UnitCategoryRepository unitCategoryRepository;

  @Override
  public UnitCategoryResponseDTO create(UnitCategoryRequestDTO unitCategoryRequestDTO) {
    ensureNameIsUnique(unitCategoryRequestDTO.name());
    UnitCategory newUnit = unitCategoryMapper.toEntity(unitCategoryRequestDTO);
    UnitCategory savedCategory = unitCategoryRepository.save(newUnit);
    return unitCategoryMapper.toDTO(savedCategory);
  }

  @Override
  public UnitCategoryResponseDTO findById(Long unitCategoryId) {
    UnitCategory unitCategory = getCategoryByIdOrThrow(unitCategoryId);
    return unitCategoryMapper.toDTO(unitCategory);
  }

  @Override
  public UnitCategoryResponseDTO updateById(Long unitCategoryId, UnitCategoryRequestDTO entity) {
    UnitCategory existingCategory = getCategoryByIdOrThrow(unitCategoryId);
    if (!existingCategory.getName().equals(entity.name())) {
      ensureNameIsUnique(entity.name());
    }
    unitCategoryMapper.updateEntityFromDTO(existingCategory, entity);
    UnitCategory updatedCategory = unitCategoryRepository.save(existingCategory);
    return unitCategoryMapper.toDTO(updatedCategory);
  }

  @Override
  public boolean deleteById(Long unitCategoryId) {
    getCategoryByIdOrThrow(unitCategoryId);
    unitCategoryRepository.deleteById(unitCategoryId);
    return true;
  }

  @Override
  public List<UnitCategoryResponseDTO> findAll() {
    List<UnitCategory> categories = unitCategoryRepository.findAll();
    return unitCategoryMapper.toDTOList(categories);
  }

  // In some cases, the caller will not use the returned object
  // This is still better than being void, because we don't then have to call the repository twice in the delete method
  private UnitCategory getCategoryByIdOrThrow(Long unitCategoryId) {
    return unitCategoryRepository.findById(unitCategoryId)
        .orElseThrow(() -> new EntityNotFoundException("Unit category with ID " + unitCategoryId + " not found."));
  }

  private void ensureNameIsUnique(String name) {
    if (unitCategoryRepository.existsByName(name)) {
      throw new EntityExistsException("Unit category with name '" + name + "' already exists.");
    }
  }
}
