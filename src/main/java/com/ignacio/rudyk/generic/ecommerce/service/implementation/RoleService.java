package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.exception.EcommerceException;
import com.ignacio.rudyk.generic.ecommerce.repository.IRoleRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Role;
import com.ignacio.rudyk.generic.ecommerce.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    private IRoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByCode(String code) {
        try {
            return roleRepository.findByCode(code);
        } catch (Exception e) {
            LOGGER.error("Hubo un error al buscar el role - code: {} - msg: {}", code, e.getMessage());
            throw new EcommerceException();
        }
    }

}