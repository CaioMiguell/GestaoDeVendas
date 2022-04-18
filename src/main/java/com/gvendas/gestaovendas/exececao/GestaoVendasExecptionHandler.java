package com.gvendas.gestaovendas.exececao;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GestaoVendasExecptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
    private static final String CONSTANT_VALIDATION_LENGTH = "Length";


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Erro> erros = gerarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> gerarListaDeErros(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<Erro>();
        bindingResult.getFieldErrors().forEach(fildError -> {
            String msgUsuario = tratarMensagemDeErroParaUsuario(fildError);
            String msgDesenvolvedor = fildError.toString();
            erros.add(new Erro(msgUsuario, msgDesenvolvedor));
        });
        return erros;

    }

    private String tratarMensagemDeErroParaUsuario(FieldError fildError) {
        if (fildError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
            return fildError.getDefaultMessage().concat(" e obrigatorio ");
        }
        if (fildError.getCode().equals(CONSTANT_VALIDATION_LENGTH)) {
            return fildError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracterres. "
                    , fildError.getArguments()[2], fildError.getArguments()[1]));
        }
        return fildError.toString();
    }
}
