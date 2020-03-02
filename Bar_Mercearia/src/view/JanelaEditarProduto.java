package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import controller.ControllerProduto;
import dto.ProdutoDTO;

public class JanelaEditarProduto extends JanelaPadraoPequena{
	private JTextField txtNome;
	private JTextField txtPreco;
	private JComboBox undMedida;
	private JanelaProdutos janela;
	private ProdutoDTO produto;
	private int linha;
	
	public JanelaEditarProduto(JanelaProdutos janela, ProdutoDTO produto, int linha) {
		this.setTitle("Editar produto");
		this.janela = janela;
		this.produto = produto;
		this.linha = linha;
		inserirDados();
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblNome = new CustomLabel("NOME");
		lblNome.setBounds(20, 20, 100, 30);
		add(lblNome);
		CustomLabel lblPreco = new CustomLabel("PREÇO");
		lblPreco.setBounds(20, 80, 100, 30);
		add(lblPreco);
		CustomLabel lblUnidade = new CustomLabel("UNIDADE DE MEDIDA:");
		lblUnidade.setBounds(20, 140, 290, 30);
		add(lblUnidade);
	}

	@Override
	public void addBotoes() {
		CustomButton btSalvar = new CustomButton("Salvar");
		btSalvar.addActionListener(new OuvinteEditarProduto());
		btSalvar.setBounds(130, 240, 120, 30);
		add(btSalvar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.addActionListener(new OuvinteEditarProduto());
		btCancelar.setBounds(130, 290, 120, 30);
		add(btCancelar);
	}

	@Override
	public void addTextField() {
		txtNome = new CustomTextField();
		txtNome.setBounds(110, 20, 220, 30);
		add(txtNome);
		txtPreco = new CustomTextField();
		txtPreco.setBounds(110, 80, 220, 30);
		txtPreco.addKeyListener(new OuvinteAceitarNumeros(txtPreco));
		add(txtPreco);
	}

	@Override
	public void addComboBox() {
		String[] opcoes = {"KG", "UND", "LITRO", "PORÇÃO"};
		undMedida = new JComboBox<>(opcoes);
		undMedida.setBounds(20, 190, 280, 30);
		undMedida.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		add(undMedida);
	}

	@Override
	public void addTabela() {}
	public void inserirDados() {
		txtNome.setText(produto.getNome());
		txtPreco.setText(Float.toString(produto.getPrecoUnidade()));
	}
	private class OuvinteEditarProduto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerProduto cp = new ControllerProduto();
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cancelar":
				dispose();
				break;
			case "Salvar":
				produto.setNome(txtNome.getText());
				produto.setPrecoUnidade(Float.parseFloat(txtPreco.getText()));
				produto.setUnidadeMedida((String)undMedida.getSelectedItem());
				cp.editar(produto);
				janela.getModelo().removeRow(linha);
				ProdutoDTO p = cp.buscar(produto.getNome());
				Object[] dados = {p.getId(), p.getNome(), p.getQntdEstoque(), p.getUnidadeMedida(), p.getPrecoUnidade()};
				janela.getModelo().addRow(dados);
				dispose();
				break;
			}
		}
	}
}