package com.uade.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Integer itemId;

    @Column(name = "description" ) 
    private String description;

    @Column(name = "active" )
    private Boolean active;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price" )
    private Integer price;

    @Column (nullable = false, name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cat_ID", referencedColumnName = "cat_ID")
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
        name = "Image_List",
        joinColumns = @JoinColumn(name = "item_ID"),
        inverseJoinColumns = @JoinColumn(name = "img_ID")
    )
    private List<Image> images;

    @OneToMany(mappedBy = "item")
    private List<ListaDeEspera> listaDeEspera;

    @OneToMany(mappedBy = "item")
    private List<List> listItems;
}
    

