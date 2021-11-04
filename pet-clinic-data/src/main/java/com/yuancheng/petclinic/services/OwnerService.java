package com.yuancheng.petclinic.services;

import com.yuancheng.petclinic.models.Owner;

public interface OwnerService extends CrudService<Owner, Long>{
  Owner findByLastName(String lastName);
}
