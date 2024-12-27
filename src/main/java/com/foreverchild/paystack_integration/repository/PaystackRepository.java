package com.foreverchild.paystack_integration.repository;

import com.foreverchild.paystack_integration.model.PaystackTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaystackRepository extends JpaRepository<PaystackTransaction,Long> {
    PaystackTransaction findByReference(String ref);
}
