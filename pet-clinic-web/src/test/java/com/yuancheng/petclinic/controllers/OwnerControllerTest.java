package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
  void findOwners() throws Exception{
    mockMvc.perform(get("/owners/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("/owners/findOwners"))
            .andExpect(model().attributeExists("owner"));

    verifyNoInteractions(ownerService);
  }

  @Test
  void testFindFormReturnMany() throws Exception {
    when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build(), Owner.builder().id(2L).build()));

    mockMvc.perform(get("/owners"))
            .andExpect(status().isOk())
            .andExpect(view().name("/owners/ownersList"))
            .andExpect(model().attributeExists("selections"))
            .andExpect(model().attribute("selections", hasSize(2)));
  }

  @Test
  void testFindFormReturnOne() throws Exception {
    when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build()));

    mockMvc.perform(get("/owners"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"));
  }

  @Test
  void processFindFormEmptyReturnMany() throws Exception{
    when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build(), Owner.builder().id(2L).build()));

    mockMvc.perform(get("/owners")
                    .param("lastName", "")
                    .param("city", "city name")
                    .param("name", "pet name"))
            .andExpect(status().isOk())
            .andExpect(view().name("/owners/ownersList"))
            .andExpect(model().attribute("selections", hasSize(2)));
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

  @Test
  void initCreationForm() throws Exception {
    mockMvc.perform(get("/owners/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().attributeExists("owner"));

    verifyNoInteractions(ownerService);
  }

  @Test
  void processCreationForm() throws Exception{
    // given
    Owner owner = Owner.builder().id(1L).build();

    // when
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);

    // then
    mockMvc.perform(post("/owners/new"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("owner"));

    verify(ownerService).save(ArgumentMatchers.any());

  }

  @Test
  void initUpdateOwnerForm() throws Exception{
    // given
    Owner owner = Owner.builder().id(1L).build();

    // when
    when(ownerService.findById(anyLong())).thenReturn(owner);

    // then
    mockMvc.perform(get("/owners/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().attributeExists("owner"));

    verify(ownerService, times(1)).findById(anyLong());
  }

  @Test
  void processUpdateOwnerForm() throws Exception{
    // given
    Owner owner = Owner.builder().id(1L).build();

    // when
    when(ownerService.save(any())).thenReturn(owner);

    // then
    mockMvc.perform(post("/owners/1/edit"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("owner"));

    verify(ownerService, times(1)).save(ArgumentMatchers.any());
  }
}