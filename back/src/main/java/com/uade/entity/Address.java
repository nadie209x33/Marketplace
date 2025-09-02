package com.uade.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Column(name="address_ID")
    private Integer addressId;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "apt", nullable = false)
    private String apt;

    @Column(name = "others", nullable = false)
    private String others;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name ="User_Address",
        joinColumns = @JoinColumn(name = "Address_ID"),
        inverseJoinColumns = @JoinColumn(name = "Uinfo_ID")
    )
    private List<UserInfo> usersInfo = new ArrayList<>();
    

}
