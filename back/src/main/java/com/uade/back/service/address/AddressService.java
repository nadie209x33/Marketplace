package com.uade.back.service.address;

import java.util.List;

import com.uade.back.dto.address.AddressDto;
import com.uade.back.dto.address.CreateAddressRequest;

public interface AddressService {

    AddressDto createAddress(CreateAddressRequest addressRequest);

    List<AddressDto> getAddresses();

    AddressDto getAddressById(Integer addressId);

    AddressDto updateAddress(Integer addressId, CreateAddressRequest addressRequest);

    /**
     * Elimina la relación del usuario actual con la address y borra la address si
     * queda huérfana
     */
    void deleteAddress(Integer addressId);
}
