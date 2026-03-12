package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.dto.NewUserDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.ResponseDTO;
import com.ignacio.rudyk.generic.ecommerce.service.IUserService;
import com.ignacio.rudyk.generic.ecommerce.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    private ResponseEntity<ResponseDTO> createUser(@RequestBody NewUserDTO newUser, HttpServletRequest httpRequest) {
        userService.createUser(newUser);
        return HttpUtil.isSucceful2xxResponse(httpRequest, null);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ResponseDTO> getUser() {
        return ResponseEntity.ok(new ResponseDTO());
    }

    /**
     * Reemplaza/actualiza un usuario que ya existe
     * **/
    @PutMapping("/{id}")
    private ResponseEntity<ResponseDTO> updateUser(){
        return ResponseEntity.ok(new ResponseDTO());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseDTO> deleteUser(){
        return ResponseEntity.ok(new ResponseDTO());
    }

}