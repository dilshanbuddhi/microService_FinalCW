package org.example.paymentservice.controller;

import org.example.paymentservice.entity.Payment;
import org.example.paymentservice.entity.Receipt;
import org.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Payment> getPaymentByTransactionId(@PathVariable String transactionId) {
        Optional<Payment> payment = paymentService.getPaymentByTransactionId(transactionId);
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public List<Payment> getPaymentsByUserId(@PathVariable String userId) {
        return paymentService.getPaymentsByUserId(userId);
    }
    
    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable Payment.PaymentStatus status) {
        return paymentService.getPaymentsByStatus(status);
    }
    
    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        Payment processedPayment = paymentService.processPayment(payment);
        return ResponseEntity.ok(processedPayment);
    }
    
    @PostMapping("/{id}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long id) {
        Payment refundedPayment = paymentService.refundPayment(id);
        return refundedPayment != null ? ResponseEntity.ok(refundedPayment) : ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/receipts/number/{receiptNumber}")
    public ResponseEntity<Receipt> getReceiptByNumber(@PathVariable String receiptNumber) {
        Optional<Receipt> receipt = paymentService.getReceiptByNumber(receiptNumber);
        return receipt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/receipts/transaction/{transactionId}")
    public ResponseEntity<Receipt> getReceiptByTransactionId(@PathVariable String transactionId) {
        Optional<Receipt> receipt = paymentService.getReceiptByTransactionId(transactionId);
        return receipt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/receipts/user/{userId}")
    public List<Receipt> getReceiptsByUserId(@PathVariable String userId) {
        return paymentService.getReceiptsByUserId(userId);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}