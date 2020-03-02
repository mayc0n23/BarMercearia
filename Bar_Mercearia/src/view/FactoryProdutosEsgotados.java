package view;

public class FactoryProdutosEsgotados implements FactoryStrategy{
	@Override
	public StrategyRelatorio obterRelatorio(String tipo) {
		// TODO Auto-generated method stub
		return new RelatorioProdutosEsgotados();
	}
}