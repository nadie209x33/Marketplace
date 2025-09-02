package com.uade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Lista_de_Espera", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_ID", "item_ID"})
    })
public class ListaDeEspera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LDE_ID")
    private Integer lDEID;

    private Integer ldeId;

    @ManyToOne
    @JoinColumn(name = "user_ID", referencedColumnName = "user_ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "item_ID", referencedColumnName = "item_ID")
    private Inventario item;
}

