package com.uade.back.controller;

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
import com.uade.back.dto.address.CreateAddressRequest;
import com.uade.back.service.address.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody CreateAddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.createAddress(addressRequest));
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Integer addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Integer addressId,
            @RequestBody CreateAddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, addressRequest));
    }

    // ⬇️ ESTE es el método del snippet
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build(); // 204
    }
}
