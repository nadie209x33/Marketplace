package com.uade.back.dto.order;

import com.uade.back.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Integer paymentId;
    private Integer amount;
    private String paymentMethod;
    private Instant timestamp;
    private PaymentStatus status;
    private Integer transactionId;
}
