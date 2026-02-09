package com.example.calculator.service;

import com.example.calculator.entity.UnitCategory;
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

  private final UnitCategoryRepository unitCategoryRepository;

  @Override
  public UnitCategoryResponseDTO create(UnitCategoryRequestDTO unitCategoryRequestDTO) {
    ensureNameIsUnique(unitCategoryRequestDTO.name());
    UnitCategory unitCategory = new UnitCategory();
    unitCategory.setName(unitCategoryRequestDTO.name());
    UnitCategory savedCategory = unitCategoryRepository.save(unitCategory);
    return convertFromEntityToDTO(savedCategory);
  }

  @Override
  public UnitCategoryResponseDTO findById(Long unitCategoryId) {
    UnitCategory unitCategory = getCategoryByIdOrThrow(unitCategoryId);
    return convertFromEntityToDTO(unitCategory);
  }

  @Override
  public UnitCategoryResponseDTO updateById(Long unitCategoryId, UnitCategoryRequestDTO entity) {
    UnitCategory existingCategory = getCategoryByIdOrThrow(unitCategoryId);
    // It's a simple object so mapping will occur in service layer directly.
    existingCategory.setName(entity.name());
    UnitCategory updatedCategory = unitCategoryRepository.save(existingCategory);
    return convertFromEntityToDTO(updatedCategory);
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
    return categories.stream()
        .map(this::convertFromEntityToDTO)
        .toList();
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

  private UnitCategoryResponseDTO convertFromEntityToDTO(UnitCategory unitCategory) {
    return new UnitCategoryResponseDTO(
        unitCategory.getId(),
        unitCategory.getName()
    );
  }
}
