package com.yuancheng.petclinic.repositories;

import com.yuancheng.petclinic.models.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
