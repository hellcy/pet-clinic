package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.models.Visit;
import com.yuancheng.petclinic.services.PetService;
import com.yuancheng.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VisitControllerTest {

  @Mock
  PetService petService;

  @Mock
  VisitService visitService;

  @InjectMocks
  VisitController visitController;

  MockMvc mockMvc;

  Pet pet;
  Visit visit;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

    pet = new Pet();
    pet.setId(1L);
    visit = new Visit();
    visit.setId(2L);
    pet.getVisits().add(visit);
    visit.setPet(pet);
  }

  @Test
  void initNewVisitForm() throws Exception {

    // when
    when(petService.findById(anyLong())).thenReturn(pet);

    // then
    mockMvc.perform(get("/owners/1/pets/1/visits/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("/pets/createOrUpdateVisitForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeExists("visit"));

    verify(petService, times(1)).findById(anyLong());
  }

  @Test
  void processNewVisitForm() throws Exception{
    // when
    when(petService.findById(anyLong())).thenReturn(pet);

    // then
    mockMvc.perform(post("/owners/1/pets/1/visits/new")
                    .param("date", "2018-11-11")
                    .param("description", "some description"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeExists("visit"));

    verify(visitService, times(1)).save(any(Visit.class));

  }
}