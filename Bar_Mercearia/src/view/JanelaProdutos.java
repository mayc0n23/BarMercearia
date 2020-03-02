package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ControllerProduto;
import dto.ProdutoDTO;

public class JanelaProdutos extends JanelaPadraoMedia{
	private DefaultTableModel modelo;
	private JTable tabela;
	private JanelaPadraoGrande janela;
	
	public JanelaProdutos(JanelaPadraoGrande janela) {
		this.setTitle("Produtos");
		this.janela = janela;
		this.setVisible(true);
	}
	public JanelaProdutos() {
		this.setTitle("Produtos");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {}

	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Cadastrar");
		btCadastrar.setToolTipText("Cadastrar produto");
		btCadastrar.setBounds(440, 40, 120, 30);
		btCadastrar.addActionListener(new OuvinteProdutos(this));
		add(btCadastrar);
		CustomButton btEditar = new CustomButton("Editar");
		btEditar.setToolTipText("Editar produto");
		btEditar.setBounds(440, 100, 120, 30);
		btEditar.addActionListener(new OuvinteProdutos(this));
		add(btEditar);
		CustomButton btExcluir = new CustomButton("Excluir");
		btExcluir.setToolTipText("Excluir produto");
		btExcluir.addActionListener(new OuvinteProdutos(this));
		btExcluir.setBounds(440, 160, 120, 30);
		add(btExcluir);
		CustomButton btEntrada = new CustomButton("Entrada");
		btEntrada.setToolTipText("Dar entrada em um produto");
		btEntrada.addActionListener(new OuvinteProdutos(this));
		btEntrada.setBounds(440, 220, 120, 30);
		add(btEntrada);
		CustomButton btVoltar = new CustomButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(janela == null) {
					new JanelaMercearia();
				}else if(janela instanceof JanelaMercearia) {
					new JanelaMercearia();
					janela.dispose();
				}else if(janela instanceof JanelaBar) {
					new JanelaBar();
					janela.dispose();
				}
			}	
		});
		btVoltar.setBounds(440, 280, 120, 30);
		add(btVoltar);
	}

	@Override
	public void addTextField() {}
	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerProduto cp = new ControllerProduto();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"Id", "Nome", "Qtd Estoque", "Und Medida", "Preço"};
		for(String coluna: colunas) {
			modelo.addColumn(coluna);
		}
		for(IteratorOfLists it = new IteratorOfLists(cp.listar().getListaProdutos()); !it.isDone(); it.next()) {
			ProdutoDTO produto = (ProdutoDTO) it.currentItem();
			Object[] dados = {produto.getId(), produto.getNome(), produto.getQntdEstoque(), produto.getUnidadeMedida(), produto.getPrecoUnidade()};
			modelo.addRow(dados);
		}
		JScrollPane conteiner = new JScrollPane(tabela);
		conteiner.setBounds(10, 10, 400, 380);
		add(conteiner);
	}
	public DefaultTableModel getModelo() {
		return modelo;
	}

	public JTable getTabela() {
		return tabela;
	}
	private class OuvinteProdutos implements ActionListener {
		private JanelaProdutos janela;
		
		public OuvinteProdutos(JanelaProdutos janela) {
			this.janela = janela;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerProduto cp = new ControllerProduto();
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cadastrar":
				new JanelaCadastroProdutos();
				dispose();
				break;
			case "Excluir":
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					String nome = (String) modelo.getValueAt(linha, 1);
					ProdutoDTO p = new ProdutoDTO();
					p.setNome(nome);
					cp.deletar(p);
					modelo.removeRow(linha);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
				break;
			case "Editar":
				linha = tabela.getSelectedRow();
				if(linha >= 0) {
					ProdutoDTO p = cp.buscar((String)modelo.getValueAt(linha, 1));
					new JanelaEditarProduto(janela, p, linha);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
				break;
			case "Entrada":
				linha = tabela.getSelectedRow();
				if(linha >= 0) {
					ProdutoDTO p = cp.buscar((String) modelo.getValueAt(linha, 1));
					new JanelaEntradaProduto(p, linha, janela);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
				break;
			}
		}
	}
}