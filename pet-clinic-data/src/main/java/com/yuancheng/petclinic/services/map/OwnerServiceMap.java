package com.yuancheng.petclinic.services.map;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.services.OwnerService;
import com.yuancheng.petclinic.services.PetService;
import com.yuancheng.petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {
  private final PetService petService;
  private final PetTypeService petTypeService;

  public OwnerServiceMap(PetService petService, PetTypeService petTypeService) {
    this.petService = petService;
    this.petTypeService = petTypeService;
  }

  @Override
  public Set<Owner> findAll() {
    return super.findAll();
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Owner object) {
    super.delete(object);
  }

  @Override
  public Owner save(Owner object) {
    if (object != null) {
      if (object.getPets() != null) {
        object.getPets().forEach(pet -> {
          if (pet.getPetType() != null) {
            if (pet.getPetType().getId() == null) {
              petTypeService.save(pet.getPetType());
            }
          } else throw new RuntimeException("Pet type is required!");

          if (pet.getId() == null) {
            petService.save(pet);
          }
        });
      }

      return super.save(object);
    } else {
      return null;
    }
  }

  @Override
  public Owner findById(Long id) {
    return super.findById(id);
  }

  @Override
  public Owner findByLastName(String lastName) {
    return null;
  }
}
