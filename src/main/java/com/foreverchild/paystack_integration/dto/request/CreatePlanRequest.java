package com.foreverchild.paystack_integration.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
public record CreatePlanRequest(
        @NotNull(message = "Plan name cannot be null")
        @JsonProperty("name")
        String name,

        @NotNull(message = "Interval cannot be null")
        @JsonProperty("interval")
        String interval,

        @NotNull(message = "Amount cannot be null")
        @JsonProperty("amount")
        @Min(value = 100, message = "amount should be more than 100 NGN")
        @Pattern(regexp = "^[0-9]+$", message = "Can only be numbers")
        String amount,

        @JsonProperty("currency")
        String currency
) {

}
