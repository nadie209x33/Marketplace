package com.uade.dto;

import lombok.Data;

@Data
public class UserInfo {
    private final int uinfo_ID;
    private final String first_name;
    private final String last_name;
    private final String mail;
    private final boolean confirm_mail;
}
