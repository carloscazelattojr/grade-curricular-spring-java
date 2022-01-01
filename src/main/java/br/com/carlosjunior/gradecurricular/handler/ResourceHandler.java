package br.com.carlosjunior.gradecurricular.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.carlosjunior.gradecurricular.exceptions.MateriaException;
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

}
