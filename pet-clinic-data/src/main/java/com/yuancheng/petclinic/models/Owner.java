package com.yuancheng.petclinic.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person{

  @Builder
  public Owner(Long id, String firstName, String lastName, String address, String telephone, String city, Set<Pet> pets) {
    super(id, firstName, lastName);
    this.address = address;
    this.telephone = telephone;
    this.city = city;
    if (pets != null) {
      this.pets = pets;
    }
  }

  @Column(name = "address")
  private String address;

  @Column(name = "telephone")
  private String telephone;

  @Column(name = "city")
  private String city;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private Set<Pet> pets = new HashSet<>();

  /**
   * Return the pet with the given name, or null if none found for this Owner
   * @param name to test
   * @return
   */
  public Pet getPet(String name) {
    return getPet(name, false);
  }

  /**
   * Return the pet with the given name, or null if none found for this Owner
   * @param name to test
   * @param ignoreNew
   * @return
   */
  public Pet getPet(String name, boolean ignoreNew) {
    name = name.toLowerCase();
    for (Pet pet : pets) {
      if (!ignoreNew || !pet.isNew()) {
        String compName = pet.getName();
        compName = compName.toLowerCase();
        if (compName.equals(name)) {
          return pet;
        }
      }
    }
    return null;
  }
}
