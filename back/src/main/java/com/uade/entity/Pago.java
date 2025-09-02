package com.uade.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "Pago")
public class Pago {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "pago_ID")
private Integer pagoId;

@Column(nullable = false, name = "monto")
private Integer monto;

@Column(nullable = false, name = "medio")
private String medio;

@Column(nullable = false, name = "timestamp")
private Instant timestamp;

@Column(nullable = false, name = "TX_ID")
private Integer txId;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "pedido_ID", referencedColumnName = "pedido_ID")
    private Pedido pedido;

}