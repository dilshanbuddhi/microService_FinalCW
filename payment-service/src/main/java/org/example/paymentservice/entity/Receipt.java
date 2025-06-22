package org.example.paymentservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String receiptNumber;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String vehicleId;

    @Column(nullable = false)
    private Long parkingSpaceId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime issuedAt = LocalDateTime.now();

    private String parkingDuration;
    private String spaceLocation;

    // Constructors

    public Receipt(Payment payment) {
        this.receiptNumber = payment.getCardNumber();
        this.transactionId = payment.getTransactionId();
        this.userId = payment.getUserId();
        this.vehicleId = payment.getVehicleId();
        this.parkingSpaceId = payment.getParkingSpaceId();
        this.amount = payment.getAmount();
        this.paymentMethod = payment.getPaymentMethod().toString();
    }

    public Receipt() {

    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public Long getParkingSpaceId() { return parkingSpaceId; }
    public void setParkingSpaceId(Long parkingSpaceId) { this.parkingSpaceId = parkingSpaceId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }

    public String getParkingDuration() { return parkingDuration; }
    public void setParkingDuration(String parkingDuration) { this.parkingDuration = parkingDuration; }

    public String getSpaceLocation() { return spaceLocation; }
    public void setSpaceLocation(String spaceLocation) { this.spaceLocation = spaceLocation; }
}
