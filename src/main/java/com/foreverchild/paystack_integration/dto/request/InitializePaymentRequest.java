package com.foreverchild.paystack_integration.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentRequest {

    @Min(value = 100, message = "amount should be more than 100 NGN")
    @Pattern(regexp = "^[0-9]+$", message = "Can only be numbers")
    @JsonProperty("amount")
    private String amount;

    @NotNull(message = "Email cannot be null")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Currency cannot be null")
    @JsonProperty("currency")
    private String currency;

    //@NotNull(message = "Plan cannot be null")
    @JsonProperty("plan")
    private String plan;

    @NotNull(message = "Channels cannot be null")
    @JsonProperty("channels")
    private String[] channels;

    @JsonProperty("reference")
    private String reference;
}
