package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.dto.NewUserDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.UserDTO;

public interface IUserService {

    void createUser(NewUserDTO newUserDTO);

    UserDTO findById(Long id);

    void updateUser(NewUserDTO newUserDTO);

    void deleteUser(Long id);

}