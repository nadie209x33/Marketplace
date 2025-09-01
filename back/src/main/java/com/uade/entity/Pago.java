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
@Entity
@NoArgsConstructor
@Table(name = "Pago")
public class Pago {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int pago_ID;

@Column(nullable = false)
private int monto;

@Column(nullable = false)
private String medio;

@Column(nullable = false)
private Instant timestamp;

@Column(nullable = false)
private int tx_ID;

public Pago(int monto, String medio, Instant timestamp, int tx_ID){
this.monto = monto;
this.medio = medio;
this.timestamp = timestamp;
this.tx_ID = tx_ID;
}


}
