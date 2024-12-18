package com.foreverchild.paystack_integration.controller;

import com.foreverchild.paystack_integration.dto.request.CreatePlanRequest;
import com.foreverchild.paystack_integration.dto.request.InitializePaymentRequest;
import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import com.foreverchild.paystack_integration.dto.response.InitializePaymentResponse;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import com.foreverchild.paystack_integration.service.paystack.PaystackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/paystack",produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    private final PaystackService paystackService;

    public PaymentController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    @PostMapping("/create-plan")
    public ResponseEntity<CreatePlanResponse> createPlan(@Validated @RequestBody CreatePlanRequest createPlanRequest) throws Exception {
       CreatePlanResponse response = paystackService.createPaystackPlan(createPlanRequest);
        return response.isStatus() ? new ResponseEntity<>(response, HttpStatus.CREATED) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST
        );
    }

    @PostMapping("/initialize-payment")
    public ResponseEntity<InitializePaymentResponse> initializePayment(@Validated @RequestBody InitializePaymentRequest initializePaymentRequest) throws Throwable {
        InitializePaymentResponse response = paystackService.initializePaystackPayment(initializePaymentRequest);
        return response.isStatus() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verify-payment/{reference}/{plan}/{id}")
    public ResponseEntity<PaymentVerificationResponse> paymentVerification(@PathVariable(value = "reference") String reference,
                                                           @PathVariable(value = "plan") String plan,
                                                           @PathVariable(value = "id") Long id) throws Exception {
        PaymentVerificationResponse response = paystackService.paystackPaymentVerification(reference, plan, id);
        return response.getStatus().equals("success")? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
