package com.uade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioNuevoDTO {
    private String firstName;
    private String lastName;
    private String mail;
    private String passkey;
}
