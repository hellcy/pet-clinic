package com.yuancheng.petclinic.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity{

  @Builder
  public Pet(Long id, LocalDate birthDate, String name, Owner owner, PetType petType, Set<Visit> visits) {
    super(id);
    this.birthDate = birthDate;
    this.name = name;
    this.owner = owner;
    this.petType = petType;
    this.visits = visits;
  }

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Owner owner;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private PetType petType;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
  private Set<Visit> visits = new HashSet<>();
}
