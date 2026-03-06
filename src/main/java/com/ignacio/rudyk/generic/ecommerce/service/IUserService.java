package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.repository.dto.NewUserDTO;

public interface IUserService {

    void createUser(NewUserDTO newUser);

}