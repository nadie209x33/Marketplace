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
@Table(name = "Pago")
public class Pago {

public Pago(){}
public Pago(Int monto, String medio, Timestamp timestamp, String TX_ID){
this.monto = monto;
this.medio = medio
This.timestamp = timestamp;
this.TX_ID = TX_ID;
}

@Id
@@GeneratedValue(strategy = GenerationType.IDENTITY)
private Int pago_ID;

@Column(nullable = false)
private Int monto;

@Column(nullable = false)
private String medio;

@Column(nullable = false)
private Timestamp timestamp;


}
