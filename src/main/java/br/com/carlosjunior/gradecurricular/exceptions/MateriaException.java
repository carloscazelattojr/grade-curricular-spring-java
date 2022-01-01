package br.com.carlosjunior.gradecurricular.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MateriaException extends RuntimeException {

	private static final long serialVersionUID = 7729355976737029964L;

	private final HttpStatus httpStatus;

	public MateriaException(final String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}

}
