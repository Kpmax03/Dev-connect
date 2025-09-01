package com.dev.connect.exception;

import com.dev.connect.apiResponse.ExceptionResponse;
import com.dev.connect.enums.PostType;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
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
                .status("not found ")
                .datetime(LocalDateTime.now().toString())
                .build();

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCradentialException.class)
    public ResponseEntity<ExceptionResponse> invalidCradentialExceptionHandler(InvalidCradentialException ex){
        ExceptionResponse response= ExceptionResponse.builder()
                .message(ex.getMessage())
                .status("not authorized")
                .datetime(LocalDateTime.now().toString())
                .build();

        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ExceptionResponse> illegalOperationExceptionHandler(IllegalOperationException ex){
        ExceptionResponse response= ExceptionResponse.builder()
                .message(ex.getMessage()).status("access dnied")
                .datetime(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(WantsToGiveException.class)
    public ResponseEntity<ExceptionResponse> WantsTOGiveExceptionHandler(WantsToGiveException ex){
        ExceptionResponse response=ExceptionResponse.builder()
                .message(ex.getMessage())
                .status("not defined")
                .datetime(LocalDateTime.now().toString())
                .build();

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    // for handling enums
    //HttpMessageNotReadableException for enum coming via @requestBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,Object>> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex){
        HashMap<String, Object> response = buildEnumExceptionHandler(ex);
        return ResponseEntity.badRequest().body(response);
    }
    //ConversionFailedException for enum coming via @pathvariable
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<HashMap<String,Object>> conversionFailedExceptionHandler(ConversionFailedException ex){

        HashMap<String, Object> response = buildEnumExceptionHandler(ex);
        response.put("your enum ",ex.getValue());

        return ResponseEntity.badRequest().body(response);

    }
    public HashMap<String,Object>  buildEnumExceptionHandler(Exception ex){
      //  ExceptionResponse response=new ExceptionResponse();
        HashMap<String, Object> response=new HashMap<>();
        response.put("error","invalid enum");

        if(ex.getMessage()!=null && ex.getMessage().contains("PostType")){

            response.put("valid enum ", Arrays.stream(PostType.values()).map(e->e.name()).collect(Collectors.toList()));
        }
        return response;
    }
}
