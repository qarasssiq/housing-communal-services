package org.example.Web2.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bill_id")
    private Bill billId;

    private LocalDate date;

    private Double amount;

    private String checkAddress;

    public Payment() {
    }

    public Payment(Bill bill, LocalDate date, Double amount, String checkAddress) {
        this.billId = bill;
        this.date = date;
        this.amount = amount;
        this.checkAddress = checkAddress;
    }

    public Bill getBillId() {
        return billId;
    }

    public void setBillId(Bill bill) {
        this.billId = bill;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheck(String checkAddress) {
        this.checkAddress = checkAddress;
    }
}
