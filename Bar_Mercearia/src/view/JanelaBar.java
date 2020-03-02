package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.VendedorDTO;

public class JanelaBar extends JanelaPadraoGrande{
	private DefaultTableModel modelo;
	private JTable tabela;
	
	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}

	public JTable getTabela() {
		return tabela;
	}

	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}

	public JanelaBar() {
		this.setTitle("Bar do Flamengo");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblMesas = new CustomLabel("Mesas");
		lblMesas.setBounds(260, 10, 120, 30);
		add(lblMesas);
	}

	@Override
	public void addBotoes() {
		CustomButton btAdd = new CustomButton("Adicionar Mesa");
		btAdd.addActionListener(new OuvinteBar(this));
		btAdd.setBounds(550, 200, 190, 30);
		add(btAdd);
		CustomButton btExcluir = new CustomButton("Excluir Mesa");
		btExcluir.addActionListener(new OuvinteBar(this));
		btExcluir.setBounds(550, 260, 190, 30);
		add(btExcluir);
		CustomButton btDetalhes = new CustomButton("Detalhes Mesa");
		btDetalhes.addActionListener(new OuvinteBar(this));
		btDetalhes.setBounds(550, 320, 190, 30);
		add(btDetalhes);
	}

	@Override
	public void addTextField() {}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ManterMesas mm = new ManterMesas();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"Nº Mesa", "Status"};
		for(String s: colunas) {
			modelo.addColumn(s);
		}
		for(int i = 0; i < mm.getMesas().size(); i++) {
			Object[] dados = {mm.getMesas().get(i).getNumMesa(), mm.getMesas().get(i).getStatus()};
			modelo.addRow(dados);
		}
		JScrollPane conteiner = new JScrollPane(tabela);
		conteiner.setBounds(15, 60, 500, 500);
		add(conteiner);
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
		JMenuItem pagamentos = new JMenuItem("Pagamentos");
		pagamentos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VendedorDTO v = new VendedorDTO();
				v.setNome("Edcarlos");
				v.setId(1);
				new JanelaPagamentos(v);
			}
		});
		opcoes.add(pagamentos);
		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new JanelaLogin();
			}
		});
		opcoes.add(sair);
		menuGerenciar.add(produtos);
		menuGerenciar.add(clientes);
		menuBar.add(menuGerenciar);
		menuBar.add(relatorios);
		menuBar.add(opcoes);
		this.setJMenuBar(menuBar);
	}
	private class OuvinteRelatorios implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String mes = "";
			FactoryStrategy fabrica = null;
			String botao = e.getActionCommand();
			if(botao.equals("Clientes com saldo devedor")) {
				fabrica = new FactorySaldoDevedor();
			}else if(botao.equals("Lucro do mês")) {
				fabrica = new FactoryLucroMes();
				String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
				mes = (String) JOptionPane.showInputDialog(null, "Escolha um mês", "Mês", JOptionPane.QUESTION_MESSAGE, null, meses, meses[0]);
			}else if(botao.equals("Produtos em estoque")) {
				fabrica = new FactoryProdutosEmEstoque();
			}else if(botao.equals("Produtos em falta")) {
				fabrica = new FactoryProdutosEsgotados();
			}
			StrategyRelatorio sr = fabrica.obterRelatorio("Bar");
			sr.gerarRelatorio(mes, 1);
		}
	}
	private class OuvinteBar implements ActionListener {
		private JanelaBar janela;
		
		public OuvinteBar(JanelaBar janela) {
			this.janela = janela;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ManterMesas mm = new ManterMesas();
			String botao = e.getActionCommand();
			if(botao.equals("Adicionar Mesa")) {
				Mesa m = new Mesa();
				m.setStatus("Disponivel");
				m.setNumMesa(mm.getCodMesa());
				mm.getMesas().add(m);
				mm.salvarMesas(mm.getMesas());
				Object[] dado = {m.getNumMesa(), m.getStatus()};
				modelo.addRow(dado);
			}else if(botao.equals("Excluir Mesa")) {
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					mm.getMesas().remove(linha);
					modelo.removeRow(linha);
					mm.salvarMesas(mm.getMesas());
				}else {
					JOptionPane.showMessageDialog(null, "Nenhuma mesa está selecionada");
				}
			}else {
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					int n = (int) modelo.getValueAt(linha, 0);
					new JanelaPedidoBar(n, janela);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhuma mesa está selecionada");
				}
			}
		}
	}
	private class OuvinteProdutos implements ActionListener {
		private JanelaBar janela;
		
		public OuvinteProdutos(JanelaBar janela) {
			this.janela = janela;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new JanelaProdutos(janela);
		}
	}
}