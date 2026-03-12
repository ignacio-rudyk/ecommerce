package com.ignacio.rudyk.generic.ecommerce.exception.handler;

import com.ignacio.rudyk.generic.ecommerce.dto.response.ErrorDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.MetadataDTO;
import com.ignacio.rudyk.generic.ecommerce.dto.response.ResponseDTO;
import com.ignacio.rudyk.generic.ecommerce.exception.EcommerceException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class EcommerceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EcommerceExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        LOGGER.error("Error inesperado en - Method: {} - URI: {} - Message: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage(), ex);

        MetadataDTO metadata = new MetadataDTO();
        metadata.setPath(request.getRequestURI());
        metadata.setMethod(request.getMethod());
        metadata.setCode(1);

        ErrorDTO error = new ErrorDTO();
        error.setCode(-1);
        error.setMsg("Error interno en el servidor");

        ResponseDTO response = new ResponseDTO();
        response.setData(null);
        response.setErrorList(List.of(error));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(EcommerceException.class)
    public ResponseEntity<ResponseDTO> handleLibraryException(
            EcommerceException ex,
            HttpServletRequest request) {

        LOGGER.error("Hubo un error en la operacion - Method: {} - URI: {} - Message: {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage(), ex);

        MetadataDTO metadata = new MetadataDTO();
        metadata.setPath(request.getRequestURI());
        metadata.setMethod(request.getMethod());
        metadata.setCode(ex.getHttpStatus().value());

        ErrorDTO error = new ErrorDTO();
        error.setCode(ex.getCode());
        error.setMsg(ex.getMessage());

        ResponseDTO response = new ResponseDTO();
        response.setMetadata(metadata);
        response.setData(null);
        response.setErrorList(List.of(error));

        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

}