package org.example.paymentservice.service;

import org.example.paymentservice.entity.Payment;
import org.example.paymentservice.entity.Receipt;
import org.example.paymentservice.repo.PaymentRepository;
import org.example.paymentservice.repo.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    private Random random = new Random();

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }

    public List<Payment> getPaymentsByUserId(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    public Payment processPayment(Payment payment) {
        // Validate mock card data
        if (!validateCardData(payment)) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            return paymentRepository.save(payment);
        }

        // Simulate payment processing
        boolean paymentSuccess = simulatePaymentTransaction();

        if (paymentSuccess) {
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            payment.setPaymentTime(LocalDateTime.now());
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
        }

        Payment savedPayment = paymentRepository.save(payment);

        // Generate receipt if payment successful
        if (paymentSuccess) {
            generateReceipt(savedPayment);
        }

        return savedPayment;
    }

    public Payment refundPayment(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            if (payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
                payment.setStatus(Payment.PaymentStatus.REFUNDED);
                return paymentRepository.save(payment);
            }
        }
        return null;
    }

    public Receipt generateReceipt(Payment payment) {
        Receipt receipt = new Receipt(payment);
        return receiptRepository.save(receipt);
    }

    public Optional<Receipt> getReceiptByNumber(String receiptNumber) {
        return receiptRepository.findByReceiptNumber(receiptNumber);
    }

    public Optional<Receipt> getReceiptByTransactionId(String transactionId) {
        return receiptRepository.findByTransactionId(transactionId);
    }

    public List<Receipt> getReceiptsByUserId(String userId) {
        return receiptRepository.findByUserId(userId);
    }

    private boolean validateCardData(Payment payment) {
        // Mock validation - in real scenario, this would validate with payment gateway
        String cardNumber = payment.getCardNumber();
        if (cardNumber == null || cardNumber.length() < 16) {
            return false;
        }

        // Simulate card validation failure for specific test cases
        if (cardNumber.equals("4000000000000002")) {
            return false; // Declined card
        }

        return true;
    }

    private boolean simulatePaymentTransaction() {
        // Simulate 90% success rate
        return random.nextDouble() < 0.9;
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}