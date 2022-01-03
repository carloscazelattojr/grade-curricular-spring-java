package br.com.carlosjunior.gradecurricular.services;

import java.util.List;

import br.com.carlosjunior.gradecurricular.dto.CursoDto;
import br.com.carlosjunior.gradecurricular.entities.CursoEntity;

public interface CursoService {

	Boolean cadastrarCurso(CursoDto cursoDto);

	Boolean atualizarCurso(CursoDto cursoDto);
	
	CursoDto consultarPorCodigo(String codigoCurso);
	
	List<CursoDto> listar();
	
	Boolean excluir(Long cursoId);
}
