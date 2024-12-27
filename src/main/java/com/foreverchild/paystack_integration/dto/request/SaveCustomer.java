package com.foreverchild.paystack_integration.dto.request;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

public record SaveCustomer(

        @Column(name = "customerId")
        String customerId,

        @Column(name = "username", nullable = false)
        String username,

        @Column(name = "name", nullable = false)
        String name,

        @Column(name = "address", nullable = false)
        String address,

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_on", updatable = false, nullable = false)
        Date createdOn

        ) {
}
