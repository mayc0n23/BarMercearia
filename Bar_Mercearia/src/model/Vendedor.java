package model;

import dao.IVendedorDAO;
import dao.VendedorDAO;
import dto.VendedorDTO;

public class Vendedor {
	private String nome;
	private String senha;
	private IVendedorDAO dao = new VendedorDAO();
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public VendedorDTO recuperar(String nome, String senha) {
		return dao.recuperarVendedor(nome, senha);
	}
}