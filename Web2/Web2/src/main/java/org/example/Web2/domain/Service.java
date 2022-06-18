package org.example.Web2.domain;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id")
    private Address addressId;

    private String type;

    private String personalAccountNumber;

    private Double lastMeter = 0.0;

    public Service() {
    }

    public Service(Address addressId, String type, String personalAccountNumber) {
        this.addressId = addressId;
        this.type = type;
        this.personalAccountNumber = personalAccountNumber;
    }

    public Long getId() {
        return id;
    }

    public Address getAddressId() {
        return addressId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPersonalAccountNumber() {
        return personalAccountNumber;
    }

    public void setPersonalAccountNumber(String personalAccountNumber) {
        this.personalAccountNumber = personalAccountNumber;
    }

    public Double getLastMeter() {
        return lastMeter;
    }

    public void setLastMeter(Double lastMeter) {
        this.lastMeter = lastMeter;
    }
}
