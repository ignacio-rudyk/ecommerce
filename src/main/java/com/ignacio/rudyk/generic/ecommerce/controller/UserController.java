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
    private ResponseEntity<ResponseDTO> getUser(@PathVariable Long id, HttpServletRequest httpRequest) {
        return HttpUtil.isSucceful2xxResponse(httpRequest, userService.findById(id));
    }

    @PutMapping("/update-user")
    private ResponseEntity<ResponseDTO> updateUser(@RequestBody NewUserDTO newUser, HttpServletRequest httpRequest) {
        userService.updateUser(newUser);
        return HttpUtil.isSucceful2xxResponse(httpRequest, null);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id, HttpServletRequest httpRequest) {
        userService.deleteUser(id);
        return HttpUtil.isSucceful2xxResponse(httpRequest, null);
    }

}