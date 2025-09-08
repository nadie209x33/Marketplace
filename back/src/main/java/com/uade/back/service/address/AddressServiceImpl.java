package com.uade.back.service.address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uade.back.dto.address.AddressDto;
import com.uade.back.dto.address.CreateAddressRequest;
import com.uade.back.entity.Address;
import com.uade.back.entity.UserInfo;
import com.uade.back.entity.Usuario;
import com.uade.back.repository.AddressRepository;
import com.uade.back.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UsuarioRepository usuarioRepository;

    private UserInfo getCurrentUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUserInfo_Mail(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
        return usuario.getUserInfo();
    }

    @Override
    public AddressDto createAddress(CreateAddressRequest addressRequest) {
        UserInfo userInfo = getCurrentUserInfo();

        Address address = new Address();
        address.setStreet(addressRequest.getStreet());
        address.setApt(addressRequest.getApt());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setOthers(addressRequest.getOthers());
        address.setName(addressRequest.getName());
        address.getUsersInfo().add(userInfo);
        
        Address savedAddress = addressRepository.save(address);
        return AddressDto.fromEntity(savedAddress);
    }

    @Override
    public List<AddressDto> getAddresses() {
        UserInfo userInfo = getCurrentUserInfo();
        
        return userInfo.getAddresses().stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressById(Integer addressId) {
        UserInfo userInfo = getCurrentUserInfo();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(ui -> ui.getUserInfoId().equals(userInfo.getUserInfoId()));

        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        return AddressDto.fromEntity(address);
    }

    @Override
    public AddressDto updateAddress(Integer addressId, CreateAddressRequest addressRequest) {
        UserInfo userInfo = getCurrentUserInfo();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(ui -> ui.getUserInfoId().equals(userInfo.getUserInfoId()));

        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        address.setStreet(addressRequest.getStreet());
        address.setApt(addressRequest.getApt());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setOthers(addressRequest.getOthers());
        address.setName(addressRequest.getName());

        Address updatedAddress = addressRepository.save(address);
        return AddressDto.fromEntity(updatedAddress);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        UserInfo userInfo = getCurrentUserInfo();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isUserAddress = address.getUsersInfo().stream()
                .anyMatch(ui -> ui.getUserInfoId().equals(userInfo.getUserInfoId()));

        if (!isUserAddress) {
            throw new RuntimeException("Address does not belong to the user");
        }

        addressRepository.delete(address);
    }
}
