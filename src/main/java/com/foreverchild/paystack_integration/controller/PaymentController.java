package com.foreverchild.paystack_integration.controller;

import com.foreverchild.paystack_integration.dto.request.*;
import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import com.foreverchild.paystack_integration.dto.response.InitializePaymentResponse;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import com.foreverchild.paystack_integration.service.paystack.PaystackService;
import kong.unirest.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/paystack", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping("/receive-payment")
    public ResponseEntity<InitializePaymentResponse> receivePayment(@Validated @RequestBody InitializePaymentRequest initializePaymentRequest) throws Throwable {
        InitializePaymentResponse response = paystackService.initializePaystackPayment(initializePaymentRequest);
        return response.isStatus() ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verify-payment/{reference}/{plan}/{id}")
    public ResponseEntity<PaymentVerificationResponse> paymentVerification(@PathVariable(value = "reference") String reference,
                                                                           @PathVariable(value = "plan") String plan,
                                                                           @PathVariable(value = "id") long id) throws Exception {
        PaymentVerificationResponse response = paystackService.paystackPaymentVerification(reference, plan, id);
        return response.getData().getStatus().equals("success") ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/create-recipient")
    public ResponseEntity<?> createRecipient(@RequestBody CreateTransferReceipient createTransferReceipient) {
        HttpResponse<String> response = paystackService.createReceipient(createTransferReceipient);
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(), HttpStatus.OK) : new ResponseEntity<>(response.getBody(),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/initialize-transfer")
    public ResponseEntity<String> initiateTransfer(SendPaymentRequest sendPaymentRequest) {
        HttpResponse<String> response = paystackService.initiateTransfer(sendPaymentRequest);
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(), HttpStatus.OK) :
                new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/finalize-transfer")
    public ResponseEntity<?> finalizeTransfer(@RequestBody FinalizeTransactionRequest finalizeTransactionRequest) {
        HttpResponse<String> response = paystackService.finalizeTransaction(finalizeTransactionRequest);
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(),HttpStatus.OK)
                : new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/list-country")
    public ResponseEntity<String> listCountry() {
        HttpResponse<String> response = paystackService.getListOfCountries();
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(), HttpStatus.OK)
                : new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/list-state/{country}")
    public ResponseEntity<String> listStates(@PathVariable(value = "country") String country) {
        HttpResponse<String> response = paystackService.getListOfStates(country);
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(), HttpStatus.OK)
                : new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/list-banks")
    public ResponseEntity<String> listBanks() {
        HttpResponse<String> response = paystackService.getListOfBanks();
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(), HttpStatus.OK)
                : new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/account/{accountNumber}/{bankCode}")
    public ResponseEntity<String> validateBankAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                      @PathVariable(value = "bankCode") String bankCode) {
        HttpResponse<String> response = paystackService.validateBankAccount(accountNumber, bankCode);
        return response.getStatus() == 200 ? new ResponseEntity<>(response.getBody(), HttpStatus.OK)
                : new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);

    }




}
