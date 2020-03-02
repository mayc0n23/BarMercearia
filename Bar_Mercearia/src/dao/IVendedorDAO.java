package dao;

import dto.VendedorDTO;

public interface IVendedorDAO {
	public VendedorDTO recuperarVendedor(String nome, String senha);
}