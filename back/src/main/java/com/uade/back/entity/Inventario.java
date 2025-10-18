package com.uade.back.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
    @Column(name = "item_ID")
    private Integer itemId;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private double price;

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cat_ID", referencedColumnName = "cat_ID")
    private Categoria categoria;

    @OneToMany(
        mappedBy = "inventario",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<Image> images = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ListaDeEspera> listaDeEspera;

    @OneToMany(mappedBy = "item")
    private java.util.List<com.uade.back.entity.List> listItems;
}
