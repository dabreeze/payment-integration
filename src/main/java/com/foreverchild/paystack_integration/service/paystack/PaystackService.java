package com.foreverchild.paystack_integration.service.paystack;

import com.foreverchild.paystack_integration.dto.request.*;
import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import com.foreverchild.paystack_integration.dto.response.InitializePaymentResponse;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import com.foreverchild.paystack_integration.dto.response.remote.FinalizeTransactionResponse;
import kong.unirest.HttpResponse;

public interface PaystackService {

    CreatePlanResponse createPaystackPlan(CreatePlanRequest createPlanRequest);
    InitializePaymentResponse initializePaystackPayment(InitializePaymentRequest initializePaymentRequest);
    PaymentVerificationResponse paystackPaymentVerification(String ref, String plan, long id);

    HttpResponse<String> initiateTransfer(SendPaymentRequest sendPaymentRequest);

    HttpResponse<String> getListOfCountries();

    HttpResponse<String> getListOfStates(String Country);

    HttpResponse<String> getListOfBanks();

    HttpResponse<String> validateBankAccount(String accountNumber, String bankCode);

    HttpResponse<String> createReceipient(CreateTransferReceipient createTransferReceipient);

    HttpResponse<String> finalizeTransaction(FinalizeTransactionRequest finalizeTransactionRequest);
}
