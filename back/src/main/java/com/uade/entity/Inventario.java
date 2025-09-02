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
@Table(name = "Inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_ID" )
    private Integer itemID;

    @Column(name = "description" ) 
    private String description;

    @Column(name = "active" )
    private Boolean active;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price" )
    private Integer price;

    //TODO Agregar cat_ID

    @Column (nullable = false, name = "name")
    private String name;
}
    

