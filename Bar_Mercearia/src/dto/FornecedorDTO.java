package dto;

import java.util.ArrayList;
import model.Pessoa;

public class FornecedorDTO extends Pessoa implements DTO{
	private String cnpj;
	private String telefone;
	private ArrayList<Object> listaFornecedor = new ArrayList<>();
	private int id;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public ArrayList<Object> getListaFornecedor() {
		return listaFornecedor;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public void addFornecedor(FornecedorDTO f) {
		listaFornecedor.add(f);
	}
}