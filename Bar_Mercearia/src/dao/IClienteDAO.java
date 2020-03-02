package dao;

import dto.ClienteDTO;
import model.ClienteJaCadastradoException;

public interface IClienteDAO {
	public void cadastrarCliente(ClienteDTO cliente)throws ClienteJaCadastradoException;
	public void deletarCliente(ClienteDTO cliente);
	public void editarCliente(ClienteDTO cliente);
	public ClienteDTO listarClientes();
	public ClienteDTO pesquisarCliente(int id);
	public int recuperarId(String nome, String rua);
	public int recuperarId(String cpf);
	public ClienteDTO clientesNegativados();
	public void atualizarSaldo(int id, float valor);
	public String recupSenha();
}