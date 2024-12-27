package com.foreverchild.paystack_integration.dto.request;

public record FinalizeTransactionRequest(
        String transfer_code,
        String otp
) {

}
