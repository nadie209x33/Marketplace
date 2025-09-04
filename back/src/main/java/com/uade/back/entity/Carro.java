package com.uade.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @OneToOne
    @JoinColumn(name = "list_ID", referencedColumnName = "list_ID")
    private List list;
}
