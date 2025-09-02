package com.uade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Lista_de_Espera")
public class ListaDeEspera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LDE_ID")
    private Integer lDEID;

    //TODO agregar user_ID
    //TODO agregar item_ID
    //TODO agregar regla de uniquidad

}

