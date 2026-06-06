package com.ignacio.rudyk.generic.ecommerce.controller;

import com.ignacio.rudyk.generic.ecommerce.dto.ProductRequestDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.ResponseDTO;
import com.ignacio.rudyk.generic.ecommerce.service.IProductService;
import com.ignacio.rudyk.generic.ecommerce.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private IProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create-product")
    public ResponseEntity<ResponseDTO> createProduct(HttpServletRequest httpServletRequest,
            @RequestBody ProductRequestDTO newProduct) {
        LOGGER.info("Llamado al servicio POST /create-product - body: {}", newProduct);
        productService.createProduct(newProduct);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getProduct(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        LOGGER.info("Llamado al servicio GET /get-product - ID: {}", id);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllProducts(HttpServletRequest httpServletRequest,
            @PageableDefault(size = 10) Pageable pageable) {
        LOGGER.info("Llamado al servicio GET /product - page: {}, size: {}", pageable.getPageNumber(),
                pageable.getPageSize());
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, productService.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateProduct(HttpServletRequest httpServletRequest,
            @RequestBody ProductRequestDTO updateProduct) {
        LOGGER.info("Llamado al servicio PUT /update-product - body: {}", updateProduct);
        productService.updateProduct(updateProduct);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        LOGGER.info("Llamado al servicio DELETE /delete-product - ID: {}", id);
        productService.deleteProduct(id);
        return HttpUtil.isSucceful2xxResponse(httpServletRequest, null);
    }

}