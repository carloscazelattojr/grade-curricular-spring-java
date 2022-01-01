package br.com.carlosjunior.gradecurricular.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.carlosjunior.gradecurricular.dto.MateriaDto;
import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;
import br.com.carlosjunior.gradecurricular.exceptions.MateriaException;
import br.com.carlosjunior.gradecurricular.repositories.MateriaRepository;

@Service
public class MateriaServiceImpl implements MateriaService {

	private static final String MENSAGEM_ERRO = "Erro interno. Contate o administrador do sistema!";
	private static final String MATERIA_NAO_ENCONTRADA = "Matéria não cadastrada";

	private MateriaRepository materiaRepository;
	private ModelMapper mapper;

	public MateriaServiceImpl(MateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean atualizar(MateriaDto materia) {
		try {

			consultar(materia.getId());
			Optional<MateriaEntity> materiaOptional = materiaRepository.findById(materia.getId());

			if (materiaOptional.isPresent()) {
				MateriaEntity materiaAtualizada = this.mapper.map(materia, MateriaEntity.class);
				materiaRepository.save(materiaAtualizada);
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public Boolean excluir(Long id) {
		try {
			consultar(id);
			MateriaEntity materia = materiaRepository.findById(id).get();
			materiaRepository.delete(materia);
			return Boolean.TRUE;
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<MateriaDto> listar() {
		try {
			return this.mapper.map(materiaRepository.findAll(), new TypeToken<List<MateriaDto>>() {
			}.getType());
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public MateriaDto consultar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);
			if (materiaOptional.isPresent()) {
				return this.mapper.map(materiaOptional, MateriaDto.class);
			}
			throw new MateriaException(MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean cadastrar(MateriaDto materia) {
		try {
			MateriaEntity materiaEntity = this.mapper.map(materia, MateriaEntity.class);
			materiaRepository.save(materiaEntity);
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}