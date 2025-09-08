package com.uade.back.service.address;

import java.util.List;

import com.uade.back.dto.address.AddressDto;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto, String userName);
    List<AddressDto> getAddressesByUserName(String userName);
    AddressDto getAddressById(Integer addressId, String userName);
    AddressDto updateAddress(Integer addressId, AddressDto addressDto, String userName);
    void deleteAddress(Integer addressId, String userName);
}
