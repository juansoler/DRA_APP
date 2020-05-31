package ual.dra.rest;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = {
	      @UniqueConstraint(columnNames = { "numeroPregunta", "numeroTema" })})
public class Pregunta implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(unique = true)
    private Long id;
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    private Integer numeroTema;

    
    public Pregunta(Long id, Integer numeroTema, Integer numeroPregunta, @NotNull String pregunta, String respuestaA,
			String respuestaB, String respuestaC, String respuestaD, String respuestaCorrecta, Tema tema) {
		super();
		this.id = id;
		this.numeroTema = numeroTema;
		this.numeroPregunta = numeroPregunta;
		this.pregunta = pregunta;
		this.respuestaA = respuestaA;
		this.respuestaB = respuestaB;
		this.respuestaC = respuestaC;
		this.respuestaD = respuestaD;
		this.respuestaCorrecta = respuestaCorrecta;
		this.tema = tema;
	}

	public Integer getNumeroTema() {
		return numeroTema;
	}

	public void setNumeroTema(Integer numeroTema) {
		this.numeroTema = numeroTema;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	private Integer numeroPregunta;
	
    
    



	@NotNull
    private String pregunta;

    private String respuestaA;
    
    private String respuestaB;
    
    private String respuestaC;
    
    private String respuestaD;
    
    private String respuestaCorrecta;

    
//    @NotNull
//    @Email
//    @Size(max = 100)
//    @Column(unique = true)
//    private String email;

  

    // Hibernate requires a no-arg constructor
    public Pregunta() {

    }

    /* @Override
	public boolean equals(Pregunta obj) {
		// TODO Auto-generated method stub
    	if ((this.numeroTema == obj.numeroTema) {
    		
    	}
		return super.equals(obj);
	}
	
	*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tema_id")
	private Tema tema;
	
	public Integer getNumeroPregunta() {
		return numeroPregunta;
	}

	public void setNumeroPregunta(Integer numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}

	

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuestaA() {
		return respuestaA;
	}

	public void setRespuestaA(String respuestaA) {
		this.respuestaA = respuestaA;
	}

	public String getRespuestaB() {
		return respuestaB;
	}

	public void setRespuestaB(String respuestaB) {
		this.respuestaB = respuestaB;
	}

	public String getRespuestaC() {
		return respuestaC;
	}

	public void setRespuestaC(String respuestaC) {
		this.respuestaC = respuestaC;
	}

	public String getRespuestaD() {
		return respuestaD;
	}

	public void setRespuestaD(String respuestaD) {
		this.respuestaD = respuestaD;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	
	

	
	
	

	
   
	
	
	
}