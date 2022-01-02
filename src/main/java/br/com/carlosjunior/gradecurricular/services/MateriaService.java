package br.com.carlosjunior.gradecurricular.services;

import java.util.List;

import br.com.carlosjunior.gradecurricular.dto.MateriaDto;

public interface MateriaService {

	public Boolean atualizar(final MateriaDto materia);

	public Boolean excluir(final Long id);

	public List<MateriaDto> listar();

	public MateriaDto consultar(final Long id);

	public Boolean cadastrar(final MateriaDto materia);

	public List<MateriaDto> findByHoraMinima(int horaMinima);
	
	public List<MateriaDto> findByFrequencia(int frequencia);

}
