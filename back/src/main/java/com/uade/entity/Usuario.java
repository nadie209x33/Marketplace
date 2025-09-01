package com.uade.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_ID;

    @Column (nullable = false, name="passkey" )
    private String passkey; 

    @Column (name="auth_level")
    private Integer authLevel; 

    @Column (nullable = false, name= "active")
    private Boolean active; 

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "otp_ID", referencedColumnName = "otp_ID")
    private Otp otp;

}