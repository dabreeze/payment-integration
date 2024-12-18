package com.foreverchild.paystack_integration.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConstants {
    public static final Integer STATUS_CODE_OK = 200;
    public static final Integer STATUS_CODE_CREATED = 201;
    public static final String PAYSTACK_PLAN = "https://api.paystack.co/plan";
    public static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
    public static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
}
