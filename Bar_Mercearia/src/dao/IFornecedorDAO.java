package dao;

import dto.FornecedorDTO;
import model.FornecedorJaCadastradoException;

public interface IFornecedorDAO {
	public void cadastrarFornecedor(FornecedorDTO f)throws FornecedorJaCadastradoException;
	public void deletarFornecedor(FornecedorDTO f);
	public void editarFornecedor(FornecedorDTO f);
	public FornecedorDTO listarFornecedores();
	public FornecedorDTO pesquisarFornecedor(String cnpj);
}	