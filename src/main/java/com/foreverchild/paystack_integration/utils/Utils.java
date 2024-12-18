package com.foreverchild.paystack_integration.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import com.foreverchild.paystack_integration.enums.PricingPlanType;
import com.foreverchild.paystack_integration.model.Customer;
import com.foreverchild.paystack_integration.model.PaystackTransaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Utils {
    public <T> T jsonToObject(String json, Class<T> c) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(json, c);
        } catch (Exception e) {
            return null;
        }
    }

    public PaystackTransaction mapPaysatckTransactionForSaving(PaymentVerificationResponse paymentVerificationResponse,
                                                               Customer customer, PricingPlanType pricingPlanType) {
        return PaystackTransaction.builder()
                .customer(customer)
                .reference(paymentVerificationResponse.getData().getReference())
                .amount(paymentVerificationResponse.getData().getAmount())
                .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                .paidAt(paymentVerificationResponse.getData().getPaidAt())
                .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                .channel(paymentVerificationResponse.getData().getChannel())
                .currency(paymentVerificationResponse.getData().getCurrency())
                .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                .pricingPlanType(pricingPlanType)
                .createdOn(new Date())
                .build();
    }
}
