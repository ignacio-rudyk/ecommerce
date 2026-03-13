package com.ignacio.rudyk.generic.ecommerce.service.implementation;

import com.ignacio.rudyk.generic.ecommerce.dto.NewUserDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.UserDTO;
import com.ignacio.rudyk.generic.ecommerce.enumerate.RoleEnum;
import com.ignacio.rudyk.generic.ecommerce.enumerate.UserStateEnum;
import com.ignacio.rudyk.generic.ecommerce.exception.EcommerceException;
import com.ignacio.rudyk.generic.ecommerce.exception.UserNotFoundException;
import com.ignacio.rudyk.generic.ecommerce.mapper.IUserMapper;
import com.ignacio.rudyk.generic.ecommerce.repository.IUserRepository;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.Password;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.User;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.UserContact;
import com.ignacio.rudyk.generic.ecommerce.service.IRoleService;
import com.ignacio.rudyk.generic.ecommerce.service.IUserService;
import com.ignacio.rudyk.generic.ecommerce.service.IUserStateService;
import com.ignacio.rudyk.generic.ecommerce.util.CryptographyUtil;
import org.mapstruct.control.MappingControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private IRoleService roleService;

    private IUserStateService userStateService;

    private IUserRepository userRepository;

    private IUserMapper userMapper;

    public UserService(IRoleService roleService, IUserStateService userStateService, IUserRepository userRepository, IUserMapper userMapper) {
        this.roleService = roleService;
        this.userStateService = userStateService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
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
        userRepository.save(newUser);
    }

    @Override
    public UserDTO findById(Long id) {
        if(id == null)
            return null;
        Optional<User> opUser = userRepository.findByIdWithUserContact(id);
        return opUser.map(value -> userMapper.toDTO(value)).orElse(null);
    }

    @Override
    @Transactional
    public void updateUser(NewUserDTO newUserDTO) {
        if(newUserDTO.id() == null)
            throw new UserNotFoundException();
        Optional<User> opUser = userRepository.findById(newUserDTO.id());
        if(opUser.isEmpty())
            throw new UserNotFoundException();
        User user = opUser.get();
        user.setFirstName(newUserDTO.firstName());
        user.setLastName(newUserDTO.lastName());
        user.getUserContact().setEmail(newUserDTO.email());
        user.getUserContact().setPhoneNumber(newUserDTO.phone());
        user.getUserContact().setIndicative(newUserDTO.indicative());
        user.setBithday(newUserDTO.bithday());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if(id == null)
            throw new UserNotFoundException();
        Optional<User> opUser = userRepository.findById(id);
        if(opUser.isEmpty())
            throw new UserNotFoundException();
        this.userRepository.delete(opUser.get());
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