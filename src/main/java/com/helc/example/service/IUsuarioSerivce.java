package com.helc.example.service;

import java.util.List;

import com.helc.example.model.Usuario;

public interface IUsuarioSerivce {
	
	public List<Usuario> findAll();
	
	public Usuario save(Usuario usuario);

}
