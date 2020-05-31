package ual.dra.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestController 
public class TemaController {

	@Autowired
	TemaRepository up;
	
	/*
	@PostMapping(path = "/temas")
	public ResponseEntity<String>  postTema(@RequestBody Integer id, @RequestBody int numeroTema, @RequestBody String nombreTema, @RequestBody String descripcion){
		
		if (up.existsById(id)) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}else {
			up.save(new Tema(id, nombreTema, numeroTema, descripcion));
			return new ResponseEntity<String>("Creado", HttpStatus.ACCEPTED);
		}
	}
	
	*/
//	
//	@GetMapping(path = "/getRankingByName")
//	public ResponseEntity<String> getByName(@RequestParam String nombre){
//		System.out.println("dentro del metodo");
//		return new ResponseEntity<String>("El ranking de " + nombre + " es: " + up.findByNombre(nombre).getRanking(), HttpStatus.ACCEPTED);
//	}	
	
}
