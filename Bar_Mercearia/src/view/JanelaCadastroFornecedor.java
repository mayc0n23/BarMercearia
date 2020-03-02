package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.ControllerFornecedor;
import dto.FornecedorDTO;
import model.FornecedorJaCadastradoException;

public class JanelaCadastroFornecedor extends JanelaPadraoPequena{
	private JTextField txtNome;
	private JFormattedTextField txtCNPJ;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JFormattedTextField txtTel;
	private JanelaFornecedores janela;
	
	public JanelaCadastroFornecedor(JanelaFornecedores janela) {
		this.janela = janela;
		this.setTitle("Cadastrar Fornecedor");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblNome = new CustomLabel("NOME");
		lblNome.setBounds(15, 5, 80, 30);
		add(lblNome);
		CustomLabel lblCnpj = new CustomLabel("CNPJ");
		lblCnpj.setBounds(15, 50, 80, 30);
		add(lblCnpj);
		CustomLabel lblRua = new CustomLabel("RUA");
		lblRua.setBounds(15, 95, 80, 30);
		add(lblRua);
		CustomLabel lblNumero = new CustomLabel("NÚMERO");
		lblNumero.setBounds(15, 140, 115, 30);
		add(lblNumero);
		CustomLabel lblBairro =  new CustomLabel("BAIRRO");
		lblBairro.setBounds(15, 185, 100, 30);
		add(lblBairro);
		CustomLabel lblCidade = new CustomLabel("CIDADE");
		lblCidade.setBounds(15, 230, 110, 30);
		add(lblCidade);
		CustomLabel lblTel = new CustomLabel("TELEFONE");
		lblTel.setBounds(15, 275, 140, 30);
		add(lblTel);
	}

	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Cadastrar");
		btCadastrar.setBounds(30, 315, 160, 30);
		btCadastrar.addActionListener(new OuvinteCadastro());
		add(btCadastrar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.setBounds(195, 315, 160, 30);
		btCancelar.addActionListener(new OuvinteCadastro());
		add(btCancelar);
	}

	@Override
	public void addTextField() {
		txtCidade = new CustomTextField();
		txtCidade.setBounds(123, 230, 230, 30);
		add(txtCidade);
		txtNome = new CustomTextField();
		txtNome.setBounds(125, 5, 230, 30);
		add(txtNome);
		try {
			MaskFormatter mascara = new MaskFormatter("##.###.###/####-##");
			txtCNPJ = new JFormattedTextField(mascara);
			txtCNPJ.setBounds(125, 50, 230, 30);
			txtCNPJ.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			add(txtCNPJ);
		} catch (ParseException e) {}
		txtRua = new CustomTextField();
		txtRua.setBounds(125, 95, 230, 30);
		add(txtRua);
		txtNumero = new CustomTextField();
		txtNumero.setBounds(125, 140, 230, 30);
		txtNumero.addKeyListener(new OuvinteAceitarNumeros(txtNumero));
		add(txtNumero);
		txtBairro = new CustomTextField();
		txtBairro.setBounds(125, 185, 230, 30);
		add(txtBairro);
		try {
			MaskFormatter mask = new MaskFormatter("(##)#########");
			txtTel = new JFormattedTextField(mask);
			txtTel.setBounds(165, 275, 200, 30);
			txtTel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			add(txtTel);
		}catch(ParseException e) {}
	}
	public void addComboBox() {}
	public void addTabela() {}
	
	private class OuvinteCadastro implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cancelar":
				dispose();
				break;
			case "Cadastrar":
				FornecedorDTO fornecedor = new FornecedorDTO();
				fornecedor.setNome(txtNome.getText());
				fornecedor.setCnpj(txtCNPJ.getText());
				fornecedor.setRua(txtRua.getText());
				fornecedor.setNumero(Integer.parseInt(txtNumero.getText()));
				fornecedor.setBairro(txtBairro.getText());
				fornecedor.setCidade(txtCidade.getText());
				fornecedor.setTelefone(txtTel.getText());
				ControllerFornecedor cf = new ControllerFornecedor();
				try {
					cf.cadastrar(fornecedor);
					Object[] dados = {fornecedor.getNome(), fornecedor.getCnpj(), fornecedor.getRua(), fornecedor.getNumero(), fornecedor.getBairro(), fornecedor.getCidade(), fornecedor.getTelefone()};
					janela.getModelo().addRow(dados);
					
				} catch (FornecedorJaCadastradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				dispose();
			}
		}
	}
}