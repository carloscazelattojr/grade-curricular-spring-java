package br.com.carlosjunior.gradecurricular.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.carlosjunior.gradecurricular.exceptions.MateriaException;
import br.com.carlosjunior.gradecurricular.models.ErrorMapResponse;
import br.com.carlosjunior.gradecurricular.models.ErrorMapResponse.ErrorMapResponseBuilder;
import br.com.carlosjunior.gradecurricular.models.ErrorResponse;
import br.com.carlosjunior.gradecurricular.models.ErrorResponse.ErrorResponseBuilder;

@ControllerAdvice
public class ResourceHandler {

	@ExceptionHandler(MateriaException.class)
	public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException me) {
		ErrorResponseBuilder erro = ErrorResponse.builder();

		erro.httpStatus(me.getHttpStatus().value());
		erro.mensagem(me.getMessage());
		erro.timeStamp(System.currentTimeMillis());
		return ResponseEntity.status(me.getHttpStatus()).body(erro.build());

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException m) {
		
		Map<String, String> erros = new HashMap<>();
		m.getBindingResult().getAllErrors().forEach(erro -> {
			String campo = ((FieldError)erro).getField();
			String mensagem = erro.getDefaultMessage();
			erros.put(campo, mensagem);
		});
		
		ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
		errorMap.erros(erros)
				.httpStatus(HttpStatus.BAD_REQUEST.value())
				.timeStamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.build());
		
	}

}
