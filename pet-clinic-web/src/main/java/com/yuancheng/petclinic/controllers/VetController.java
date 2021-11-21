package com.yuancheng.petclinic.controllers;

import com.yuancheng.petclinic.models.Vet;
import com.yuancheng.petclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {

  private final VetService vetService;

  public VetController(VetService vetService) {
    this.vetService = vetService;
  }

  @RequestMapping({"/vets/index", "/vets/index.html", "/vets", "/vets.html"})
  public String listVets(Model model) {
    model.addAttribute("vets", vetService.findAll());

    return "vets/index";
  }

  // @ResponseBody indicates that the return value should be bound to the web response body
  @GetMapping({"/api/vets"})
  public @ResponseBody Set<Vet> getVetsJson() {
    return vetService.findAll();
  }
}
