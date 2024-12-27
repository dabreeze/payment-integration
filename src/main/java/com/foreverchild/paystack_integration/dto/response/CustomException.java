package com.foreverchild.paystack_integration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {
    private boolean status;
    private String message;
    private LocalDateTime timeStamp;
}
