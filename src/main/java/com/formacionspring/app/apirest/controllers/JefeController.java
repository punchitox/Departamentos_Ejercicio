package com.formacionspring.app.apirest.controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionspring.app.apirest.entity.Empleado;
import com.formacionspring.app.apirest.entity.Jefe;
import com.formacionspring.app.apirest.service.JefeService;

@RestController
@RequestMapping("/api")
public class JefeController {

	@Autowired
	private JefeService servicio;
	
	@GetMapping("/jefes")
	public List<Jefe> getJefes(){
		return servicio.findAll();
	}
	
	@GetMapping("/jefes/{id}")
	public ResponseEntity<?> getJefe(@PathVariable Long id){
		Jefe jefe = null;
		Map<String,Object> response = new HashMap<>();
		
		try{
			
			jefe = servicio.findById(id);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar consulta a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(jefe == null) {
			response.put("mensaje", "El jefe ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Jefe>(jefe,HttpStatus.OK);
	}
	
	@PostMapping("/jefes")
	public ResponseEntity<?> saveJefe( @RequestBody Jefe jefe){
		
		Jefe jefeNew = null;		
		Map<String,Object> response = new HashMap<>();
		
		try{
			
			jefeNew = servicio.save(jefe);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar insert a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con exito!");
		response.put("cliente", jefeNew);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/jefes/{id}")
	public ResponseEntity<?> updateJefe(@RequestBody Jefe jefe,@PathVariable Long id) {
		
		Jefe jefeUpdate = servicio.findById(id);
			
		Map<String,Object> response = new HashMap<>();
		
		try{
			
			jefeUpdate.setNombre(jefe.getNombre());
			jefeUpdate.setDni(jefe.getDni());
			jefeUpdate.setSalario(jefe.getSalario());
			jefeUpdate.setTelefono(jefe.getTelefono());
			jefeUpdate.setIdDep(jefe.getIdDep());
			
			servicio.save(jefeUpdate);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar actualizar la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El jefe ha sido actualizado con exito!");
		response.put("jefe", jefeUpdate);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/jefes/{id}")
	public ResponseEntity<?> deleteJefe(@PathVariable Long id) {

		Jefe jefeBorrado = servicio.findById(id);
		
		Map<String,Object> response = new HashMap<>();
		
		if(jefeBorrado == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try{
						
			servicio.delete(id);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar borrar el jefe");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		response.put("mensaje", "El cliente ha sido borrado con exito!");
		response.put("jefe", jefeBorrado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

	}
	
}
