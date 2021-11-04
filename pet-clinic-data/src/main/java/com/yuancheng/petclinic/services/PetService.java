package com.yuancheng.petclinic.services;

import com.yuancheng.petclinic.models.Pet;

import java.util.Set;

public interface PetService {

  Pet findById(Long id);

  Pet save(Pet pet);

  Set<Pet> findAll();
}
