package com.formacionspring.app.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionspring.app.apirest.dao.LoginDao;
import com.formacionspring.app.apirest.entity.Login;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginDao loginDao;

	@Override
	@Transactional(readOnly = true)
	public List<Login> findAll() {
		// TODO Auto-generated method stub
		return (List<Login>)loginDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Login findById(Long id) {
		// TODO Auto-generated method stub
		return loginDao.findById(id).orElse(null);
	}

	@Transactional
	public Login save(Login login) {
		// TODO Auto-generated method stub
		return loginDao.save(login);
	}

	@Transactional
	public Login delete(Long id) {
		// TODO Auto-generated method stub
		Login loginDaoBorrado = findById(id);
		loginDao.deleteById(id);
		return 	loginDaoBorrado;
	}

}
