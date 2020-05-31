package ual.dra.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestController
public class PreguntaController {

	@Autowired
	PreguntaRepository up;
	
//	
//	@GetMapping(path = "/getRankingByName")
//	public ResponseEntity<String> getByName(@RequestParam String nombre){
//		System.out.println("dentro del metodo");
//		return new ResponseEntity<String>("El ranking de " + nombre + " es: " + up.findByNombre(nombre).getRanking(), HttpStatus.ACCEPTED);
//	}	
	
}
