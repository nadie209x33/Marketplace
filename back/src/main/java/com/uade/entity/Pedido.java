package com.uade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_ID")
    private Integer pedidoId;

    @Column(name = "list_ID")
    private Integer listId;

    @ManyToOne
    @JoinColumn(name = "user_ID", referencedColumnName = "user_ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "delivery_ID", referencedColumnName = "delivery_ID")
    private Delivery delivery;

    @Column(nullable = false, name = "status")
    private String status;
}