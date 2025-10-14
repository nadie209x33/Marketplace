package com.uade.back.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserUpdateDTO {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private Boolean isEnabled;
    private Boolean isEmailConfirmed;
}
