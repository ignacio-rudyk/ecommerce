package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.repository.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/create-payment")
    private ResponseEntity<ResponseDTO> createPayment(){
        return ResponseEntity.ok(new ResponseDTO());
    }

    @PostMapping("/{id}/refund-payment")
    private ResponseEntity<ResponseDTO> refundPayment(@PathVariable Long id){
        return ResponseEntity.ok(new ResponseDTO());
    }

    @GetMapping("/by-id/{id}")
    private ResponseEntity<ResponseDTO> getPaymentById(@PathVariable Long id){
        return ResponseEntity.ok(new ResponseDTO());
    }

    @GetMapping("/by-user/{user-id}")
    private ResponseEntity<ResponseDTO> getPaymentByUser(@PathVariable("user-id") Long userId){
        return ResponseEntity.ok(new ResponseDTO());
    }

}