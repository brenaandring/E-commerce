package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Address;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.AddressRepo;
import org.springframework.stereotype.Service;

@Service
public class AddressServ {
    private final AddressRepo addressRepo;

    public AddressServ(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public void saveAddress(Address address) {
        addressRepo.save(address);
    }

    public Address findByUser(User user) {
        return addressRepo.findByUser(user);
    }
}
