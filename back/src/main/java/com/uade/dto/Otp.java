package com.uade.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class Otp {
    private final int opt_ID;
    private final String opt;
    private final Instant timestamp;
}
