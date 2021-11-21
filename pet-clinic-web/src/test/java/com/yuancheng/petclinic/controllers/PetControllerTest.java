package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.models.PetType;
import com.yuancheng.petclinic.services.OwnerService;
import com.yuancheng.petclinic.services.PetService;
import com.yuancheng.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

  @Mock
  OwnerService ownerService;

  @Mock
  PetTypeService petTypeService;

  @Mock
  PetService petService;

  PetController petController;

  MockMvc mockMvc;

  Owner owner;

  Set<PetType> petTypes;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    petController = new PetController(petService, petTypeService, ownerService);

    mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

    owner = Owner.builder().id(1L).build();

     petTypes = new HashSet<>();
     petTypes.add(PetType.builder().id(1L).name("Dog").build());
     petTypes.add(PetType.builder().id(2L).name("Cat").build());
  }

  @Test
  void initCreationForm() throws Exception{
    // when
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    // then
    mockMvc.perform(get("/owners/1/pets/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("/pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeExists("types"));
  }

  @Test
  void processCreationFormHappyPath() throws Exception{
    // when
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    // then
    mockMvc.perform(post("/owners/1/pets/new")
                    .param("name", "pet name")
                    .param("birthDate", "2020-12-31"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("types"))
            .andExpect(model().attributeExists("pet"));

    verify(petService, times(1)).save(any());
  }

  @Test
  void processCreationFormUnhappyPath() throws Exception{
    // when
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    // then
    mockMvc.perform(post("/owners/1/pets/new")
                    .param("name", "pet name")
                    .param("birthDate", "2020-20-05"))
            .andExpect(status().isOk())
            .andExpect(view().name("/pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("types"))
            .andExpect(model().attributeExists("pet"));
    verifyNoInteractions(petService);
  }

  @Test
  void initUpdateForm() throws Exception{
    // when
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);
    when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

    // then
    mockMvc.perform(get("/owners/1/pets/2/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("/pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("types"))
            .andExpect(model().attributeExists("pet"));
  }

  @Test
  void processUpdateForm() throws Exception{
    // when
    when(ownerService.findById(anyLong())).thenReturn(owner);
    when(petTypeService.findAll()).thenReturn(petTypes);

    // then
    mockMvc.perform(post("/owners/1/pets/2/edit"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("types"))
            .andExpect(model().attributeExists("pet"));

    verify(petService, times(1)).save(any());
  }
}