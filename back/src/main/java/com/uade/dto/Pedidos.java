package com.uade.dto;

import lombok.Data;

@Data
public class Pedidos {
    private final int pedido_ID;
    private final int list_ID;
    private final int user_ID;
    private final int delvery_ID;
    private final boolean status;
}
