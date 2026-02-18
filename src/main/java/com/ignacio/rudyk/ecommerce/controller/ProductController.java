package com.ignacio.rudyk.ecommerce.controller;

import com.ignacio.rudyk.ecommerce.repository.dto.ProductRequestDTO;
import com.ignacio.rudyk.ecommerce.repository.dto.response.ResponseDTO;
import com.ignacio.rudyk.ecommerce.util.HttpUtil;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @PostMapping
    public ResponseDTO createProduct(@RequestBody ProductRequestDTO productRequest, HttpRequest httpRequest) {
        return HttpUtil.isSucceful2xxResponse(httpRequest, null);
    }

}