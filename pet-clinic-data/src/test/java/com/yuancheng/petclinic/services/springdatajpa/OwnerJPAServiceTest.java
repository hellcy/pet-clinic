package com.yuancheng.petclinic.services.springdatajpa;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.repositories.OwnerRepository;
import com.yuancheng.petclinic.repositories.PetRepository;
import com.yuancheng.petclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJPAServiceTest {

  @Mock
  OwnerRepository ownerRepository;

  @Mock
  PetRepository petRepository;

  @Mock
  PetTypeRepository petTypeRepository;

  @InjectMocks
  OwnerJPAService ownerJPAService;

  private final String lastName = "smith";

  Owner expectedOwner;

  @BeforeEach
  void setUp() {
    expectedOwner = Owner.builder().lastName(lastName).id(1L).build();

  }

  @Test
  void findAll() {
    Set<Owner> expectedOwners = new HashSet<>();
    expectedOwners.add(Owner.builder().id(1L).build());
    expectedOwners.add(Owner.builder().id(2L).build());

    when(ownerRepository.findAll()).thenReturn(expectedOwners);

    Set<Owner> returnedOwners = ownerJPAService.findAll();

    assertEquals(expectedOwners.size(), returnedOwners.size());
  }

  @Test
  void findById() {
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(expectedOwner));

    Owner returnedOwner = ownerJPAService.findById(1L);
    assertNotNull(returnedOwner);
  }

  @Test
  void findByIdNotFound() {
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

    Owner returnedOwner = ownerJPAService.findById(1L);
    assertNull(returnedOwner);
  }

  @Test
  void save() {
    when(ownerRepository.save(any())).thenReturn(expectedOwner);

    Owner returnedOwner = ownerJPAService.save(expectedOwner);

    // verify that the save() method is being called and result is not null
    verify(ownerRepository).save(any());
    assertNotNull(returnedOwner);
  }

  @Test
  void delete() {
    ownerJPAService.delete(expectedOwner);
    verify(ownerRepository, times(1)).delete(any());
  }

  @Test
  void deleteById() {
    ownerJPAService.deleteById(expectedOwner.getId());
    verify(ownerRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void findByLastName() {

    when(ownerRepository.findByLastName(any())).thenReturn(expectedOwner);

    Owner returnedOwner = ownerJPAService.findByLastName(lastName);

    assertEquals(expectedOwner.getLastName(), returnedOwner.getLastName());
    verify(ownerRepository, times(1)).findByLastName(any());
  }
}