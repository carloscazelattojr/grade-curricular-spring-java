package br.com.carlosjunior.gradecurricular.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.carlosjunior.gradecurricular.constants.MensagensErros;
import br.com.carlosjunior.gradecurricular.dto.CursoDto;
import br.com.carlosjunior.gradecurricular.dto.MateriaDto;
import br.com.carlosjunior.gradecurricular.entities.CursoEntity;
import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;
import br.com.carlosjunior.gradecurricular.exceptions.CursoException;
import br.com.carlosjunior.gradecurricular.exceptions.MateriaException;
import br.com.carlosjunior.gradecurricular.repositories.CursoRepository;
import br.com.carlosjunior.gradecurricular.repositories.MateriaRepository;

@CacheConfig(cacheNames = { "curso" })
@Service
public class CursoServiceImpl implements CursoService {

	private CursoRepository cursoRepository;
	private MateriaRepository materiaRepository;

	private ModelMapper mapper;

	public CursoServiceImpl(CursoRepository cursoRepository, MateriaRepository materiaRepository) {
		this.mapper = new ModelMapper();
		this.cursoRepository = cursoRepository;
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean cadastrarCurso(CursoDto cursoDto) {

		try {
			// No cadastro, se passar ID deverá estourar mensagem de erro
			if (cursoDto.getId() != null) {
				throw new CursoException(MensagensErros.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
			}

			// Não permitir cadastrar curso com mesmo CODIGO. Cada curso deverá conter um
			// código único.
			if (cursoRepository.findCursoByCodigo(cursoDto.getCodigo()) != null) {
				throw new CursoException(MensagensErros.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(),
						HttpStatus.BAD_REQUEST);
			}

			return cadastrarOuAtualizar(cursoDto);
		}catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw new CursoException(MensagensErros.ERRO_INTERNO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean atualizarCurso(CursoDto cursoDto) {

		try {
			// Para atualizar, eu preciso buscar o já cadastrado.
			consultarPorCodigo(cursoDto.getCodigo());
			return cadastrarOuAtualizar(cursoDto);
		} catch (CursoException c) {
			throw c;			
		} catch (Exception e) {
			throw new CursoException(MensagensErros.ERRO_INTERNO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(key = "#codCurso")
	@Override
	public CursoDto consultarPorCodigo(String codigoCurso) {
		try {
			CursoEntity curso = cursoRepository.findCursoByCodigo(codigoCurso);
			if (curso == null) {
				throw new CursoException(MensagensErros.ERRO_INTERNO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return mapper.map(curso, CursoDto.class);
		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw new CursoException(MensagensErros.ERRO_INTERNO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size()<3")
	@Override
	public List<CursoDto> listar() {
		return mapper.map(cursoRepository.findAll(), new TypeToken<List<MateriaDto>>() {}.getType());
	}

	@Override
	public Boolean excluir(Long cursoId) {

		try {
			// Verificar se existe o curso com ID informado
			if (cursoRepository.findById(cursoId).isPresent()) {
				cursoRepository.deleteById(cursoId);
				return Boolean.TRUE;
			}
			throw new CursoException(MensagensErros.ERRO_CURSO_NAO_ENCONTRADO.getValor(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw new CursoException(MensagensErros.ERRO_INTERNO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Boolean cadastrarOuAtualizar(CursoDto cursoDto) {

		// Um curso tem várias matérias. Array para guardar as materias
		List<MateriaEntity> materias = new ArrayList<>();

		// verificar se no cursoDTO foi informado ids das Matérias
		if (!cursoDto.getMaterias().isEmpty()) {
			cursoDto.getMaterias().forEach(materiaId -> {
				// verificar se o ID da matéria inforamda existe? Se existir adicionar na lista
				// de Materias.
				MateriaEntity materia = materiaRepository.findById(materiaId).get();
				if (materia.getId() != null) {
					materias.add(materia);
				} else {
					throw new MateriaException("Matéria_Id: " + materia.getId() + " inválido ou não cadastrado.",
							HttpStatus.NOT_FOUND);
				}
			});
		}

		// Incluir ou Atualizar curso
		CursoEntity curso = new CursoEntity();

		curso.setNome(cursoDto.getNome());
		curso.setCodigo(cursoDto.getCodigo());
		curso.setMaterias(materias);

		// Atualizar ?
		if (cursoDto.getId() != null) {
			curso.setId(cursoDto.getId());
		}
		// ------

		cursoRepository.save(curso);

		return Boolean.TRUE;
	}
	
	
}
