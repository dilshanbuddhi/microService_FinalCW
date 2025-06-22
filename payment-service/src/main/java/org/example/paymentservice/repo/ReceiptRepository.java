package org.example.paymentservice.repo;

import org.example.paymentservice.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByReceiptNumber(String receiptNumber);
    Optional<Receipt> findByTransactionId(String transactionId);
    List<Receipt> findByUserId(String userId);
}
