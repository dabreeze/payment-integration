package com.foreverchild.paystack_integration.dto.response.remote;

import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SendPaymentResponse {
    private boolean status;
    private String message;
    private String type;
    private String code;
    private CreatePlanResponse.Data data;

}
