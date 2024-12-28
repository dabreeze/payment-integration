package com.foreverchild.paystack_integration.service.paystack;

import com.foreverchild.paystack_integration.constants.ServiceConstants;
import com.foreverchild.paystack_integration.dto.request.*;
import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import com.foreverchild.paystack_integration.dto.response.InitializePaymentResponse;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import com.foreverchild.paystack_integration.dto.response.remote.FinalizeTransactionResponse;
import com.foreverchild.paystack_integration.enums.PricingPlanType;
import com.foreverchild.paystack_integration.model.Customer;
import com.foreverchild.paystack_integration.model.PaystackTransaction;
import com.foreverchild.paystack_integration.repository.CustomerRepository;
import com.foreverchild.paystack_integration.repository.PaystackRepository;
import com.foreverchild.paystack_integration.utils.HttpHelper;
import com.foreverchild.paystack_integration.utils.Utils;
import jakarta.transaction.Transactional;
import kong.unirest.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaystackServiceImpl implements PaystackService {

    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private Utils utils;
    @Autowired
    private ServiceConstants constants;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaystackRepository paystackRepository;

    @Override
    public CreatePlanResponse createPaystackPlan(CreatePlanRequest createPlanRequest) {
        CreatePlanResponse createPlanResponse = new CreatePlanResponse();
        HttpResponse<String> response = httpHelper.post(ServiceConstants.PAYSTACK_PLAN, createPlanRequest);
        if (response.getStatus() == ServiceConstants.STATUS_CODE_CREATED) {
            createPlanResponse = utils.jsonToObject(response.getBody(), CreatePlanResponse.class);
        } else {
            createPlanResponse = utils.jsonToObject(response.getBody(), CreatePlanResponse.class);

        }
        return createPlanResponse;
    }

    @Override
    public InitializePaymentResponse initializePaystackPayment(InitializePaymentRequest initializePaymentRequest) {
        InitializePaymentResponse initializePaymentResponse = null;
        System.out.println("request to url" + ServiceConstants.PAYSTACK_RECEIVE_PAYMENT);
        int amount = Integer.parseInt(initializePaymentRequest.getAmount())*100;
        initializePaymentRequest.setAmount(String.valueOf(amount));

        Optional<Customer> customer = customerRepository.findById(initializePaymentRequest.getCustomerId());
        if (customer.isEmpty()) {
            return InitializePaymentResponse.builder()
                    .message("Customer not found")
                    .status(false)
                    .build();
        }
        PaymentVerificationResponse verifyPayment = paystackPaymentVerification(initializePaymentRequest.getPaymentReference(),
                initializePaymentRequest.getPlan(), initializePaymentRequest.getCustomerId()
        );

        if (verifyPayment != null && verifyPayment.getStatus().equals("false")) {


            Map<String, String> paystackRequest = new HashMap<>();
            paystackRequest.put("amount", initializePaymentRequest.getAmount());
            paystackRequest.put("email", initializePaymentRequest.getEmail());
            HttpResponse<String> response = httpHelper.post(ServiceConstants.PAYSTACK_RECEIVE_PAYMENT, paystackRequest);
            if (response.getStatus() == ServiceConstants.STATUS_CODE_OK) {
                initializePaymentResponse = utils.jsonToObject(response.getBody(), InitializePaymentResponse.class);
//            save to db

            } else {
                initializePaymentResponse = utils.jsonToObject(response.getBody(), InitializePaymentResponse.class);

            }

            return initializePaymentResponse;
        }
        if (verifyPayment.getData().getStatus().equals("success")) {
            return InitializePaymentResponse.builder()
                    .message("Duplicate transaction")
                    .status(false)
                    .build();
        }

        return initializePaymentResponse;
    }


    @Transactional
    @Override
    public PaymentVerificationResponse paystackPaymentVerification(String ref, String plan, long id) {
        PaymentVerificationResponse paymentVerificationResponse = null;
        PaystackTransaction paystackTransaction = null;
        try {
            HttpResponse<String> response = httpHelper.get(ServiceConstants.PAYSTACK_VERIFY + ref);
            paymentVerificationResponse = utils.jsonToObject(response.getBody(), PaymentVerificationResponse.class);

            if (response.getStatus() == ServiceConstants.STATUS_CODE_OK && paymentVerificationResponse.getData().getStatus().equals("success")) {
                paymentVerificationResponse = utils.jsonToObject(response.getBody(), PaymentVerificationResponse.class);
                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                paystackTransaction = utils.mapPaysatckTransactionForSaving(paymentVerificationResponse, String.valueOf(id), pricingPlanType);
                PaystackTransaction savedTransaction = paystackRepository.findByReference(paystackTransaction.getReference());
                if (savedTransaction == null) {
                    paystackRepository.save(paystackTransaction);
                }
                return paymentVerificationResponse;
            }
        }catch (Exception ex){
            ex.getStackTrace();
        }
        return paymentVerificationResponse;

    }


    @Override
    public HttpResponse<String> initiateTransfer(SendPaymentRequest sendPaymentRequest) {
        FinalizeTransactionResponse transferResponse = null;

        sendPaymentRequest.setAmount(sendPaymentRequest.getAmount().multiply(BigInteger.valueOf(100)));
        System.out.println("Sending request to url" + ServiceConstants.PAYSTACK_SEND_PAYMENT);
        return httpHelper.post(ServiceConstants.PAYSTACK_SEND_PAYMENT, sendPaymentRequest);
    }

    @Override
    public HttpResponse<String> getListOfCountries() {
        HttpResponse<String> response = httpHelper.get(ServiceConstants.PAYSTACK_LIST_OF_COUNTRY);

        return response;
    }

    @Override
    public HttpResponse<String> getListOfStates(String country) {
        System.out.println("url "+ServiceConstants.PAYSTACK_LIST_OF_STATES+country);
        HttpResponse<String> response = httpHelper.get(ServiceConstants.PAYSTACK_LIST_OF_STATES+country);
        return response;
    }

    @Override
    public HttpResponse<String> getListOfBanks() {
        HttpResponse<String> response = httpHelper.get(ServiceConstants.PAYSTACK_LIST_OF_BANKS+"bank");
        return response;
    }

    @Override
    public HttpResponse<String> validateBankAccount(String accountNumber, String bankCode) {
        HttpResponse<String> response = httpHelper.get(ServiceConstants.PAYSTACK_VALIDATE_ACCOUNT_NUMBER+
                accountNumber+"&"+"bank_code="+bankCode);
        return response;
    }

    @Override
    public HttpResponse<String> createReceipient(CreateTransferReceipient createTransferReceipient) {
        System.out.println(createTransferReceipient);
        System.out.println("url "+ServiceConstants.PAYSTACK_CREATE_RECEIPIENT);
        return httpHelper.post(ServiceConstants.PAYSTACK_CREATE_RECEIPIENT, createTransferReceipient );
    }

    @Override
    public HttpResponse<String> finalizeTransaction(FinalizeTransactionRequest finalizeTransactionRequest) {
        return httpHelper.post(ServiceConstants.PAYSTACK_FINALIZE_TRANSACTION, finalizeTransactionRequest );

    }
}
