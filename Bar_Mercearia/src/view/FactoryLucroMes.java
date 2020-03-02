package view;

public class FactoryLucroMes implements FactoryStrategy{

	@Override
	public StrategyRelatorio obterRelatorio(String tipo) {
		if(tipo.equals("Bar")) {
			return new RelatorioLucroMesBar();
		}
		return new RelatorioLucroMesMercearia();
	}
}