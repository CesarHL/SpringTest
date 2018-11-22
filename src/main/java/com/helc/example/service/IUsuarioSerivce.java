package com.helc.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.helc.example.model.Usuario;

public interface IUsuarioSerivce {
	
	public List<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public Usuario findById(Long id);
	
	public Usuario save(Usuario usuario);
	
	public void delete(Long id);

}
