package br.com.carlosjunior.gradecurricular.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {

	@Query("select m from MateriaEntity m where m.horas >= :horaMinima")
	public List<MateriaEntity> findByHoraMinima(@Param("horaMinima") int horaMinima);

	@Query("select m from MateriaEntity m where m.frequencia = :frequencia")
	public List<MateriaEntity> findByFrequencia(@Param("frequencia") int frequencia);

}
