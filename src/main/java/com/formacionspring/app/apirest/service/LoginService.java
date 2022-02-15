package com.formacionspring.app.apirest.service;

import java.util.List;

import com.formacionspring.app.apirest.entity.Login;

public interface LoginService {
	public List<Login> findAll();
	public Login findById(Long id);
	public Login save(Login login);
	public Login delete(Long id);
}
