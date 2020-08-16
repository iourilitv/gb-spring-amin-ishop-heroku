package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.repository.AddressRepository;
import ru.geekbrains.spring.ishop.utils.filters.AddressFilter;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import java.util.List;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private UtilFilter utilFilter;

    @Autowired
    public void setRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> findAll(Sort sort) {
        return addressRepository.findAll(sort);
    }

    public Page<Address> findAll(AddressFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return addressRepository.findAll(filter.getSpec(), pageRequest);
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

    public void delete(Address address) {
        addressRepository.delete(address);
    }

}
