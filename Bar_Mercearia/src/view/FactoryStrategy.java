package view;

//Padrão Factory Method
//Interface que delega quais metodos classes concretas irão implementar
public interface FactoryStrategy {
	public StrategyRelatorio obterRelatorio(String tipo);
}