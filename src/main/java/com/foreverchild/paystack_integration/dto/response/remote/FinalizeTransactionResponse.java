package com.foreverchild.paystack_integration.dto.response.remote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinalizeTransactionResponse {
    public boolean status;
    public String message;
    public Data data;

    public static class Data{
        public String domain;
        public int amount;
        public String currency;
        public String reference;
        public String source;
        public Object source_details;
        public String reason;
        public String status;
        public Object failures;
        public String transfer_code;
        public Object titan_code;
        public Object transferred_at;
        public int id;
        public int integration;
        public int recipient;
        public Date createdAt;
        public Date updatedAt;

    }
}
