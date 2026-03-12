package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.exception.EcommerceException;
import com.ignacio.rudyk.generic.ecommerce.repository.IUserStateRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.UserState;
import com.ignacio.rudyk.generic.ecommerce.service.IUserStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserStateService implements IUserStateService {

    private IUserStateRepository userStateRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStateService.class);

    public UserStateService(IUserStateRepository userStateRepository) {
        this.userStateRepository = userStateRepository;
    }

    @Override
    public UserState findByCode(String code) {
        try {
            return userStateRepository.findByCode(code);
        } catch (Exception e) {
            LOGGER.error("Hubo un error al buscar el el estado del usuario - code: {} - msg: {}", code, e.getMessage());
            throw new EcommerceException();
        }
    }

}