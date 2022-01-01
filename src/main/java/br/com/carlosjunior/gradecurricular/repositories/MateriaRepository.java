package br.com.carlosjunior.gradecurricular.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.carlosjunior.gradecurricular.entities.MateriaEntity;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {

}
