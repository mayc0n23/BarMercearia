package model;

import dao.ClienteDAO;
import dao.IClienteDAO;
import dto.ClienteDTO;

public class Cliente extends Pessoa{
	private int id;
	private float saldoDevedor;
	private IClienteDAO dao = new ClienteDAO();
	
	public float getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(float saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
	
	public void cadastrar(ClienteDTO cliente)throws ClienteJaCadastradoException {
		dao.cadastrarCliente(cliente);
	}
	public void deletar(ClienteDTO cliente) {
		dao.deletarCliente(cliente);
	}
	public void editar(ClienteDTO cliente) {
		dao.editarCliente(cliente);
	}
	public ClienteDTO listar() {
		return dao.listarClientes();
	}
	public ClienteDTO procurarCliente(int id) {
		return dao.pesquisarCliente(id);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int recoverId(String nome, String rua) {
		return dao.recuperarId(nome, rua);
	}
	public int recoverId(String cpf) {
		return dao.recuperarId(cpf);
	}
	public ClienteDTO relatorio() {
		return dao.clientesNegativados();
	}
	public void attSaldo(int id, float valor) {
		dao.atualizarSaldo(id, valor);
	}
	public String recSenha() {
		return dao.recupSenha();
	}
}