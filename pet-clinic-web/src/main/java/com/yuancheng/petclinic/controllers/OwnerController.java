package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.Person;
import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

  private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  // defensive coding, forbid users to set id property
  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @RequestMapping("/find")
  public String findOwner(Model model) {
    model.addAttribute("owner", Owner.builder().build());
    return "/owners/findOwners";
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable Long ownerId) {
    ModelAndView mav = new ModelAndView("/owners/ownerDetails");
    mav.addObject(ownerService.findById(ownerId));
    return mav;
  }

  @GetMapping
  public String processFindForm(Owner owner, Person person, Pet pet, Model model) {
    // allow parameterless GET request for /owners to return all records
    if (owner.getLastName() == null) {
      owner.setLastName(""); // empty String signifies the broadest possible search
    }

    // find owner by last name
    List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
    if (results.isEmpty()) {
      // no owners found
      //bindingResult.rejectValue("lastName", "notFound", "not found");
      return "owners/findOwners";
    } else if (results.size() == 1) {
      // one owner found
      owner = results.iterator().next();
      return "redirect:/owners/" + owner.getId();
    } else {
      // multiple owners found
      model.addAttribute("selections", results);
      return "/owners/ownersList";
    }
  }

  @GetMapping("/new")
  public String initCreationForm(Model model) {
    model.addAttribute("owner", Owner.builder().build());
    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/new")
  public String processCreationForm(@Valid Owner owner, BindingResult result) {
    // return to view if form has error
    if (result.hasErrors()) {
      return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    } else {
      Owner savedOwner = ownerService.save(owner);
      return "redirect:/owners/" + savedOwner.getId();
    }
  }

  @GetMapping("/{ownerId}/edit")
  public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
    model.addAttribute(ownerService.findById(ownerId));
    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
  }

  @PostMapping("/{ownerId}/edit")
  public String processUpdateOwnerForm(@PathVariable Long ownerId,
                                       @Valid Owner owner, BindingResult result) {
    if (result.hasErrors()) {
      return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    } else {
      owner.setId(ownerId);
      Owner savedOwner = ownerService.save(owner);
      return "redirect:/owners/" + savedOwner.getId();
    }
  }
}
