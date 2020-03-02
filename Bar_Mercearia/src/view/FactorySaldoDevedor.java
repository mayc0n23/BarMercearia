package view;

//Fabrica que retorna uma estrategia para gerar um relatorio

public class FactorySaldoDevedor implements FactoryStrategy{
	@Override
	public StrategyRelatorio obterRelatorio(String tipo) {
		return new RelatorioSaldoDevedor();
	}
}