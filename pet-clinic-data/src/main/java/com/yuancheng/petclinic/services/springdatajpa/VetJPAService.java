package com.yuancheng.petclinic.services.springdatajpa;

import com.yuancheng.petclinic.models.Vet;
import com.yuancheng.petclinic.repositories.VetRepository;
import com.yuancheng.petclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetJPAService implements VetService {

  private final VetRepository vetRepository;

  public VetJPAService(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  @Override
  public Set<Vet> findAll() {
    Set<Vet> vets = new HashSet<>();
    vetRepository.findAll().iterator().forEachRemaining(vets::add);
    return vets;
  }

  @Override
  public Vet findById(Long aLong) {
    return vetRepository.findById(aLong).orElse(null);
  }

  @Override
  public Vet save(Vet object) {
    /*
      be aware that when save Vet, if we have unsaved Speciality attached to Vet
      it will throw an error
     */
    return vetRepository.save(object);
  }

  @Override
  public void delete(Vet object) {
    vetRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    vetRepository.deleteById(aLong);
  }
}
