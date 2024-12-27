package com.foreverchild.paystack_integration.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateTransferReceipient(
        @JsonProperty("type")
        String type,
        @JsonProperty("name")
        String name,
        @JsonProperty("accountNumber")
        String account_number,
        @JsonProperty("bankCode")
        String bank_code,
        String description,
        String currency,
        @JsonProperty(value = "authorizationCode")
        String authorization_code

) {
}
