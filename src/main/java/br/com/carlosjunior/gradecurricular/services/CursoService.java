package br.com.carlosjunior.gradecurricular.services;

import br.com.carlosjunior.gradecurricular.dto.CursoDto;
import br.com.carlosjunior.gradecurricular.models.Response;

public interface CursoService {

	Boolean cadastrarCurso(CursoDto curso);
}
