package com.uade.back.dto.cupon;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class CuponResponse {
    private Integer id;
    private String codigo;
    private Double porcentajeDescuento;
    private Instant fechaExpiracion;
    private Integer usosMaximos;
    private Integer usosActuales;
    private Boolean activo;
}
