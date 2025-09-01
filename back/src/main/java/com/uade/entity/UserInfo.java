package com.uade.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uinfo_ID;

    @Column (nullable = false)
    private String first_name; 

    @Column (nullable = false)
    private String last_name; 

    @Column (nullable = false)
    private String mail; 

    @Column (nullable = false)
    private Boolean confirm_mail; 
    
    @ManyToMany(mappedBy = "usersInfo")
    private List<Address> addresses = new ArrayList<>();
}
