package com.uade.back.dto.cupon;

import lombok.Data;
import java.time.Instant;

@Data
public class CuponUpdateRequest {
    private String codigo;
    private Double porcentajeDescuento;
    private Instant fechaExpiracion;
    private Integer usosMaximos;
    private Boolean activo;
}
