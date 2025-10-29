package com.uade.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cupon")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cupon_id")
    private Integer cuponId;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, name = "porcentaje_descuento")
    private Double porcentajeDescuento;

    @Column(nullable = false, name = "fecha_expiracion")
    private Instant fechaExpiracion;

    @Builder.Default
    @Column(nullable = false, name = "usos_maximos")
    private Integer usosMaximos = 1; // Por defecto, un solo uso

    @Builder.Default
    @Column(nullable = false, name = "usos_actuales")
    private Integer usosActuales = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = true;
}
