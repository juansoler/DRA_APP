package ual.dra.rest;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
//@Table(name = "temas")
public class Tema implements Serializable {
	
    @Id
    @GeneratedValue
    @Column(unique = true)
    private Long id;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", orphanRemoval = true,cascade = CascadeType.REMOVE)   
    private Set<Pregunta> preguntas;

    @NotNull
    private String nombreTema;
    
    @NotNull
    @Column(unique = true)
    private Integer tema;
    
    @NotNull
    private String descripcion;






//    @NotNull
//    @Column(name = "numeroTema")
//    private int numeroTema;

  

    public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	// Hibernate requires a no-arg constructor
    public Tema() {

    }
    
	
	public Set<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Set<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public String getNombreTema() {
		return nombreTema;
	}

	public void setNombreTema(String nombreTema) {
		this.nombreTema = nombreTema;
	}


	public Integer getTema() {
		return tema;
	}


	public void setTema(Integer tema) {
		this.tema = tema;
	}


	public Tema(Long id, Set<Pregunta> preguntas, @NotNull String nombreTema, @NotNull Integer tema,
			@NotNull String descripcion) {
		super();
		this.id = id;
		this.preguntas = preguntas;
		this.nombreTema = nombreTema;
		this.tema = tema;
		this.descripcion = descripcion;
	}


	
	

	

	

	
	
	
}