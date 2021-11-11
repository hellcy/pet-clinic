package com.yuancheng.petclinic.services.map;

import com.yuancheng.petclinic.models.Vet;
import com.yuancheng.petclinic.services.SpecialityService;
import com.yuancheng.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
  private final SpecialityService specialityService;

  public VetMapService(SpecialityService specialityService) {
    this.specialityService = specialityService;
  }

  @Override
  public Set<Vet> findAll() {
    return super.findAll();
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Vet object) {
    super.delete(object);
  }

  @Override
  public Vet save(Vet object) {
    if (object.getSpecialities().size() > 0) {
      object.getSpecialities().forEach(speciality -> {
        if (speciality.getId() == null) {
          specialityService.save(speciality);
        }
      });
    }
    return super.save(object);
  }

  @Override
  public Vet findById(Long id) {
    return super.findById(id);
  }
}
