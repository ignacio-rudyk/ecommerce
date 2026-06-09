package com.ignacio.rudyk.generic.ecommerce.mapper;

import com.ignacio.rudyk.generic.ecommerce.dto.UserContactDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.UserDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.UserStateDTO;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.User;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.UserContact;
import com.ignacio.rudyk.generic.ecommerce.repository.entity.UserState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserDTO toDTO(User user);

    UserContactDTO toDTO(UserContact userContact);

    UserStateDTO toDTO(UserState userState);

}