package com.yuancheng.petclinic.formatters;

import com.yuancheng.petclinic.models.PetType;
import com.yuancheng.petclinic.services.PetService;
import com.yuancheng.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

/**
 * This formatter will be used by Spring MVC to parse String to PetType object
 */
@Component
public class PetTypeFormatter implements Formatter<PetType> {

  private final PetTypeService petTypeService;

  public PetTypeFormatter(PetTypeService petTypeService) {
    this.petTypeService = petTypeService;
  }

  @Override
  public String print(PetType petType, Locale locale) {
    return petType.getName();
  }

  @Override
  public PetType parse(String text, Locale locale) throws ParseException {
    Set<PetType> petTypes = petTypeService.findAll();
    for (PetType petType : petTypes) {
      if (petType.getName().equals(text)) {
        return petType;
      }
    }
    throw new ParseException("Type not found: " + text, 0);
  }
}
