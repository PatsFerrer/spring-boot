function salvarUsuario() {
	let id = $("#id").val();
	let nome = $("#nome").val();
	let idade = $("#idade").val();

	if (nome == null || nome != null && nome.trim() == '') {
		$("#nome").focus();
		alert('Informe o nome');
		return;
	}

	if (idade == null || idade != null && idade.trim() == '') {
		$("#idade").focus();
		alert('Informe a idade');
		return;
	}

	//ajax
	$.ajax({
		method: "POST",
		url: "salvar", //chama a url mapeada do PostMapping, no método 'salvar' do controller
		data: JSON.stringify({ id: id, nome: nome, idade: idade }), //'id' do atributo da classe Usuario : o value do 'id' (linha 45) do input do form
		contentType: "application/json; charset=utf-8",
		success: function(response) {

			$("#id").val(response.id);
			alert("Gravou com sucesso!")
		}
	}).fail(function(xhr, status, errorThrown) {
		alert("Erro ao salvar usuário: " + xhr.responseText)
	});
}

function pesquisarUser() {
	let nome = $('#nameBusca').val(); //pega o input de buscar

	if (nome != null && nome.trim() != '') { //se o nome for diferente de nulo e de espaços vazios

		$.ajax({
			method: "GET",
			url: "buscarPorNome", //chama a url mapeada do GetMapping, no método 'buscarPorNome' do controller
			data: "name=" + nome, // RequestParam do objeto no metodo buscarPorNome do controller + input da linha 112
			success: function(response) { // response retorna a Lista do nosso método
				$('#tabelaResultados > tbody > tr').remove;

				for (let i = 0; i < response.length; i++) {
					$('#tabelaResultados > tbody').append('<tr id="' + response[i].id + '"><td> ' + response[i].id + ' </td> <td> ' + response[i].nome + ' </td> <td><button type="button" onclick="colocarEmEdicao( ' + response[i].id + ' )" class="btn btn-primary">Ver</button></td> <td><button type="button" class="btn btn-danger" onclick="deleteUser( ' + response[i].id + ' )">Delete</button></td> </tr>');
				}
			}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao buscar usuário: " + xhr.responseText)
		});

	}
}

function colocarEmEdicao(id) {

	$.ajax({
		method: "GET",
		url: "buscarUserId", //chama a url mapeada do GetMapping, no método 'buscarPorId' do controller
		data: "idUser=" + id, // RequestParam do metodo buscarUserId do controller + id que recebe por parâmetro
		success: function(response) {

			$("#id").val(response.id); //mostra no input os campos preenchidos
			$("#nome").val(response.nome);
			$("#idade").val(response.idade);

			$('#modalPesquisarUser').modal('hide'); // fecha o modal ao clicar em 'ver'

		}
	}).fail(function(xhr, status, errorThrown) {
		alert("Erro ao buscar usuário por id: " + xhr.responseText)
	});

}

function deleteUser(id) {

	if (confirm('Deseja realmente deletar?')) {
		$.ajax({
			method: "DELETE",
			url: "delete", //chama a url mapeada do DeleteMapping, no método 'delete' do controller
			data: "id=" + id, // RequestParam do metodo buscarUserId do controller + id que recebe por parâmetro
			success: function(response) {

				$('#' + id).remove(); // # é o id da linha 124 (id="'+response[i].id+'") + id do usuario que é o mesmo

				alert(response);

			}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao deletar usuário por id: " + xhr.responseText)
		});
	}

}


function deletarDaTela() {

	let id = $('#id').val();

	if (id != null && id.trim() != '') {

		deleteUser(id);
		document.getElementById('formCadastroUser').reset();
	}

}