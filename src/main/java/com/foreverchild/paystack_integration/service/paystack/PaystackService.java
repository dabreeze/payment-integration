package com.foreverchild.paystack_integration.service.paystack;

import com.foreverchild.paystack_integration.dto.request.CreatePlanRequest;
import com.foreverchild.paystack_integration.dto.request.InitializePaymentRequest;
import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import com.foreverchild.paystack_integration.dto.response.InitializePaymentResponse;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import kong.unirest.HttpResponse;
import org.springframework.http.ResponseEntity;

public interface PaystackService {

    CreatePlanResponse createPaystackPlan(CreatePlanRequest createPlanRequest);
    InitializePaymentResponse initializePaystackPayment(InitializePaymentRequest initializePaymentRequest);
    PaymentVerificationResponse paystackPaymentVerification(String ref, String plan, Long id);
}
