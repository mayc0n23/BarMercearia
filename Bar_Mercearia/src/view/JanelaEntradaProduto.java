package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class JanelaEntradaProduto extends JanelaPadraoPequena{
	private JTextField txtNome;
	private JTextField txtPreco;
	private JTextField txtQtd;
	private JFormattedTextField txtData;
	private DefaultTableModel modelo;
	private JTable tabela;
	private ProdutoDTO produto;
	private JanelaProdutos janela;
	private int linha;
	
	public JanelaEntradaProduto(ProdutoDTO produto, int linha, JanelaProdutos janela) {
		this.setTitle("Entrada de um produto");
		this.produto = produto;
		this.linha = linha;
		this.janela = janela;
		inserirDado();
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblNome = new CustomLabel("Produto");
		lblNome.setBounds(20, 10, 100, 30);
		add(lblNome);
		CustomLabel lblQtd = new CustomLabel("Quantidade");
		lblQtd.setBounds(20, 50, 130, 30);
		add(lblQtd);
		CustomLabel lblPreco = new CustomLabel("Preço");
		lblPreco.setBounds(20, 100, 100, 30);
		add(lblPreco);
		CustomLabel lblData = new CustomLabel("Validade");
		lblData.setBounds(20, 150, 120, 30);
		add(lblData);
	}

	@Override
	public void addBotoes() {
		CustomButton btSalvar = new CustomButton("Salvar");
		btSalvar.addActionListener(new OuvinteEntradaProduto());
		btSalvar.setBounds(240, 220, 120, 30);
		add(btSalvar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.addActionListener(new OuvinteEntradaProduto());
		btCancelar.setBounds(240, 270, 120, 30);
		add(btCancelar);
	}

	@Override
	public void addTextField() {
		txtNome = new CustomTextField();
		txtNome.setBounds(110, 10, 220, 30);
		txtNome.setEnabled(false);
		add(txtNome);
		txtQtd = new CustomTextField();
		txtQtd.setBounds(150, 50, 180, 30);
		txtQtd.addKeyListener(new OuvinteAceitarNumeros(txtQtd));
		add(txtQtd);
		txtPreco = new CustomTextField();
		txtPreco.setBounds(150, 100, 180, 30);
		txtPreco.addKeyListener(new OuvinteAceitarNumeros(txtPreco));
		add(txtPreco);
		try {
			MaskFormatter mask = new MaskFormatter("####-##-##");
			txtData = new JFormattedTextField(mask);
			txtData.setBounds(150, 150, 180, 30);
			txtData.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			add(txtData);
		} catch (ParseException e) {}
	}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerFornecedor cf = new ControllerFornecedor();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"NOME", "CNPJ"};
		for(String coluna: colunas) {
			modelo.addColumn(coluna);
		}
		for(IteratorOfLists it = new IteratorOfLists(cf.listar().getListaFornecedor()); !it.isDone(); it.next()) {
			FornecedorDTO f = (FornecedorDTO) it.currentItem();
			Object[] dados = {f.getNome(), f.getCnpj()};
			modelo.addRow(dados);
		}
		JScrollPane conteiner = new JScrollPane(tabela);
		conteiner.setBounds(10, 190, 200, 150);
		add(conteiner);
	}
	public void inserirDado() {
		txtNome.setText(produto.getNome());
	}
	private class OuvinteEntradaProduto implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cancelar":
				dispose();
				break;
			case "Salvar":
				ControllerFornecedor cf = new ControllerFornecedor();
				ControllerProduto cp = new ControllerProduto();
				int row = tabela.getSelectedRow();
				if(row >= 0) {
					String cnpj = (String) modelo.getValueAt(row, 1);
					FornecedorDTO f = cf.buscar(cnpj);
					cp.inserirLote(produto.getId(), txtData.getText());
					Date data = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String datee = sdf.format(data);
					cp.inserirCompraProduto(produto.getId(), f.getId(), Float.parseFloat(txtPreco.getText()), datee, Integer.parseInt(txtQtd.getText()));
					janela.getModelo().removeRow(linha);
					ProdutoDTO p = cp.buscar(produto.getNome());
					Object[] dados = {p.getId(), p.getNome(), p.getQntdEstoque(), p.getUnidadeMedida(), p.getPrecoUnidade()};
					janela.getModelo().addRow(dados);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um fornecedor");
				}
				break;
			}
		}
	}
}