package com.uade.back.entity;

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
    @Column(name = "uinfo_ID")
    private Integer userInfoId;

    @Column (name = "first_name", nullable = false)
    private String firstName; 

    @Column (name = "last_name", nullable = false)
    private String lastName; 

    @Column (nullable = false)
    private String mail; 

    @Column (name = "confirm_mail", nullable = false)
    private Boolean confirmMail; 
    
    @ManyToMany(mappedBy = "usersInfo")
    private List<Address> addresses = new ArrayList<>();
}
