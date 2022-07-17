package com.pauloeduardocosta.forum.rs.exception;

import com.pauloeduardocosta.forum.dto.CampoInvalidoDTO;
import com.pauloeduardocosta.forum.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.forum.service.exception.TopicoFechadoException;
import com.pauloeduardocosta.forum.service.exception.UsuarioNaoEAutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ObjetoNaoEncotradoException.class)
    public ResponseEntity<StandardError> objetoNaoEncontrado(ObjetoNaoEncotradoException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(TopicoFechadoException.class)
    public ResponseEntity<StandardError> topicoFechado(TopicoFechadoException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidacaoError> handle(MethodArgumentNotValidException exception) {
        ValidacaoError validacaoError = new ValidacaoError(HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erro -> {
            String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            CampoInvalidoDTO campoInvalidoDTO = new CampoInvalidoDTO(erro.getField(), mensagem);

            validacaoError.getCamposInvalidos().add(campoInvalidoDTO);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validacaoError);
    }

    @ExceptionHandler(UsuarioNaoEAutorException.class)
    public ResponseEntity<StandardError> usuarioIncorreto(UsuarioNaoEAutorException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
