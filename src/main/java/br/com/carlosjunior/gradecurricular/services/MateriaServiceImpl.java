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

	@Autowired
	private MateriaRepository materiaRepository;

	@Override
	public Boolean atualizar(MateriaDto materia) {
		try {

			consultar(materia.getId());
			Optional<MateriaEntity> materiaOptional = materiaRepository.findById(materia.getId());

			if (materiaOptional.isPresent()) {
				ModelMapper mapper = new ModelMapper();
				MateriaEntity materiaAtualizada = mapper.map(materia, MateriaEntity.class);
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
			ModelMapper mapper =  new ModelMapper();
			return mapper.map(materiaRepository.findAll(),new TypeToken<List<MateriaDto>>() {}.getType()); 
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public MateriaDto consultar(Long id) {
		try {
			Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);
			if (materiaOptional.isPresent()) {
				ModelMapper mapper = new ModelMapper();
				return mapper.map(materiaOptional, MateriaDto.class);
			}
			throw new MateriaException("Matéria não cadastrada", HttpStatus.NOT_FOUND);
		} catch (MateriaException m) {
			throw m;
		} catch (Exception e) {
			throw new MateriaException("Erro interno. Contate o administrador do sistema!",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean cadastrar(MateriaDto materia) {
		try {

			ModelMapper mapper = new ModelMapper();
			MateriaEntity materiaEntity = mapper.map(materia, MateriaEntity.class);
			materiaRepository.save(materiaEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
