package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.repository.AddressRepository;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    @Autowired
    public void setRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address findById(Long id) {
        return addressRepository.getOne(id);
    }

    public Address getAddressByUserDeliveryAddress(Address userAddress) {
        return addressRepository
                .getAddressByCountryEqualsAndCityEqualsAndAddressEquals(
                        userAddress.getCountry(), userAddress.getCity(),
                        userAddress.getAddress());
    }

    public void save(Address address) {
        addressRepository.save(address);
    }

}
