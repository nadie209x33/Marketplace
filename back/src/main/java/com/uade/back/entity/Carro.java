package com.uade.back.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carro_ID;

    @OneToOne
    @JoinColumn(name = "user_ID", referencedColumnName = "user_ID")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "cupon_id")
    private Cupon cupon;

    @OneToMany(
        mappedBy = "carro",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private java.util.List<com.uade.back.entity.List> items =
        new java.util.ArrayList<>();
}
