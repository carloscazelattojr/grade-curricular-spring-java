package br.com.carlosjunior.gradecurricular.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.carlosjunior.gradecurricular.constants.MensagensErros;
import br.com.carlosjunior.gradecurricular.dto.CursoDto;
import br.com.carlosjunior.gradecurricular.entities.CursoEntity;
import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;
import br.com.carlosjunior.gradecurricular.exceptions.CursoException;
import br.com.carlosjunior.gradecurricular.exceptions.MateriaException;
import br.com.carlosjunior.gradecurricular.repositories.CursoRepository;
import br.com.carlosjunior.gradecurricular.repositories.MateriaRepository;

@Service
public class CursoServiceImpl implements CursoService {

	private static final String MENSAGEM_ERRO = "Erro interno. Contate o administrador do sistema!";

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
			
			if (cursoDto.getId() != null ) {
				throw new CursoException(MensagensErros.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
			}
			
			cadastrarOuAtualizar(cursoDto);
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new CursoException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	private Boolean cadastrarOuAtualizar(CursoDto cursoDto) {

		// Um curso tem várias matérias. Array para guardar as materias 
		List<MateriaEntity> materias = new ArrayList<>();

		// verificar se no cursoDTO foi informado ids das Matérias
		if (!cursoDto.getMaterias().isEmpty()) {
			cursoDto.getMaterias().forEach(materiaId -> {
				// verificar se o ID da matéria inforamda existe? Se existir adicionar na lista de Materias.
				MateriaEntity materia = materiaRepository.findById(materiaId).get();
				if (materia.getId() != null) {
					materias.add(materia);
				} else {
					throw new MateriaException("Matéria_Id: " + materia.getId() + " inválido ou não cadastrado.",
							HttpStatus.NOT_FOUND);
				}
			});
		}
		
		//Incluir ou Atualizar curso
		CursoEntity curso = new CursoEntity();
		
		curso.setNome(cursoDto.getNome());
		curso.setCodigo(cursoDto.getCodigo());
		curso.setMaterias(materias);

		//Atualizar ?
		if (cursoDto.getId() != null) {
			curso.setId(cursoDto.getId());
		}
		// ------
		
		cursoRepository.save(curso);

		return Boolean.TRUE;
	}

}
