package com.uade.back.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.back.dto.address.AddressDto;
import com.uade.back.service.address.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto, Principal principal) {
        return ResponseEntity.ok(addressService.createAddress(addressDto, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAddresses(Principal principal) {
        return ResponseEntity.ok(addressService.getAddressesByUserName(principal.getName()));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Integer addressId, Principal principal) {
        return ResponseEntity.ok(addressService.getAddressById(addressId, principal.getName()));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Integer addressId, @RequestBody AddressDto addressDto, Principal principal) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, addressDto, principal.getName()));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId, Principal principal) {
        addressService.deleteAddress(addressId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
