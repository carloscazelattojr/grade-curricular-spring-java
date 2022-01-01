package br.com.carlosjunior.gradecurricular.services;

import java.util.List;

import br.com.carlosjunior.gradecurricular.dto.MateriaDto;
import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;

public interface MateriaService {

	public Boolean atualizar(final MateriaDto materia);

	public Boolean excluir(final Long id);

	public List<MateriaEntity> listar();

	public MateriaEntity consultar(final Long id);

	public Boolean cadastrar(final MateriaDto materia);

}
