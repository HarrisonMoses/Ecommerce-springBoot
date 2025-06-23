package com.harrisonmoses.store.services;

import com.harrisonmoses.store.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private AddressRepository addressRepository;

    public void getAddress() {
        addressRepository.findById(1L).orElseThrow();
    }

    @Transactional
    public void fetchRelated(){
        var address = addressRepository.findById(1L).orElseThrow();
        var userEmail = address.getUser().getEmail();
        System.out.println(userEmail);

    }

    public void deleteRelated(){

    }


}
