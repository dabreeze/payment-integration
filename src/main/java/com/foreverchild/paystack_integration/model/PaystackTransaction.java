package com.foreverchild.paystack_integration.model;

import com.foreverchild.paystack_integration.enums.PricingPlanType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "paystack_transaction")
public class PaystackTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Column(name = "paid_at")
    private String paidAt;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "channel")
    private String channel;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "pricing_plan_type")
    @Enumerated(EnumType.STRING)
    private PricingPlanType pricingPlanType = PricingPlanType.BASIC;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false, nullable = false)
    private Date createdOn;
}
