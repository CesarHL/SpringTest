package com.helc.example.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helc.example.model.Usuario;
import com.helc.example.service.IUsuarioSerivce;

@RestController
@RequestMapping("/user/v1")
public class UsuarioRestController {

	@Autowired
	private IUsuarioSerivce usuarioService;

	@GetMapping("/users")
	public List<Usuario> getAllUsers() {
		return usuarioService.findAll();
	}

	@PostMapping("/users")
	public Usuario insertUser(@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
	}

	public IUsuarioSerivce getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioSerivce usuarioService) {
		this.usuarioService = usuarioService;
	}

}
