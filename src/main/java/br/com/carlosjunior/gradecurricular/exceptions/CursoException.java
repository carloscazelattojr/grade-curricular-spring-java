package br.com.carlosjunior.gradecurricular.exceptions;

import org.springframework.http.HttpStatus;

public class CursoException extends RuntimeException {

	private static final long serialVersionUID = 466379351448814762L;
	private final HttpStatus httpStatus;

	public CursoException(final String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
	
}
