package dto;

import java.util.ArrayList;
import model.Pessoa;

public class ClienteDTO extends Pessoa implements DTO{
	private float saldoDevedor;
	private int id;
	private ArrayList<Object> todosClientes = new ArrayList<>();
	private String telefone;
	private String cpf;
	private ArrayList<ArrayList<Object>> relatorio = new ArrayList<>();
	
	public ArrayList<ArrayList<Object>> getRelatorio() {
		return relatorio;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public float getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(float saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
	public void addCliente(ClienteDTO c) {
		todosClientes.add(c);
	}
	public ArrayList<Object> getTodosClientes() {
		return todosClientes;
	}
}