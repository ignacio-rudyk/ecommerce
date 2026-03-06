package com.ignacio.rudyk.generic.ecommerce.repository.dto;

import java.util.Date;

public record NewUserDTO(String email, String password, String firstName, String lastName, String phone, Date bithday) { }