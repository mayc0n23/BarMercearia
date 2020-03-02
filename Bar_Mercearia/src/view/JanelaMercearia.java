package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ControllerCliente;
import controller.ControllerPagamento;
import controller.ControllerPedido;
import controller.ControllerProduto;
import dto.ClienteDTO;
import dto.PagamentoDTO;
import dto.PedidoDTO;
import dto.ProdutoDTO;
import dto.VendedorDTO;

public class JanelaMercearia extends JanelaPadraoGrande{
	private DefaultTableModel modeloProduto;
	private JTable tabelaProduto;
	private DefaultTableModel modeloCarrinho;
	private JTable tabelaCarrinho;
	private CustomLabel lblValor;
	private ArrayList<ProdutoDTO> produtosCarrinho = new ArrayList<>();
	
	public  JanelaMercearia() {
		this.setTitle("Mercearia do Flamengo");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblProdutos = new CustomLabel("PRODUTOS");
		lblProdutos.setBounds(110, 20, 150, 30);
		add(lblProdutos);
		CustomLabel lblCarrinho = new CustomLabel("CARRINHO");
		lblCarrinho.setBounds(535, 20, 150, 30);
		add(lblCarrinho);
		CustomLabel lblTotal = new CustomLabel("Total da compra:");
		lblTotal.setBounds(480, 520, 190, 30);
		add(lblTotal);
		lblValor = new CustomLabel("0");
		lblValor.setBounds(668, 520, 80, 30);
		add(lblValor);
	}

	@Override
	public void addBotoes() {
		JButton btInserir = new JButton(",");
		btInserir.setIcon(new ImageIcon(getClass().getResource("/images/icons8-inserir-coluna-à-direita-30.png")));
		btInserir.setBounds(395, 180, 34, 34);
		btInserir.setToolTipText("Inserir no carrinho");
		btInserir.addActionListener(new OuvinteMercearia());
		add(btInserir);
		JButton btRemover = new JButton(".");
		btRemover.setIcon(new ImageIcon(getClass().getResource("/images/icons8-inserir-coluna-esquerda-30.png")));
		btRemover.setBounds(395, 240, 34, 34);
		btRemover.setToolTipText("Remover do carrinho");
		btRemover.addActionListener(new OuvinteMercearia());
		add(btRemover);
		CustomButton btFinalizar = new CustomButton("Finalizar compra");
		btFinalizar.setBounds(500, 570, 200, 30);
		btFinalizar.addActionListener(new OuvinteMercearia());
		add(btFinalizar);
	}

	@Override
	public void addTextField() {}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerProduto cp = new ControllerProduto();
		modeloProduto = new DefaultTableModel();
		tabelaProduto = new JTable(modeloProduto);
		String[] colunasProduto = {"Id", "Nome", "Preço", "Und Medida", "Qtd Estoque"};
		for(String colunaProduto: colunasProduto) {
			modeloProduto.addColumn(colunaProduto);
		}
		for(IteratorOfLists it = new IteratorOfLists(cp.listar().getListaProdutos()); !it.isDone(); it.next()) {
			ProdutoDTO p = (ProdutoDTO) it.currentItem();
			Object[] dados = {p.getId(), p.getNome(), p.getPrecoUnidade(), p.getUnidadeMedida(), p.getQntdEstoque()};
			modeloProduto.addRow(dados);
		}
		JScrollPane conteinerProduto = new JScrollPane(tabelaProduto);
		conteinerProduto.setBounds(20, 60, 350, 550);
		add(conteinerProduto);
		
