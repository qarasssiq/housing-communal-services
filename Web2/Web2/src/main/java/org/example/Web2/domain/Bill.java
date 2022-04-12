package org.example.Web2.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service serviceId;

    private String status = "Не оплачено";

    private Double meter;

    private Double amount;

    private LocalDate date;

    public Bill() {
    }

    public Bill(Service serviceId, String status, Double meter, Double amount, LocalDate date) {
        this.serviceId = serviceId;
        this.status = status;
        this.meter = meter;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMeter() {
        return meter;
    }

    public void setMeter(Double meter) {
        this.meter = meter;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
