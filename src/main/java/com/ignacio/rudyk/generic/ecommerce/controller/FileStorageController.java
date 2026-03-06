package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.repository.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileStorageController {

    @GetMapping("/{id}")
    private ResponseEntity<ResponseDTO> createUser() {
        return ResponseEntity.ok(new ResponseDTO());
    }

}