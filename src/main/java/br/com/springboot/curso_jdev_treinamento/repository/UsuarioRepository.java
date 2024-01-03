package br.com.springboot.curso_jdev_treinamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	// Buscar por nome
	@Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%") // aponta pra classe, no caso 'from Usuario'; upper() traz maiusculo, coloque no controller, no método buscarPorNome, o toUpperCase() tb; trim() retira os espaços; %?1% é 1 pq só tem 1 parâmetro (que é o name), se tivesse 2 parametros colocaria mais de 1 entre as %%
	List<Usuario> buscarPorNome(String name); //por ser interface, não fazemos nada dentro de {}
}
