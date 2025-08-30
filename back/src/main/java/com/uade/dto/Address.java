package com.uade.dto;

import lombok.Data;

@Data
public class Address {
    private final int address_ID;
    private final String postal_code;
    private final String street;
    private final String apt;
    private final String others;
    private final String name;
}
