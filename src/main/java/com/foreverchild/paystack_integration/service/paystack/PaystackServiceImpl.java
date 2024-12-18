package com.foreverchild.paystack_integration.service.paystack;

import com.foreverchild.paystack_integration.constants.ServiceConstants;
import com.foreverchild.paystack_integration.dto.request.CreatePlanRequest;
import com.foreverchild.paystack_integration.dto.request.InitializePaymentRequest;
import com.foreverchild.paystack_integration.dto.response.CreatePlanResponse;
import com.foreverchild.paystack_integration.dto.response.InitializePaymentResponse;
import com.foreverchild.paystack_integration.dto.response.PaymentVerificationResponse;
import com.foreverchild.paystack_integration.enums.PricingPlanType;
import com.foreverchild.paystack_integration.model.Customer;
import com.foreverchild.paystack_integration.model.PaystackTransaction;
import com.foreverchild.paystack_integration.repository.CustomerRepository;
import com.foreverchild.paystack_integration.repository.PaystackRepository;
import com.foreverchild.paystack_integration.utils.HttpHelper;
import com.foreverchild.paystack_integration.utils.Utils;
import jakarta.transaction.Transactional;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        System.out.println("request to url"+ ServiceConstants.PAYSTACK_INITIALIZE_PAY);
        String ref = generatePaymentRef().replaceAll("[^a-zA-Z0-9]","");
        initializePaymentRequest.setReference(ref);
        System.out.println(ref);
        HttpResponse<String> response = httpHelper.post(ServiceConstants.PAYSTACK_INITIALIZE_PAY, initializePaymentRequest);
        if (response.getStatus() == ServiceConstants.STATUS_CODE_OK) {
            initializePaymentResponse = utils.jsonToObject(response.getBody(), InitializePaymentResponse.class);
        } else {
            initializePaymentResponse = utils.jsonToObject(response.getBody(), InitializePaymentResponse.class);

        }
        return initializePaymentResponse;
    }

    private String generatePaymentRef(){
        return "PP-".concat(LocalDateTime.now().toString());
    }

    @Transactional
    @Override
    public PaymentVerificationResponse paystackPaymentVerification(String ref, String plan, Long id) {
        PaymentVerificationResponse paymentVerificationResponse = null;
        PaystackTransaction paystackTransaction = null;
        HttpResponse<String> response = httpHelper.get(ServiceConstants.PAYSTACK_VERIFY + ref);

        if (response.getStatus() == ServiceConstants.STATUS_CODE_OK) {
            paymentVerificationResponse = utils.jsonToObject(response.getBody(), PaymentVerificationResponse.class);
        }
        if (paymentVerificationResponse != null && paymentVerificationResponse.getData().getStatus().equals("success")) {

            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isEmpty()) {
                throw new IllegalArgumentException("No customer found with ID " + id);
            }
            PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

            paystackTransaction = utils.mapPaysatckTransactionForSaving(paymentVerificationResponse, customer.get(), pricingPlanType);
            paystackRepository.save(paystackTransaction);

            return paymentVerificationResponse;

        }
        if (paymentVerificationResponse == null || paymentVerificationResponse.getData().getStatus().equals("false")) {

            return paymentVerificationResponse;

        } else {
            return paymentVerificationResponse;
        }

    }
}
