package com.yuancheng.petclinic.services;

import com.yuancheng.petclinic.models.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long>{
  Owner findByLastName(String lastName);
  List<Owner> findAllByLastNameLike(String lastName);
}
