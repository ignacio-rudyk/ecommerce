package com.ignacio.rudyk.generic.ecommerce.dto;

import java.util.Date;

public record UserDTO(Long id, Long roleId, String firstName, String lastName, UserContactDTO userContact, Long avatarFileId, Date createdAt, UserStateDTO userState, Date birthday) { }
