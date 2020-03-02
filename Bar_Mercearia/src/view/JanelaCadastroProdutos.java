package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import controller.ControllerFornecedor;
import controller.ControllerProduto;
import dto.FornecedorDTO;
import dto.ProdutoDTO;

public class JanelaCadastroProdutos extends JanelaPadraoMedia{
	private JTextField txtNome;
	private JTextField txtQntdEstoque;
	private JTextField txtPrecoProduto;
	private JFormattedTextField txtDataValidade;
	private JTextField txtPrecoCompra;
	private JFormattedTextField txtDataCompra;
	private DefaultTableModel modelo;
	private JTable tabela;
	private JComboBox undMedida;
	
	public JanelaCadastroProdutos() {
		this.setTitle("Cadastrar produto");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel produto = new CustomLabel("PRODUTO");
		produto.setBounds(80, 10, 130, 30);
		add(produto);
		CustomLabel fornecedor = new CustomLabel("FORNECEDOR");
		fornecedor.setBounds(300, 10, 180, 30);
		add(fornecedor);
		CustomLabel lblNome = new CustomLabel("Nome");
		lblNome.setBounds(10, 60, 100, 30);
		add(lblNome);
		CustomLabel lblQtd = new CustomLabel("Qtd Estoque");
		lblQtd.setBounds(10, 100, 140, 30);
		add(lblQtd);
		CustomLabel lblPreco = new CustomLabel("Preço");
		lblPreco.setBounds(10, 140, 100, 30);
		add(lblPreco);
		CustomLabel lblDataValidade = new CustomLabel("Validade");
		lblDataValidade.setBounds(10, 180, 100, 30);
		add(lblDataValidade);
		CustomLabel lblDataCompra = new CustomLabel("Data entrada");
		lblDataCompra.setBounds(280, 60, 160, 30);
		add(lblDataCompra);
		CustomLabel lblPrecoCompra = new CustomLabel("Preço fornecido");
		lblPrecoCompra.setBounds(280, 100, 180, 30);
		add(lblPrecoCompra);
		CustomLabel lblSelecione = new CustomLabel("Selecione o fornecedor:");
		lblSelecione.setBounds(290, 140, 255, 30);
		add(lblSelecione);
		CustomLabel lblUnidade = new CustomLabel("Unidade Medida:");
		lblUnidade.setBounds(10, 220, 185, 30);
		add(lblUnidade);
	}

	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Cadastrar");
		btCadastrar.setBounds(40, 310, 120, 30);
		btCadastrar.addActionListener(new OuvinteCadastroProdutos());
		add(btCadastrar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.addActionListener(new OuvinteCadastroProdutos());
		btCancelar.setBounds(40, 350, 120, 30);
		add(btCancelar);
	}

	@Override
	public void addTextField() {
		txtNome = new CustomTextField();
		txtNome.setBounds(80, 60, 180, 30);
		txtNome.setToolTipText("Nome do produto");
		add(txtNome);
		txtQntdEstoque = new CustomTextField();
		txtQntdEstoque.setBounds(150, 100, 110, 30);
		txtQntdEstoque.setToolTipText("Quantidade do produto no estoque");
		txtQntdEstoque.addKeyListener(new OuvinteAceitarNumeros(txtQntdEstoque));
		add(txtQntdEstoque);
		txtPrecoProduto = new CustomTextField();
		txtPrecoProduto.setBounds(80, 140, 180, 30);
		txtPrecoProduto.setToolTipText("Preço do produto para venda");
		txtPrecoProduto.addKeyListener(new OuvinteAceitarNumeros(txtPrecoProduto));
		add(txtPrecoProduto);
		try {
			MaskFormatter mask = new MaskFormatter("####-##-##");
			txtDataValidade = new JFormattedTextField(mask);
			txtDataValidade.setBounds(110, 180, 150, 30);
			txtDataValidade.setToolTipText("Data de validade do produto");
			txtDataValidade.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			add(txtDataValidade);
			txtDataCompra = new JFormattedTextField(mask);
			txtDataCompra.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			txtDataCompra.setToolTipText("Data da entrada do produto");
			txtDataCompra.setBounds(425, 60, 150, 30);
			add(txtDataCompra);
		} catch (ParseException e) {}
		txtPrecoCompra = new CustomTextField();
		txtPrecoCompra.setBounds(455, 100, 120, 30);
		txtPrecoCompra.setToolTipText("Preço do produto fornecido junto ao fornecedor");
		txtPrecoCompra.addKeyListener(new OuvinteAceitarNumeros(txtPrecoCompra));
		add(txtPrecoCompra);
	}

	@Override
	public void addComboBox() {
		String[] opcoes = {"KG", "UND", "LITRO", "PORÇÃO"};
		undMedida = new JComboBox<>(opcoes);
		undMedida.setBounds(10, 260, 180, 30);
		undMedida.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		add(undMedida);
	}

	@Override
	public void addTabela() {
		ControllerFornecedor cf = new ControllerFornecedor();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"Nome", "CNPJ", "Telefone"};
		for(String coluna: colunas) {
			modelo.addColumn(coluna);
		}
		for(IteratorOfLists it = new IteratorOfLists(cf.listar().getListaFornecedor()); !it.isDone(); it.next()) {
			FornecedorDTO f = (FornecedorDTO) it.currentItem();
			Object[] dados = {f.getNome(), f.getCnpj(), f.getTelefone()};
			modelo.addRow(dados);
		}
		JScrollPane conteiner = new JScrollPane(tabela);
		conteiner.setBounds(280, 180, 280, 200);
		add(conteiner);
	}
	private class OuvinteCadastroProdutos implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cancelar":
				dispose();
				new JanelaProdutos();
				break;
			case "Cadastrar":
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					ControllerProduto cp = new ControllerProduto();
					ProdutoDTO produto = new ProdutoDTO();
					produto.setNome(txtNome.getText());
					produto.setPrecoUnidade(Float.parseFloat(txtPrecoProduto.getText()));
					produto.setUnidadeMedida((String)undMedida.getSelectedItem());
					produto.setQntdEstoque(0);
					cp.cadastrar(produto);
					ControllerFornecedor cf = new ControllerFornecedor();
					String cnpj = (String) modelo.getValueAt(linha, 1);
					FornecedorDTO f = cf.buscar(cnpj);
					ProdutoDTO p = cp.buscar(produto.getNome());
					cp.inserirLote(p.getId(), txtDataValidade.getText());
					cp.inserirCompraProduto(p.getId(), f.getId(), Float.parseFloat(txtPrecoCompra.getText()), txtDataCompra.getText(), Integer.parseInt(txtQntdEstoque.getText()));
					dispose();
					new JanelaProdutos();
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum fornecedor está selecionado");
				}
				break;
			}
		}
	}
}