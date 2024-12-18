package com.foreverchild.paystack_integration.utils;

import jakarta.annotation.PostConstruct;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class HttpHelper {

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    @PostConstruct
    private void init() {

        Unirest.config()
                .setDefaultHeader("Content-Type", "application/json")
                .setDefaultHeader("Authorization", "Bearer " + paystackSecretKey);
    }

    public HttpResponse<String> post(String url, Object data) {
        return Unirest.post(url).body(data).asString();
    }

    public HttpResponse<String> get(String url) {
        return Unirest.get(url).asString();
    }


}
