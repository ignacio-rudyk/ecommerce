package com.ignacio.rudyk.generic.ecommerce.repository.entities;

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