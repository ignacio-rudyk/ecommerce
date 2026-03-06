package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.repository.dto.NewUserDTO;
import com.ignacio.rudyk.generic.ecommerce.repository.dto.response.ResponseDTO;
import com.ignacio.rudyk.generic.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    public UserController(@Autowired IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    private ResponseEntity<ResponseDTO> createUser(@RequestBody NewUserDTO newUser) {
        userService.createUser(newUser);
        return ResponseEntity.ok(new ResponseDTO());
    }

    @GetMapping("/{id}")
    private ResponseEntity<ResponseDTO> getUser(){
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