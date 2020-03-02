package controller;

import dto.FornecedorDTO;
import model.Fornecedor;
import model.FornecedorJaCadastradoException;

public class ControllerFornecedor {
	public void cadastrar(FornecedorDTO f) throws FornecedorJaCadastradoException{
		Fornecedor forn = new Fornecedor();
		forn.cadastrar(f);
	}
	public void editar(FornecedorDTO f) {
		Fornecedor forn = new Fornecedor();
		forn.editar(f);
	}
	public void deletar(FornecedorDTO f) {
		Fornecedor forn = new Fornecedor();
		forn.deletar(f);
	}
	public FornecedorDTO listar() {
		Fornecedor forn = new Fornecedor();
		return forn.listaDeFornecedores();
	}
	public FornecedorDTO buscar(String cnpj) {
		Fornecedor forn = new Fornecedor();
		return forn.pesquisarFornecedor(cnpj);
	}
}