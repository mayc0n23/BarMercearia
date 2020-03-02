package controller;

import dto.ClienteDTO;
import model.Cliente;
import model.ClienteJaCadastradoException;

public class ControllerCliente {
	public void cadastrar(ClienteDTO c) throws ClienteJaCadastradoException{
		Cliente cliente = new Cliente();
		cliente.cadastrar(c);
	}
	public void editar(ClienteDTO c) {
		Cliente cliente = new Cliente();
		cliente.editar(c);
	}
	public void deletar(ClienteDTO c) {
		Cliente cliente = new Cliente();
		cliente.deletar(c);
	}
	public ClienteDTO listar() {
		Cliente cliente = new Cliente();
		return cliente.listar();
	}
	public ClienteDTO buscar(int id) {
		Cliente cliente = new Cliente();
		return cliente.procurarCliente(id);
	}
	public int pegarId(String nome, String rua) {
		Cliente cliente = new Cliente();
		return cliente.recoverId(nome, rua);
	}
	public int pegarId(String cpf) {
		Cliente cliente = new Cliente();
		return cliente.recoverId(cpf);
	}
	public ClienteDTO relatorioCliente() {
		Cliente cliente = new Cliente();
		return cliente.relatorio();
	}
	public void atualizarSaldo(int id, float valor) {
		Cliente cliente = new Cliente();
		cliente.attSaldo(id, valor);
	}
	public String recuperarSenha() {
		Cliente cliente = new Cliente();
		return cliente.recSenha();
	}
}