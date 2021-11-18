package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.models.PetType;
import com.yuancheng.petclinic.services.OwnerService;
import com.yuancheng.petclinic.services.PetService;
import com.yuancheng.petclinic.services.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
  private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "/pets/createOrUpdatePetForm";
  private final PetService petService;
  private final PetTypeService petTypeService;
  private final OwnerService ownerService;

  public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
    this.petService = petService;
    this.petTypeService = petTypeService;
    this.ownerService = ownerService;
  }

  // common model attributes, can be used in other controller methods
  @ModelAttribute("types")
  public Set<PetType> populatePetTypes() {
    return petTypeService.findAll();
  }

  @ModelAttribute("owner")
  public Owner findOwner(@PathVariable Long ownerId) {
    return ownerService.findById(ownerId);
  }

  @InitBinder("owner")
  public void initOwnerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @GetMapping("/pets/new")
  public String initCreationForm(Owner owner, Model model) {
    Pet pet = new Pet();
    owner.getPets().add(pet);
    // view need to show Owner's firstName and lastName, we are not adding Owner attribute to the Model,
    // so we need to set Pet's Owner property in order to display Owner's property
    pet.setOwner(owner);
    model.addAttribute("pet", pet);
    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/pets/new")
  public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {
    if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
      result.rejectValue("name", "duplicate", "already exists");
    }

    owner.getPets().add(pet);
    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    } else {
      petService.save(pet);
      return "redirect:/owners/" + owner.getId();
    }
  }

  @GetMapping("/pets/{petId}/edit")
  public String initUpdateForm(@PathVariable Long petId,
                               Model model) {
    Pet pet = petService.findById(petId);
    model.addAttribute("pet", pet);
    return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/pets/{petId}/edit")
  public String processUpdateForm(@Valid Pet pet, BindingResult result,
                                  Owner owner, Model model) {
    if (result.hasErrors()) {
      pet.setOwner(owner);
      model.addAttribute("pet", pet);
      return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    } else {
      owner.getPets().add(pet);
      petService.save(pet);
      return "redirect:/owners/" + owner.getId();
    }
  }
}
