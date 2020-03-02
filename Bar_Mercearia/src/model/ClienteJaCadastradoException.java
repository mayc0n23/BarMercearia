package model;

public class ClienteJaCadastradoException extends Exception{
	public String getMessage() {
		return "Cliente já cadastrado";
	}
}