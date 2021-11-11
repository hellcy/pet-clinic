package com.yuancheng.petclinic.repositories;

import com.yuancheng.petclinic.models.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
