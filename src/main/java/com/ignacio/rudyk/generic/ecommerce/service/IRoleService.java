package com.ignacio.rudyk.generic.ecommerce.service;

import com.ignacio.rudyk.generic.ecommerce.repository.entity.Role;

public interface IRoleService {

    Role findByCode(String code);

}