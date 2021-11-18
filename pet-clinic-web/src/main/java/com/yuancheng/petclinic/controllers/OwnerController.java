package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

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
  public ModelAndView showOwner(@PathVariable String ownerId) {
    ModelAndView mav = new ModelAndView("/owners/ownerDetails");
    mav.addObject(ownerService.findById(Long.valueOf(ownerId)));
    return mav;
  }

  @GetMapping
  public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {
    // allow parameterless GET request for /owners to return all records
    if (owner.getLastName() == null) {
      owner.setLastName(""); // empty String signifies broadest possible search
    }

    // find owner by last name
    List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
    if (results.isEmpty()) {
      // no owners found
      bindingResult.rejectValue("lastName", "notFound", "not found");
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
}
