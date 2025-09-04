package com.uade.back.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private Integer userInfoId;
    private String firstName;
    private String lastName;
    private String mail;
    private Boolean confirmMail;
    private List<Integer> addressIds;
}
