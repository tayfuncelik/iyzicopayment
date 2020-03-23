package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByBankResponseIsNull(Pageable pageable);

    @Modifying
    @Query("UPDATE Payment p SET p.bankResponse = :returnCode   WHERE p.id = :paymentId")
    int updatePayment(@Param("returnCode") String returnCode, @Param("paymentId") Long paymentId);

    List<Payment> findPaymentsByBankResponseIsNotNull();

    Payment findPaymentsByPriceIs(BigDecimal price);
}
