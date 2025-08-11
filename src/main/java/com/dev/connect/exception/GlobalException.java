package com.dev.connect.exception;

import com.dev.connect.ApiResponse.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        HashMap<String,String> responseError=new HashMap<>();
        ex.getBindingResult().
                getFieldErrors().
                forEach(oneFieldError->responseError.put(oneFieldError.getField(), oneFieldError.getDefaultMessage()));
        return new ResponseEntity<>(responseError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ExceptionResponse response=ExceptionResponse.builder()
                .message(ex.getMessage())
                .status("failed")
                .datetime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCradentialException.class)
    public ResponseEntity<ExceptionResponse> invalidCradentialExceptionHandler(InvalidCradentialException ex){
        ExceptionResponse response= ExceptionResponse.builder().message(ex.getMessage()).status("not authorized").datetime(LocalDateTime.now()).build();
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }
}
