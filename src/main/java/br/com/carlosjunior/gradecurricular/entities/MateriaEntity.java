package br.com.carlosjunior.gradecurricular.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_materia")
@Data
@NoArgsConstructor // Construir construtores vazios.
public class MateriaEntity implements Serializable {

	private static final long serialVersionUID = 7462893273341444074L;

	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@JsonInclude(Include.NON_EMPTY)
	private String nome;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name = "cod")
	private String codigo;

	@Column(name = "hrs")
	private int horas;

	@Column(name = "freq")
	private int frequencia;

}
