package com.uade.back.dto.cupon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidarCuponResponse {
    private boolean valido;
    private Double porcentajeDescuento;
}
