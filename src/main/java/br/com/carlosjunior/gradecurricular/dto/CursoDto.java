package br.com.carlosjunior.gradecurricular.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDto {

	private Long id;

	@NotBlank(message = "Informe o nome do curso")
	@Size(min = 3, max = 30)
	private String nome;

	@NotBlank(message = "Informe o código do curso")
	private String codigo;

	private List<Long> materias;

}
