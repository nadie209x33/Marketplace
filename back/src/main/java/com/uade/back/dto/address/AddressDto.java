package com.uade.back.dto.address;

import com.uade.back.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Integer addressId;
    private String postalCode;
    private String street;
    private String apt;
    private String others;
    private String name;

    public static AddressDto fromEntity(Address address) {
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .apt(address.getApt())
                .others(address.getOthers())
                .name(address.getName())
                .build();
    }
}
