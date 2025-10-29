package com.uade.back.dto.cupon;

import lombok.Data;
import java.time.Instant;

@Data
public class CrearCuponRequest {
    private String codigo;
    private Double porcentajeDescuento;
    private Instant fechaExpiracion;
    private Integer usosMaximos;
}
