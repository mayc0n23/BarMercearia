package controller;

import dto.VendedorDTO;
import model.Vendedor;

public class ControllerVendedor {
	public VendedorDTO buscarVendedor(String nome, String senha) {
		Vendedor v = new Vendedor();
		return v.recuperar(nome, senha);
	}
}