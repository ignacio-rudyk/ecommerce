package com.ignacio.rudyk.ecommerce.util;

import com.ignacio.rudyk.ecommerce.repository.dto.response.MetadataDTO;
import com.ignacio.rudyk.ecommerce.repository.dto.response.ResponseDTO;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class HttpUtil {

    public static ResponseDTO isSucceful2xxResponse(HttpRequest httpRequest, Object data) {
        MetadataDTO metadata = new MetadataDTO(httpRequest.getURI().getPath(), httpRequest.getMethod().name(), HttpStatus.OK);
        return new ResponseDTO(metadata, data, new ArrayList<>());
    }

}