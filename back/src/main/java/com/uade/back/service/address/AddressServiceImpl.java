package com.uade.back.service.address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uade.back.dto.address.AddressDto;
import com.uade.back.entity.Address;
import com.uade.back.entity.UserInfo;
import com.uade.back.repository.AddressRepository;
import com.uade.back.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public AddressDto createAddress(AddressDto addressDto, String userName) {
        UserInfo userInfo = userInfoRepository.findByMail(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setApt(addressDto.getApt());
        address.setPostalCode(addressDto.getPostalCode());
        address.setOthers(addressDto.getOthers());
        address.setName(addressDto.getName());
        address.getUsersInfo().add(userInfo);
        
        Address savedAddress = addressRepository.save(address);
        return AddressDto.fromEntity(savedAddress);
    }

    @Override
    public List<AddressDto> getAddressesByUserName(String userName) {
        UserInfo userInfo = userInfoRepository.findByMail(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return userInfo.getAddresses().stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressById(Integer addressId, String userName) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(userInfo -> userInfo.getMail().equals(userName));

        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        return AddressDto.fromEntity(address);
    }

    @Override
    public AddressDto updateAddress(Integer addressId, AddressDto addressDto, String userName) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(userInfo -> userInfo.getMail().equals(userName));

        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        address.setStreet(addressDto.getStreet());
        address.setApt(addressDto.getApt());
        address.setPostalCode(addressDto.getPostalCode());
        address.setOthers(addressDto.getOthers());
        address.setName(addressDto.getName());

        Address updatedAddress = addressRepository.save(address);
        return AddressDto.fromEntity(updatedAddress);
    }

    @Override
    public void deleteAddress(Integer addressId, String userName) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(userInfo -> userInfo.getMail().equals(userName));

        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        addressRepository.delete(address);
    }
}
