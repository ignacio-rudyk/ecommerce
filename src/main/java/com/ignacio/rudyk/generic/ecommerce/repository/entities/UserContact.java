package com.ignacio.rudyk.generic.ecommerce.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_contact")
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "indicative")
    private String indicative;

    public UserContact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIndicative() {
        return indicative;
    }

    public void setIndicative(String indicative) {
        this.indicative = indicative;
    }
}
