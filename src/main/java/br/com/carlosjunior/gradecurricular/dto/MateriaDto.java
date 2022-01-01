package br.com.carlosjunior.gradecurricular.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MateriaDto {

	private Long id;

	@NotBlank(message = "Informe o nome da matéria")
	private String nome;

	@NotBlank(message = "Informe o código da matéria")
	private String codigo;

	@Min(value = 34, message = "Permitido o mínimo de 34hs por matéria")
	@Max(value = 200, message = "Permitido o máximo de 120hs por matéria")
	private int horas;

	@Min(value = 1, message = "Permitido o mínimo de 1 vez por ano")
	@Max(value = 10, message = "Permitido o máximo de 1 vez por ano")
	private int frequencia;

}