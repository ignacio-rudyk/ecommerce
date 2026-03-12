package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.repository.entity.UserState;

public interface IUserStateService {

    UserState findByCode(String code);

}
