package com.yuancheng.petclinic.bootstrap;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.Pet;
import com.yuancheng.petclinic.models.PetType;
import com.yuancheng.petclinic.models.Vet;
import com.yuancheng.petclinic.services.OwnerService;
import com.yuancheng.petclinic.services.PetTypeService;
import com.yuancheng.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
    owner1.setAddress("Waterloo");
    owner1.setCity("Sydney");
    owner1.setTelephone("0410210494");
    ownerService.save(owner1);

    Owner owner2 = new Owner();
    owner2.setFirstName("Nan");
    owner2.setLastName("Yang");
    owner2.setAddress("Ryde");
    owner2.setCity("Perth");
    owner2.setTelephone("0410888888");

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

    Pet yuansPet = new Pet();
    yuansPet.setOwner(owner1);
    yuansPet.setPetType(cat);
    yuansPet.setBirthDate(LocalDate.now());
    yuansPet.setName("Dudu");
    owner1.getPets().add(yuansPet);


    Pet nancysPet = new Pet();
    nancysPet.setOwner(owner2);
    nancysPet.setPetType(cat);
    nancysPet.setBirthDate(LocalDate.now());
    nancysPet.setName("another cat");
    owner2.getPets().add(nancysPet);

  }
}
