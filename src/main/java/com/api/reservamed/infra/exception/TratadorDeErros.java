package com.api.reservamed.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarError400(MethodArgumentNotValidException ex) {
        var erroOptional = ex.getFieldErrors().stream().findFirst();

        if (erroOptional.isPresent()) {
            var fieldError = erroOptional.get();
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();

            String message = String.format("O campo %s %s", fieldName, defaultMessage);
            return ResponseEntity.badRequest().body(new ErrorResponse(message));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse("Erro de validação desconhecido"));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<?> tratarError401(){
        return ResponseEntity.badRequest().body("Seu usuário não é permitido para acessar esta rota!");
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErrorResponse> handleValidacaoException(ValidacaoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private record DadosErroValidacao(String field, String message){
        public DadosErroValidacao(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
