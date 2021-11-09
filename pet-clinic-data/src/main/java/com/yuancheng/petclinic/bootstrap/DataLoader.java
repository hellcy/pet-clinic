package com.yuancheng.petclinic.bootstrap;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.PetType;
import com.yuancheng.petclinic.models.Vet;
import com.yuancheng.petclinic.services.OwnerService;
import com.yuancheng.petclinic.services.PetTypeService;
import com.yuancheng.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
  private final OwnerService ownerService;
  private final VetService vetService;
  private final PetTypeService petTypeService;

  public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
  }

  @Override
  public void run(String... args) throws Exception {
    Owner owner1 = new Owner();
    owner1.setFirstName("Yuan");
    owner1.setLastName("Cheng");

    ownerService.save(owner1);

    Owner owner2 = new Owner();
    owner2.setFirstName("Nan");
    owner2.setLastName("Yang");

    ownerService.save(owner2);

    System.out.println("Loaded owners...");

    Vet vet1 = new Vet();
    vet1.setFirstName("Luke");
    vet1.setLastName("Zhang");

    vetService.save(vet1);

    Vet vet2 = new Vet();
    vet2.setFirstName("Meixi");
    vet2.setLastName("Zhu");

    vetService.save(vet2);

    System.out.println("Loaded vets...");

    PetType dog = new PetType();
    dog.setName("Dog");
    PetType savedDogPetType = petTypeService.save(dog);

    PetType cat = new PetType();
    dog.setName("Cat");
    PetType savedCatPetType = petTypeService.save(cat);
  }
}
