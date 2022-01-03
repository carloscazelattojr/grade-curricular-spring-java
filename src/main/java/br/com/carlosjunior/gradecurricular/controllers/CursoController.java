package br.com.carlosjunior.gradecurricular.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	// Cadastrar
	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoDto cursoDto) {

		Response<Boolean> response = new Response<>();

		response.setData(cursoService.cadastrarCurso(cursoDto));
		response.setStatusCode(HttpStatus.CREATED.value());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Atualizar
	@PutMapping
	public ResponseEntity<Response<Boolean>> atualizarCurso(@Valid @RequestBody CursoDto cursoDto) {

		Response<Boolean> response = new Response<>();

		response.setData(cursoService.atualizarCurso(cursoDto));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Listar
	@GetMapping
	public ResponseEntity<Response<List<CursoDto>>> listarCursos() {

		Response<List<CursoDto>> response = new Response<>();

		response.setData(cursoService.listar());
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Consultar
	@GetMapping("/{codigoCurso}")
	public ResponseEntity<Response<CursoDto>> consultarCurso(@PathVariable String codigoCurso){

		Response<CursoDto> response = new Response<>();

		response.setData(cursoService.consultarPorCodigo(codigoCurso));
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	// excluir
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirCurso(@PathVariable Long id){
		Response<Boolean> response = new Response<>();
		response.setData(cursoService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
	

}
