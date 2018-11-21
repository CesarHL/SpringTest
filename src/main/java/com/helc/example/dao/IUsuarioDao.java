package com.helc.example.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helc.example.model.Usuario;

@Repository
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

}
