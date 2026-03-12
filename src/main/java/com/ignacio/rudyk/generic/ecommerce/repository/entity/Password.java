package com.ignacio.rudyk.generic.ecommerce.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "password")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password_encripted")
    private String passwordEncripted;

    @Column(name = "salt")
    private String salt;

    public Password() {
    }

    public Password(String passwordEncripted, String salt) {
        this.passwordEncripted = passwordEncripted;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordEncripted() {
        return passwordEncripted;
    }

    public void setPasswordEncripted(String passwordEncripted) {
        this.passwordEncripted = passwordEncripted;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}