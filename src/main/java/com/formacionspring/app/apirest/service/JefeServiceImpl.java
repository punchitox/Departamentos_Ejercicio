package com.formacionspring.app.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionspring.app.apirest.dao.JefeDao;
import com.formacionspring.app.apirest.entity.Jefe;

@Service
public class JefeServiceImpl implements JefeService{
	
	@Autowired
	private JefeDao jefeDao;

	@Override
	@Transactional(readOnly = true)
	public List<Jefe> findAll() {
		// TODO Auto-generated method stub
		return (List<Jefe>) jefeDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Jefe findById(Long id) {
		// TODO Auto-generated method stub
		return jefeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Jefe save(Jefe jefe) {
		// TODO Auto-generated method stub
		return jefeDao.save(jefe);
	}

	@Override
	@Transactional
	public Jefe delete(Long id) {
		Jefe jefeDaoBorrado = findById(id);
		jefeDao.deleteById(id);
		return 	jefeDaoBorrado;
	}

}
