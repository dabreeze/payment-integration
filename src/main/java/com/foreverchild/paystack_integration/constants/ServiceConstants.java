package com.foreverchild.paystack_integration.constants;

import org.springframework.stereotype.Component;

@Component
public class ServiceConstants {
    public static final Integer STATUS_CODE_OK = 200;
    public static final Integer STATUS_CODE_CREATED = 201;
    public static final String PAYSTACK_PLAN = "https://api.paystack.co/plan";
    public static final String PAYSTACK_RECEIVE_PAYMENT = "https://api.paystack.co/transaction/initialize";
    public static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
    public static final String PAYSTACK_SEND_PAYMENT = "https://api.paystack.co/transfer";
    public static final String PAYSTACK_VALIDATE_ACCOUNT_NUMBER = "https://api.paystack.co/bank/resolve?account_number=";
    public static final String PAYSTACK_LIST_OF_BANKS = "https://api.paystack.co/";
    public static final String PAYSTACK_LIST_OF_COUNTRY = "https://api.paystack.co/country";
    public static final String PAYSTACK_LIST_OF_STATES = "https://api.paystack.co/address_verification/states?country=";
    public static final String PAYSTACK_CREATE_RECEIPIENT = "https://api.paystack.co/transferrecipient";
    public static final String PAYSTACK_FINALIZE_TRANSACTION = "https://api.paystack.co/transfer/finalize_transfer";
}
