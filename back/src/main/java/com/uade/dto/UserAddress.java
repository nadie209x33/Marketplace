package com.uade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private Integer uiaId;
    private Integer uinfoId;
    private Integer addressId;
}
