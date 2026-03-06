package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.repository.dto.NewUserDTO;
import com.ignacio.rudyk.generic.ecommerce.repository.entities.User;
import com.ignacio.rudyk.generic.ecommerce.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    public void createUser(NewUserDTO newUserDTO) {
        User newUser = new User();
    }

}