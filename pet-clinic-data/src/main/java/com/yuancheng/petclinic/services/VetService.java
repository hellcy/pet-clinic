package com.yuancheng.petclinic.services;

import com.yuancheng.petclinic.models.Vet;

import java.util.Set;

public interface VetService {
  Vet findById(Long id);

  Vet save(Vet vet);

  Set<Vet> findAll();
}
