package com.helc.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.helc.example.model.Usuario;
import com.helc.example.payload.LoginRequest;
import com.helc.example.service.IUsuarioSerivce;

@CrossOrigin(origins = { "https://shrouded-ocean-90048.herokuapp.com", "http://localhost:4200" })
@RestController
@RequestMapping("/v1/user")
public class UsuarioController {

	@Autowired
	private IUsuarioSerivce usuarioService;

	Map<String, Object> responseMap;

	@GetMapping("/")
	public ResponseEntity<?> index() {
		responseMap = new HashMap<String, Object>();
		List<Usuario> listUsuario = usuarioService.findAll();
		if (listUsuario.isEmpty()) {
			responseMap.put("mensaje", "No existen registros en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(listUsuario, HttpStatus.OK);
	}

	@GetMapping("/page/{page}")
	public ResponseEntity<?> index(@PathVariable Integer page) {
		responseMap = new HashMap<String, Object>();
		Pageable pageable = PageRequest.of(page, 10);
		Page<Usuario> listUsuario = usuarioService.findAll(pageable);
		if (listUsuario.isEmpty()) {
			responseMap.put("mensaje", "No existen registros en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Page<Usuario>>(listUsuario, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		responseMap = new HashMap<String, Object>();
		Usuario usuario = usuarioService.findById(id);
		if (usuario == null) {
			responseMap.put("mensaje", "El usuario no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario usuarioExample = null;
		responseMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<String> bindingErrorsList = new ArrayList<>();

			for (FieldError error : result.getFieldErrors()) {
				bindingErrorsList.add(error.getDefaultMessage() + " " + error.getDefaultMessage());
			}
			responseMap.put("bindingErrors", bindingErrorsList);
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			usuarioExample = usuarioService.save(usuario);
		} catch (DataAccessException e) {
			responseMap.put("mensaje", "El usuario no se pudo insertar en la base de datos");
			responseMap.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		responseMap.put("mensaje", "El usuario se ha creado con éxito");
		responseMap.put("usuario", usuarioExample);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
		Usuario usuarioExample = usuarioService.findById(id);
		responseMap = new HashMap<String, Object>();
		Usuario usuarioModificado = null;

		if (result.hasErrors()) {
			List<String> bindingErrorsList = new ArrayList<>();

			for (FieldError error : result.getFieldErrors()) {
				bindingErrorsList.add(error.getDefaultMessage() + " " + error.getDefaultMessage());
			}
			responseMap.put("bindingErrors", bindingErrorsList);
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuarioExample == null) {
			responseMap.put("mensaje", "El usuario no ha podido editar");
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
		}

		try {
			usuarioExample.setCorreo(usuario.getCorreo());
			usuarioExample.setPassword(usuario.getPassword());
			usuarioModificado = usuarioService.save(usuarioExample);
		} catch (DataAccessException e) {
			responseMap.put("mensaje", "No se ha podido actualizar el usuario en la base de datos");
			responseMap.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put("mensaje", "Se ha actualizado exitosamente");
		responseMap.put("usuario", usuarioModificado);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		responseMap = new HashMap<String, Object>();
		try {
			usuarioService.delete(id);
		} catch (DataAccessException e) {
			responseMap.put("mensaje", "No se ha podido borrar el usuario en la base de datos");
			responseMap.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		responseMap.put("mensaje", "Se ha borrado el usuario exitosamente");
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
	}

	public IUsuarioSerivce getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioSerivce usuarioService) {
		this.usuarioService = usuarioService;
	}

}
