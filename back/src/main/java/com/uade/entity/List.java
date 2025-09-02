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
@Table(name = "List", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"list_ID", "item_ID"})
    })
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tlist_ID")
    private Integer tlistId;

    @Column(name = "list_ID")
    private Integer listId;

    @ManyToOne
    @JoinColumn(name = "item_ID", referencedColumnName = "item_ID")
    private Inventario item;

    @Column (nullable = false, name = "quantity")
    private Integer quantity;


}