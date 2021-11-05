package com.yuancheng.petclinic.bootstrap;

import com.yuancheng.petclinic.models.Owner;
import com.yuancheng.petclinic.models.Vet;
import com.yuancheng.petclinic.services.OwnerService;
import com.yuancheng.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
  private final OwnerService ownerService;
  private final VetService vetService;

  public DataLoader(OwnerService ownerService, VetService vetService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
  }

  @Override
  public void run(String... args) throws Exception {
    Owner owner1 = new Owner();
    owner1.setId(1L);
    owner1.setFirstName("Yuan");
    owner1.setLastName("Cheng");

    ownerService.save(owner1);

    Owner owner2 = new Owner();
    owner1.setId(2L);
    owner1.setFirstName("Nan");
    owner1.setLastName("Yang");

    ownerService.save(owner2);

    System.out.println("Loaded owners...");

    Vet vet1 = new Vet();
    vet1.setId(1L);
    vet1.setFirstName("Luke");
    vet1.setLastName("Zhang");

    vetService.save(vet1);

    Vet vet2 = new Vet();
    vet1.setId(2L);
    vet1.setFirstName("Meixi");
    vet1.setLastName("Zhu");

    vetService.save(vet2);

    System.out.println("Loaded vets...");
  }
}
