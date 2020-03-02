package model;

public class FornecedorJaCadastradoException extends Exception{
	public String getMessage() {
		return "Fornecedor já cadastrado";
	}
}