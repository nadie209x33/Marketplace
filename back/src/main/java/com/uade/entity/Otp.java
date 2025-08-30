package com.uade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@DaTa
@Entity
@Table(name = "OTP")
public class OTP {

public OTP(){}
public OTP(String otp, Timestamp timestamp){
this.otp = otp;
This.timestamp = timestamp;
}

@Id
@@GeneratedValue(strategy = GenerationType.IDENTITY)
private Int otp_ID;

@Column
private String otp;

@Column(nullable = false)
private Timestamp timestamp;


}