package com.foreverchild.paystack_integration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendPaymentRequest {

    private String source;
    private BigInteger amount;
    private String recipient;
    private String reason;
    private String currency;
    private String reference;


}
