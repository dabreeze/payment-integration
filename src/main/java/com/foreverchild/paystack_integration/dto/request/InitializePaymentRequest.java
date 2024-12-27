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
    @JsonProperty(value = "amount", required = true)
    private String amount;

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Invalid email format"
    )
    @JsonProperty(value = "email", required = true)
    private String email;

    //@NotNull(message = "Plan cannot be null")
    @JsonProperty("plan")
    private String plan;

    @NotNull(message = "Payment reference cannot be empty")
    @JsonProperty(value = "paymentReference", required = true)
    private String paymentReference;

    @NotNull(message = "customer ID cannot be empty")
    @JsonProperty(value = "customerId", required = true)
    private long customerId;
}
