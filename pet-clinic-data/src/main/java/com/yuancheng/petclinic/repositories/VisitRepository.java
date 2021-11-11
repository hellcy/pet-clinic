package com.yuancheng.petclinic.repositories;

import com.yuancheng.petclinic.models.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
