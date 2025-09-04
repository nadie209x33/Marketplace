package com.uade.back.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {
    private String firstName;
    private String lastName;
    private String mail;
    private String passkey;
}
