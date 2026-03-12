package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.NewUserDTO;
import com.ignacio.rudyk.generic.ecommerce.enumerate.RoleEnum;
import com.ignacio.rudyk.generic.ecommerce.enumerate.UserStateEnum;
import com.ignacio.rudyk.generic.ecommerce.exception.EcommerceException;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Password;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.User;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.UserContact;
import com.ignacio.rudyk.generic.ecommerce.service.IRoleService;
import com.ignacio.rudyk.generic.ecommerce.service.IUserService;
import com.ignacio.rudyk.generic.ecommerce.service.IUserStateService;
import com.ignacio.rudyk.generic.ecommerce.util.CryptographyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class UserService implements IUserService {

    private IRoleService roleService;

    private IUserStateService userStateService;

    public UserService(IRoleService roleService, IUserStateService userStateService) {
        this.roleService = roleService;
        this.userStateService = userStateService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public void createUser(NewUserDTO newUserDTO) {
            User newUser = new User();
            newUser.setPassword(generatePassword(newUserDTO.password()));
            newUser.setRoleId(roleService.findByCode(RoleEnum.USER.getCode()).getId());
            newUser.setFirstName(newUserDTO.firstName());
            newUser.setLastName(newUserDTO.lastName());
            newUser.setUserContact(generateUserContact(newUserDTO));
            newUser.setCreatedAt(new Date());
            newUser.setUserState(userStateService.findByCode(UserStateEnum.ACTIVO.getCode()));
            newUser.setBithday(newUserDTO.bithday());
    }

    private Password generatePassword(String password) {
        String salt = CryptographyUtil.generateSalt();
        String  passwordStr;
        try {
            passwordStr = CryptographyUtil.encrypt(password, salt);
        } catch (Exception e) {
            LOGGER.error("Error al encriptar la contraseña: {}", e.getMessage(), e);
            throw new EcommerceException("Error al generar la contraseña");
        }
        return new Password(passwordStr, salt);
    }

    private UserContact generateUserContact(NewUserDTO newUserDTO) {
        UserContact userContact = new UserContact();
        userContact.setEmail(newUserDTO.email());
        userContact.setPhoneNumber(newUserDTO.phone());
        userContact.setIndicative(newUserDTO.indicative());
        return userContact;
    }

}