package com.uade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Integer userId;
    private Integer otpId;
    private String passkey;
    private Integer authLevel;
    private Boolean active;
    private Integer userInfoId;
}
