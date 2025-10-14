package com.uade.back.dto.user;

import com.uade.back.entity.Role;
import com.uade.back.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String mail;
    private Role authLevel;
    private Boolean isActive;
    private Boolean isEmailConfirmed;

    public static UserListDTO fromEntity(Usuario usuario) {
        return UserListDTO.builder()
                .userId(usuario.getUser_ID())
                .firstName(usuario.getUserInfo().getFirstName())
                .lastName(usuario.getUserInfo().getLastName())
                .mail(usuario.getUsername())
                .authLevel(usuario.getAuthLevel())
                .isActive(usuario.getActive())
                .isEmailConfirmed(usuario.getUserInfo().getConfirmMail())
                .build();
    }
}
