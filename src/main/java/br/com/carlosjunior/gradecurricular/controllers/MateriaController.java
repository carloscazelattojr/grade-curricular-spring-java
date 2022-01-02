package br.com.carlosjunior.gradecurricular.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import br.com.carlosjunior.gradecurricular.constants.HyperLinkConstant;
import br.com.carlosjunior.gradecurricular.dto.MateriaDto;
import br.com.carlosjunior.gradecurricular.models.Response;
import br.com.carlosjunior.gradecurricular.services.MateriaService;

@RestController
@RequestMapping("/materia")
public class MateriaController {

	@Autowired
	private MateriaService materiaService;

	@GetMapping
	public ResponseEntity<Response<List<MateriaDto>>> listarMaterias() {
		Response<List<MateriaDto>> response = new Response<>();

		response.setData(materiaService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<MateriaDto>> consultarMateria(@PathVariable Long id) {
		Response<MateriaDto> response = new Response<>();

		MateriaDto materia = materiaService.consultar(id);

		response.setData(materia);
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultarMateria(id))
				.withSelfRel());

		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
				.withRel(HyperLinkConstant.EXCLUIR.getValor()));
		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia)).withRel(HyperLinkConstant.ATUALIZAR.getValor()));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaDto materia) {
		Response<Boolean> response = new Response<>();

		response.setData(materiaService.cadastrar(materia));
		response.setStatusCode(HttpStatus.CREATED.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withRel(HyperLinkConstant.LISTAR.getValor()));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping()
	public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaDto materia) {
		Response<Boolean> response = new Response<>();

		response.setData(materiaService.atualizar(materia));
		response.setStatusCode(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia)).withSelfRel());
		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(materia.getId()))
				.withRel(HyperLinkConstant.EXCLUIR.getValor()));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withRel(HyperLinkConstant.LISTAR.getValor()));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();

		response.setData(materiaService.excluir(id));
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
				.withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
				.withRel(HyperLinkConstant.LISTAR.getValor()));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/frequencia/{frequencia}")
	public ResponseEntity<Response<List<MateriaDto>>> listarMateriasPorFrequencia(@PathVariable int frequencia) {

		Response<List<MateriaDto>> response = new Response<>();
		List<MateriaDto> materias = materiaService.findByFrequencia(frequencia);
		response.setData(materias);
		response.setStatusCode(HttpStatus.OK.value());
		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMateriasPorFrequencia(frequencia))
				.withSelfRel());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
