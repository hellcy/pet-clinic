package com.yuancheng.petclinic.repositories;

import com.yuancheng.petclinic.models.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
