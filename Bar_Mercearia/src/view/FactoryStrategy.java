package view;

//Padr�o Factory Method
//Interface que delega quais metodos classes concretas ir�o implementar
public interface FactoryStrategy {
	public StrategyRelatorio obterRelatorio(String tipo);
}