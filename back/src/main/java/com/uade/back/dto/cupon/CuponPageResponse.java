package com.uade.back.dto.cupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CuponPageResponse {
    private List<CuponResponse> cupones;
    private int totalPages;
}
