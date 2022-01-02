package br.com.carlosjunior.gradecurricular.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carlosjunior.gradecurricular.dto.CursoDto;
import br.com.carlosjunior.gradecurricular.models.Response;
import br.com.carlosjunior.gradecurricular.services.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

	@Autowired
	private CursoService cursoService;
	
	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoDto curso) {
		
		cursoService.cadastrarCurso(curso);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);

/*

Response<List<MateriaDto>> response = new Response<>();

		response.setData(materiaService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);

 * */		
		
		
	}
 

}
