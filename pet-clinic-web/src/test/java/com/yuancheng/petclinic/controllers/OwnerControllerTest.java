package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

  @Mock
  OwnerService ownerService;

  @InjectMocks
  OwnerController ownerController;

  @Mock
  Model model;

  Set<Owner> expectedOwners;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    expectedOwners = new HashSet<>();
    expectedOwners.add(Owner.builder().id(1L).build());
    expectedOwners.add(Owner.builder().id(2L).build());

    // for testing http request responses
    mockMvc = MockMvcBuilders
            .standaloneSetup(ownerController)
            .build();
  }

  @Test
  void listOwners() throws Exception{
    when(ownerService.findAll()).thenReturn(expectedOwners);

    mockMvc.perform(get("/owners"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/index"))
            .andExpect(model().attribute("owners", hasSize(2)));
  }

  @Test
  void findOwner() throws Exception{
    mockMvc.perform(get("/owners/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));

    verifyNoInteractions(ownerService);
  }

  @Test
  void listOwnersTest() {
    String expectedPage = "owners/index";
    when(ownerService.findAll()).thenReturn(expectedOwners);

    ArgumentCaptor<Set<Owner>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

    String returnedPage = ownerController.listOwners(model);

    assertEquals(expectedPage, returnedPage);
    verify(model, times(1)).addAttribute(eq("owners"), argumentCaptor.capture());
    verify(ownerService, times(1)).findAll();

    Set<Owner> capturedOwners = argumentCaptor.getValue();
    assertEquals(expectedOwners.size(), capturedOwners.size());
  }

  @Test
  void displayOwner() throws Exception {
    // when
    when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(3L).build());

    // then
    mockMvc.perform(get("/owners/3"))
            .andExpect(status().isOk())
            .andExpect(view().name("/owners/ownerDetails"))
            .andExpect(model().attributeExists("owner"))
            .andExpect(model().attribute("owner", hasProperty("id", is(3L))));
  }
}