package com.yuancheng.petclinic.bootstrap;

import com.yuancheng.petclinic.models.*;
import com.yuancheng.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
  private final OwnerService ownerService;
  private final VetService vetService;
  private final PetTypeService petTypeService;
  private final SpecialityService specialityService;
  private final VisitService visitService;
  private final PetService petService;

  public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService, PetService petService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
    this.specialityService = specialityService;
    this.visitService = visitService;
    this.petService = petService;
  }

  @Override
  public void run(String... args) throws Exception {

    int count = petTypeService.findAll().size();

    if (count == 0) {
      loadData();
    }

  }

  private void loadData() {
    /* Create Specialities */
    Speciality radiology = new Speciality();
    radiology.setDescription("Radiology");
    Speciality savedRadiology = specialityService.save(radiology);

    Speciality surgery = new Speciality();
    surgery.setDescription("surgery");
    Speciality savedSurgery = specialityService.save(surgery);

    Speciality dentistry  = new Speciality();
    dentistry.setDescription("dentistry");
    Speciality savedDentistry = specialityService.save(dentistry);
    /* End creating Specialities */

    /* Create Owners */
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
    /* End creating Owners */

    /* Create Vets */
    Vet vet1 = new Vet();
    vet1.setFirstName("Luke");
    vet1.setLastName("Zhang");
    vet1.getSpecialities().add(savedRadiology);
    vetService.save(vet1);

    Vet vet2 = new Vet();
    vet2.setFirstName("Meixi");
    vet2.setLastName("Zhu");
    vet2.getSpecialities().add(savedSurgery);
    vetService.save(vet2);

    System.out.println("Loaded vets...");
    /* End creating Vets */

    /* Create PetType */
    PetType dog = new PetType();
    dog.setName("Dog");
    PetType savedDogPetType = petTypeService.save(dog);

    PetType cat = new PetType();
    dog.setName("Cat");
    PetType savedCatPetType = petTypeService.save(cat);
    /* End creating PetType */

    /* Create Pets */
    Pet yuansPet = new Pet();
    yuansPet.setOwner(owner1);
    yuansPet.setPetType(cat);
    yuansPet.setBirthDate(LocalDate.now());
    yuansPet.setName("Dudu");
    owner1.getPets().add(yuansPet);
    petService.save(yuansPet);

    Pet nancysPet = new Pet();
    nancysPet.setOwner(owner2);
    nancysPet.setPetType(cat);
    nancysPet.setBirthDate(LocalDate.now());
    nancysPet.setName("another cat");
    owner2.getPets().add(nancysPet);
    petService.save(nancysPet);
    /* End creating Pets */

    /* Create Visit */
    Visit catVisit = new Visit();
    catVisit.setDate(LocalDate.now());
    catVisit.setDescription("Sneezy Cat");
    catVisit.setPet(nancysPet);
    visitService.save(catVisit);
    /* End creating Visits */
  }
}
