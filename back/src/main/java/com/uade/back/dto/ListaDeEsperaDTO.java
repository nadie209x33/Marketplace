package com.uade.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListaDeEsperaDTO {
    private Integer ldeId;
    private Integer userId;
    private Integer itemId;
}
