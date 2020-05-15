package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Address;
import com.brena.ecommerce.models.Item;
import com.brena.ecommerce.models.User;
import com.brena.ecommerce.repositories.AddressRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Address findById(Long id) {
        Optional<Address> address = addressRepo.findById(id);
        return address.orElse(null);
    }
}
