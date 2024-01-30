package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

@RestController
public class GreetingsController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Curso Spring " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		// Teste de controller OK
		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);

		return "Olá mundo " + nome;
	}

	@GetMapping(value = "listatodos") // primeiro método da API
	@ResponseBody // Retorna os dados para o corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll(); // executa a consulta no banco

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // Retorna a lista de JSON
	}

	@PostMapping(value = "salvar") // Mapeia a URL
	@ResponseBody // descrição da resposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // Recebe os dados para salvar

		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { // a <?> pode retornar qualquer coisa, uma string ou um objeto
		
		//validação pra verificar se está passando o id para atualizar
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam Long id) {

		usuarioRepository.deleteById(id);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}

	// método de pesquisa
	@GetMapping(value = "buscarUserId")
	@ResponseBody
	public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "idUser") Long idUser) {

		Usuario usuario = usuarioRepository.findById(idUser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

	}
	
	// Buscar por nome
	@GetMapping(value = "buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { //recebe os dados para consultar

		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase()); // trim() tira o espaço

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);

	}
}
