package com.yuancheng.petclinic.services.map;

import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {

  private PetService petService;

  private final Long petId = 1L;

  @BeforeEach
  void setUp() {
    petService = new PetMapService();

    petService.save(Pet.builder().id(petId).build());
  }

  @Test
  void findAll() {
    Set<Pet> pets = petService.findAll();

    assertEquals(1, pets.size());
  }

  @Test
  void deleteById() {
    petService.deleteById(petId);

    assertEquals(0, petService.findAll().size());
  }

  @Test
  void delete() {
    petService.delete(petService.findById(petId));

    assertEquals(0, petService.findAll().size());
  }

  @Test
  void saveExistingId() {
    Long id = 2L;
    Pet pet = Pet.builder().id(id).build();
    Pet savedPet = petService.save(pet);

    assertEquals(id, savedPet.getId());
  }

  @Test
  void saveDuplicateId() {
    Long id = 1L;
    Pet pet = Pet.builder().id(id).build();

    Pet savedPet = petService.save(pet);

    assertEquals(id, savedPet.getId());
    assertEquals(1, petService.findAll().size());
  }

  @Test
  void saveNoId() {
    Pet savedPet = petService.save(Pet.builder().build());

    assertNotNull(savedPet);
    assertNotNull(savedPet.getId());
    assertEquals(2, petService.findAll().size());
  }

  @Test
  void findByIdExistingId() {
    Pet pet = petService.findById(petId);
    assertEquals(petId, pet.getId());
  }

  @Test
  void findByIdNotExistingId() {
    Pet pet = petService.findById(5L);
    assertNull(pet);
  }


  @Test
  void findByIdNullId() {
    Pet pet = petService.findById(null);

    assertNull(pet);
  }


}