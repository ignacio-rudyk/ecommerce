package com.ignacio.rudyk.generic.ecommerce.util;

import com.ignacio.rudyk.generic.ecommerce.dto.response.ErrorDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.MetadataDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class HttpUtil {

    public static ResponseEntity<ResponseDTO> isSucceful2xxResponse(HttpServletRequest httpRequest, Object data) {
        MetadataDTO metadata = new MetadataDTO(httpRequest.getRequestURI(), httpRequest.getMethod(), HttpStatus.OK.value());
        ErrorDTO error = new ErrorDTO();
        error.setCode(0);
        error.setMsg("Operacion exitosa");
        ResponseDTO response = new ResponseDTO(metadata, data, List.of(error));
        return ResponseEntity.ok(response);
    }

}