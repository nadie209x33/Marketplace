package com.uade.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Integer addressId;
    private String postalCode;
    private String street;
    private String apt;
    private String others;
    private String name;
    private List<Integer> usersInfoIds;
}
