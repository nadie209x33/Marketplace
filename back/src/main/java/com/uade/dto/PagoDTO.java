package com.uade.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Integer pagoId;
    private Integer monto;
    private String medio;
    private Instant timestamp;
    private String txId;
    private Integer pedidoId;
}
