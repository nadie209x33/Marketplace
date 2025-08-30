package com.uade.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "OTP")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_ID")
    private int otp_ID;

    @Column(name = "otp")
    private String otp;

    @Column(nullable = false, name = "timestamp")
    private Instant timestamp;

    public Otp(String otp, Instant timestamp){
    this.otp = otp;
    this.timestamp = timestamp;

    }

}