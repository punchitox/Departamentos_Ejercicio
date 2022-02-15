package com.formacionspring.app.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionspring.app.apirest.dao.DepartamentoDao;
import com.formacionspring.app.apirest.entity.Departamento;

@Service
public class DepartamentoServiceImpl implements DepartamentoService{
	
	@Autowired
	private DepartamentoDao departamentoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> findAll() {
		// TODO Auto-generated method stub
		return (List<Departamento>) departamentoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Departamento findById(Long id) {
		// TODO Auto-generated method stub
		return departamentoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Departamento save(Departamento departamento) {
		// TODO Auto-generated method stub
		return departamentoDao.save(departamento);
	}

	@Override
	@Transactional
	public Departamento delete(Long id) {
		Departamento departamentoDaoBorrado = findById(id);
		departamentoDao.deleteById(id);
		return 	departamentoDaoBorrado;
	}


}
