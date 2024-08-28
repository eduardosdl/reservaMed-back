package com.api.reservamed.infra.exception;

import com.api.reservamed.infra.ValidacaoException;
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
    public ResponseEntity tratarError400(MethodArgumentNotValidException ex){
        var erroOptional = ex.getFieldErrors().stream().findFirst();

        if (erroOptional.isPresent()) {
            var dadosErro = new DadosErroValidacao(erroOptional.get());
            return ResponseEntity.badRequest().body(dadosErro);
        }

        return ResponseEntity.badRequest().body(new DadosErroValidacao("desconhecido", "Erro de validação"));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity tratarError401(){
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
