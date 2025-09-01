package com.uade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_ID;

    @Column(name = "postal_code", nullable = false)
    private String postal_code;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "apt", nullable = false)
    private String apt;

    @Column(name = "others", nullable = false)
    private String others;

    @Column(name = "name", nullable = false)
    private String name;
    

}
