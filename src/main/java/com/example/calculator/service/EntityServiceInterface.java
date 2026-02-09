package com.example.calculator.service;

import java.util.List;

/**
 * All entities must have the basic CRUD operations in their service layer.
 * This interface enforces that contract using generics.
 * Note that we don't need this in the repository layer because Spring Data JPA already provides basic CRUD operations through JpaRepository.
 */

public interface EntityServiceInterface<Req, Res, ID> {
  Res create(Req dto);
  Res findById(ID id);
  Res updateById(ID id, Req dto);
  // You could return void, but returning a boolean allows the service to indicate whether the deletion was successful or not
  boolean deleteById(ID id);
  List<Res> findAll();
}