		modeloCarrinho = new DefaultTableModel();
		tabelaCarrinho = new JTable(modeloCarrinho);
		String[] colunasCarrinho = {"Id", "Nome", "Qtd", "Total"};
		for(String coluna: colunasCarrinho) {
			modeloCarrinho.addColumn(coluna);
		}
		JScrollPane conteinerCarrinho = new JScrollPane(tabelaCarrinho);
		conteinerCarrinho.setBounds(460, 60, 300, 450);
		add(conteinerCarrinho);
	}

	@Override
	public void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuGerenciar = new JMenu("Gerenciar");
		JMenuItem fornecedores = new JMenuItem("Fornecedores");
		fornecedores.setIcon(new ImageIcon(getClass().getResource("/images/icons8-fornecedor-16.png")));
		fornecedores.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaFornecedores();
			}
		});
		menuGerenciar.add(fornecedores);
		JMenuItem clientes = new JMenuItem("Clientes");
		clientes.setIcon(new ImageIcon(getClass().getResource("/images/icons8-empresa-cliente-16.png")));
		clientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaClientes();
			}
		});
		JMenuItem produtos = new JMenuItem("Produtos");
		produtos.setIcon(new ImageIcon(getClass().getResource("/images/icons8-produto-16.png")));
		produtos.addActionListener(new OuvinteProdutos(this));
		menuGerenciar.add(produtos);
		menuGerenciar.add(clientes);
		JMenu relatorios = new JMenu("Relatórios");
		JMenuItem saldoDev = new JMenuItem("Clientes com saldo devedor");
		saldoDev.addActionListener(new OuvinteRelatorios());
		relatorios.add(saldoDev);
		JMenuItem lucroMes = new JMenuItem("Lucro do mês");
		lucroMes.addActionListener(new OuvinteRelatorios());
		relatorios.add(lucroMes);
		JMenuItem faltaEstoque = new JMenuItem("Produtos em falta");
		faltaEstoque.addActionListener(new OuvinteRelatorios());
		relatorios.add(faltaEstoque);
		JMenuItem emEstoque = new JMenuItem("Produtos em estoque");
		emEstoque.addActionListener(new OuvinteRelatorios());
		relatorios.add(emEstoque);
		JMenu opcoes = new JMenu("Opções");
		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new JanelaLogin();
			}
		});
		JMenuItem pagamentos = new JMenuItem("Pagamentos");
		pagamentos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VendedorDTO v = new VendedorDTO();
				v.setNome("Sandra");
				v.setId(2);
				new JanelaPagamentos(v);
			}
		});
		opcoes.add(pagamentos);
		opcoes.add(sair);
		menuBar.add(menuGerenciar);
		menuBar.add(relatorios);
		menuBar.add(opcoes);
		this.setJMenuBar(menuBar);
	}
	private class OuvinteMercearia implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerProduto cp = new ControllerProduto();
			String botao = e.getActionCommand();
			if(botao.equals(",")) {
				int linha = tabelaProduto.getSelectedRow();
				if(linha >= 0) {
					int qtdDesejada = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantidade que deseja"));
					int qtdEstoque = (int) modeloProduto.getValueAt(linha, 4);
					if(qtdDesejada > qtdEstoque) {
						JOptionPane.showMessageDialog(null, "Quantidade inválida");
					}else {
						float preco = (float) modeloProduto.getValueAt(linha, 2);
						float total = qtdDesejada * preco;
						String nome = (String) modeloProduto.getValueAt(linha, 1);
						ProdutoDTO p = cp.buscar(nome);
						produtosCarrinho.add(p);
						Object[] dados = {p.getId(), p.getNome(), qtdDesejada, total};
						modeloCarrinho.addRow(dados);
						float precoLbl = Float.parseFloat(lblValor.getText());
						precoLbl += total;
						lblValor.setText(Float.toString(precoLbl));
					}
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
			}else if(botao.equals(".")) {
				int linha = tabelaCarrinho.getSelectedRow();
				if(linha >= 0) {
					float precoLinha = (float) modeloCarrinho.getValueAt(linha, 3);
					modeloCarrinho.removeRow(linha);
					produtosCarrinho.remove(linha);
					float valor = Float.parseFloat(lblValor.getText());
					valor -= precoLinha;
					lblValor.setText(Float.toString(valor));
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
			}else if(botao.equals("Finalizar compra")){
				if(produtosCarrinho.size() > 0) {
					ControllerCliente cc = new ControllerCliente();
					VendedorDTO v = new VendedorDTO();
					v.setId(2);
					v.setNome("Sandra");
					int idCliente = cc.pegarId(JOptionPane.showInputDialog(null, "Digite o cpf do cliente"));
					if(idCliente == 0) {
						JOptionPane.showMessageDialog(null, "Cliente não cadastrado");
					}else {
						PedidoDTO pedido = new PedidoDTO();
						ClienteDTO cliente = cc.buscar(idCliente);
						cliente.setId(idCliente);
						pedido.setCliente(cliente);
						pedido.setVendedor(v);
						pedido.setValor(Float.parseFloat(lblValor.getText()));
						ControllerPedido ccp = new ControllerPedido();
						ccp.cadastrar(pedido);
						int qtdLinhas = tabelaCarrinho.getRowCount();
						int idPedido = ccp.pegarId();
						for(int i = 0; i < qtdLinhas; i++) {
							int idProduto = (int) modeloCarrinho.getValueAt(i, 0);
							int qtd = (int) modeloCarrinho.getValueAt(i, 2);
							ccp.inserirPedido(idProduto, idPedido, qtd);
						}
						String[] op = {"Pagar agora", "Deixar fiado"};
						String res = (String) JOptionPane.showInputDialog(null, "Opções de pagamento", "Pagamento", JOptionPane.QUESTION_MESSAGE,null, op, op[0]);
						if(res.equals("Pagar agora")) {
							ControllerPagamento cpp = new ControllerPagamento();
							PagamentoDTO pag = new PagamentoDTO();
							pag.setCliente(idCliente);
							pag.setVendedor(v.getId());
							pag.setValor(pedido.getValor());
							cpp.pagar(pag);
						}else {
							cc.atualizarSaldo(idCliente, pedido.getValor());
						}
						NotaFiscal nf = new NotaFiscal();
						nf.gerarNota(cliente, produtosCarrinho, pedido.getValor());
						dispose();
						new JanelaMercearia();
					}
				}else {
					JOptionPane.showMessageDialog(null, "O carrinho está vazio");
				}
			}
		}
	}
	private class OuvinteRelatorios implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String mes = "Janeiro";
			FactoryStrategy fabrica = null;
			String botao = e.getActionCommand();
			if(botao.equals("Clientes com saldo devedor")) {
				fabrica = new FactorySaldoDevedor();
			}else if(botao.equals("Lucro do mês")) {
				fabrica = new FactoryLucroMes();
				String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
				mes = (String) JOptionPane.showInputDialog(null, "Escolha um mês", "Mês", JOptionPane.QUESTION_MESSAGE, null, meses, meses[0]);
			}else if(botao.equals("Produtos em falta")) {
				fabrica = new FactoryProdutosEsgotados();
			}else if(botao.equals("Produtos em estoque")) {
				fabrica = new FactoryProdutosEmEstoque();
			}
			StrategyRelatorio sr = fabrica.obterRelatorio("Mercearia");
			sr.gerarRelatorio(mes, 2);
		}
	}
	public void atualizarTabelaProdutos() {
		ControllerProduto cp = new ControllerProduto();
		for(int i = 0; i < tabelaProduto.getRowCount(); i++) {
			modeloProduto.removeRow(0);
		}
		for(IteratorOfLists it = new IteratorOfLists(cp.listar().getListaProdutos()); !it.isDone(); it.next()) {
			ProdutoDTO p = (ProdutoDTO) it.currentItem();
			Object[] dados = {p.getId(), p.getNome(), p.getPrecoUnidade(), p.getUnidadeMedida(), p.getQntdEstoque()};
			modeloProduto.addRow(dados);
		}
	}
	private class OuvinteProdutos implements ActionListener {
		private JanelaMercearia janela;
		
		public OuvinteProdutos(JanelaMercearia janela) {
			this.janela = janela;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new JanelaProdutos(janela);
		}
	}
}