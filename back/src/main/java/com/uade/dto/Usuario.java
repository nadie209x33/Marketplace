package com.uade.dto;

import lombok.Data;

@Data
public class Usuario {
    private final int user_ID;
    private final int opt_ID;
    private final String passkey;
    private final int aunth_level;
    private final boolean active;
    private final int uinfo_ID;
}
