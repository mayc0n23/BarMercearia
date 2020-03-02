package view;

public class FactoryProdutosEmEstoque implements FactoryStrategy{

	@Override
	public StrategyRelatorio obterRelatorio(String tipo) {
		return new RelatorioProdutosEmEstoque();
	}
}