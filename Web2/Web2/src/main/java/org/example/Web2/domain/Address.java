package org.example.Web2.domain;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User userId;

    private String city;

    private String address;

    private Integer numberOfResidents;

    private Integer apartmentSize;

    public Address() {}

    public Address(String city, String address, User user, Integer numberOfResidents, Integer apartmentSize) {
        this.city = city;
        this.address = address;
        this.userId = user;
        this.numberOfResidents = numberOfResidents;
        this.apartmentSize = apartmentSize;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumberOfResidents() {
        return numberOfResidents;
    }

    public void setNumberOfResidents(Integer numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
    }

    public Integer getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(Integer apartmentSize) {
        this.apartmentSize = apartmentSize;
    }
}
