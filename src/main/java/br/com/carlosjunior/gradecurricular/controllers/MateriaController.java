package br.com.carlosjunior.gradecurricular.controllers;

import java.util.List;

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

import br.com.carlosjunior.gradecurricular.dto.MateriaDto;
import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;
import br.com.carlosjunior.gradecurricular.repositories.MateriaRepository;
import br.com.carlosjunior.gradecurricular.services.MateriaService;

@RestController
@RequestMapping("/materia")
public class MateriaController {

	@Autowired
	private MateriaRepository repository;

	@Autowired
	private MateriaService materiaService;

	@GetMapping
	public ResponseEntity<List<MateriaEntity>> listarMaterias() {
		return ResponseEntity.status(HttpStatus.OK).body(materiaService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MateriaEntity> consultarMateria(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(materiaService.consultar(id));
	}

	@PostMapping
	public ResponseEntity<Boolean> cadastrarMateria(@RequestBody MateriaDto materia) {
		return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.cadastrar(materia));
	}

	@PutMapping()
	public ResponseEntity<Boolean> atualizarMateria(@RequestBody MateriaDto materia) {
		return ResponseEntity.status(HttpStatus.OK).body(materiaService.atualizar(materia));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(materiaService.excluir(id));
	}

}
