package com.uade.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class Pago {

    private final int pago_ID;
    private final String medio;
    private final Instant timestamp;
    private final String tx_ID;
    private final int pedido_ID;

}
