package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.models.Visit;
import com.yuancheng.petclinic.services.PetService;
import com.yuancheng.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

import static java.awt.SystemColor.text;

@Slf4j
@Controller
public class VisitController {

  private static final String VIEWS_CREATE_OR_UPDATE_VISIT_FORM = "/pets/createOrUpdateVisitForm";
  private final PetService petService;
  private final VisitService visitService;

  public VisitController(PetService petService, VisitService visitService) {
    this.petService = petService;
    this.visitService = visitService;
  }

  /**
   * This method will be called before every @RequestMapping annotated method
   * Prepare Pet and Visit Model for the view
   * @param petId
   * @param model
   * @return
   */
  @ModelAttribute("visit")
  public Visit loadPetWithVisit(@PathVariable Long petId,
                                Model model) {
    Pet pet = petService.findById(petId);
    Visit visit = new Visit();
    pet.getVisits().add(visit);
    visit.setPet(pet);
    model.addAttribute("pet", pet);
    return visit;
  }

  // Spring MVC calls method loadPetWithVisit() before initNewVisitForm() is called
  @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
  public String initNewVisitForm(@PathVariable Long petId,
                                 Model model) {
    return VIEWS_CREATE_OR_UPDATE_VISIT_FORM;
  }

  // Spring MVC calls method loadPetWithVisit() before processNewVisitForm() is called
  @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
  public String processNewVisitForm(@PathVariable Long ownerId,
                                    @Valid Visit visit, BindingResult result) {
    if (result.hasErrors()) {
      return VIEWS_CREATE_OR_UPDATE_VISIT_FORM;
    } else {
      visitService.save(visit);
      return "redirect:/owners/" + ownerId;
    }
  }

  @InitBinder
  public void dataBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");

    dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text));
      }
    });
  }
}
