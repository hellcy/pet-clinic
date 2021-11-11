package com.yuancheng.petclinic.repositories;

import com.yuancheng.petclinic.models.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
