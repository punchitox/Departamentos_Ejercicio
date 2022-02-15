package com.formacionspring.app.apirest.controllers;

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

import com.formacionspring.app.apirest.entity.Departamento;
import com.formacionspring.app.apirest.service.DepartamentoService;

@RestController
@RequestMapping("/api")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoService servicio;
	
	@GetMapping("/departamentos")
	public List<Departamento> getDepartamentos(){
		return servicio.findAll();
	}
	
	@GetMapping("/departamentos/{id}")
	public ResponseEntity<?> getDepartamento(@PathVariable Long id){
		Departamento departamento = null;
		Map<String,Object> response = new HashMap<>();
		
		try{
			
			departamento = servicio.findById(id);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar consulta a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(departamento == null) {
			response.put("mensaje", "El departamento ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Departamento>(departamento,HttpStatus.OK);
	}
	
	@PostMapping("/departamentos")
	public ResponseEntity<?> saveDepartamento( @RequestBody Departamento departamento){
		
		Departamento departamentoeNew = null;		
		Map<String,Object> response = new HashMap<>();
		
		try{
			
			departamentoeNew = servicio.save(departamento);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar insert a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El departamento ha sido creado con exito!");
		response.put("departamento", departamentoeNew);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/departamentos/{id}")
	public ResponseEntity<?> updateDepartamento(@RequestBody Departamento departamento,@PathVariable Long id) {
		
		Departamento departamentoUpdate = servicio.findById(id);
			
		Map<String,Object> response = new HashMap<>();
		
		try{
			
			departamentoUpdate.setNombre(departamento.getNombre());
			departamentoUpdate.setUbicacion(departamento.getUbicacion());
			
			servicio.save(departamentoUpdate);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar actualizar la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El departamento ha sido actualizado con exito!");
		response.put("departamento", departamentoUpdate);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/departamentos/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {

		Departamento departamentoBorrado = servicio.findById(id);
		
		Map<String,Object> response = new HashMap<>();
		
		if(departamentoBorrado == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try{
						
			servicio.delete(id);
			
		} catch(DataAccessException e){
			response.put("mensaje", "Error al realizar borrar el departamento");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		response.put("mensaje", "El departamento ha sido borrado con exito!");
		response.put("departamento", departamentoBorrado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

	}
	
	
}
