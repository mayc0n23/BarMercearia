package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.ControllerCliente;
import dto.ClienteDTO;

public class JanelaEditarCliente extends JanelaPadraoPequena{
	private JanelaClientes janela;
	private ClienteDTO cliente;
	private int linha;
	private JTextField txtNome;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JFormattedTextField txtTel;
	
	public JanelaEditarCliente(JanelaClientes janela, ClienteDTO cliente, int linha) {
		this.janela = janela;
		this.cliente = cliente;
		this.linha = linha;
		this.setTitle("Editar cliente");
		inserirDados();
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblNome = new CustomLabel("NOME");
		lblNome.setBounds(15, 5, 80, 30);
		add(lblNome);
		CustomLabel lblRua = new CustomLabel("RUA");
		lblRua.setBounds(15, 50, 80, 30);
		add(lblRua);
		CustomLabel lblNumero = new CustomLabel("NÚMERO");
		lblNumero.setBounds(10, 95, 115, 30);
		add(lblNumero);
		CustomLabel lblBairro =  new CustomLabel("BAIRRO");
		lblBairro.setBounds(15, 140, 115, 30);
		add(lblBairro);
		CustomLabel lblCidade = new CustomLabel("CIDADE");
		lblCidade.setBounds(15, 185, 100, 30);
		add(lblCidade);
		CustomLabel lblTel = new CustomLabel("TELEFONE");
		lblTel.setBounds(15, 230, 140, 30);
		add(lblTel);
	}

	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Salvar");
		btCadastrar.setBounds(30, 325, 160, 30);
		btCadastrar.addActionListener(new OuvinteditarCliente());
		add(btCadastrar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.setBounds(195, 325, 160, 30);
		btCancelar.addActionListener(new OuvinteditarCliente());
		add(btCancelar);
	}

	@Override
	public void addTextField() {
		txtNome = new CustomTextField();
		txtNome.setBounds(125, 5, 230, 30);
		add(txtNome);
		txtRua = new CustomTextField();
		txtRua.setBounds(125, 50, 230, 30);
		add(txtRua);
		txtNumero = new CustomTextField();
		txtNumero.setBounds(125, 95, 230, 30);
		txtNumero.addKeyListener(new OuvinteAceitarNumeros(txtNumero));
		add(txtNumero);
		txtBairro = new CustomTextField();
		txtBairro.setBounds(125, 140, 230, 30);
		add(txtBairro);
		txtCidade = new CustomTextField();
		txtCidade.setBounds(123, 185, 230, 30);
		add(txtCidade);
		try {
			MaskFormatter mask = new MaskFormatter("(##)#########");
			txtTel = new JFormattedTextField(mask);
			txtTel.setBounds(165, 230, 200, 30);
			txtTel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			add(txtTel);
		}catch(ParseException e) {}
	}
	@Override
	public void addComboBox() {}
	@Override
	public void addTabela() {}
	public void inserirDados() {
		txtNome.setText(cliente.getNome());
		txtRua.setText(cliente.getRua());
		txtNumero.setText(Integer.toString(cliente.getNumero()));
		txtBairro.setText(cliente.getBairro());
		txtCidade.setText(cliente.getCidade());
		txtTel.setText(cliente.getTelefone());
	}
	private class OuvinteditarCliente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			switch(botao) {
			case "Salvar":
				ControllerCliente cc = new ControllerCliente();
				ClienteDTO clientee = new ClienteDTO();
				clientee.setNome(txtNome.getText());
				clientee.setRua(txtRua.getText());
				clientee.setNumero(Integer.parseInt(txtNumero.getText()));
				clientee.setBairro(txtBairro.getText());
				clientee.setCidade(txtCidade.getText());
				clientee.setTelefone(txtTel.getText());
				cc.editar(clientee);
				Object[] dados = {cliente.getId(), clientee.getNome(), clientee.getSaldoDevedor(), clientee.getRua(), clientee.getNumero(),
						clientee.getBairro(), clientee.getCidade(), clientee.getTelefone()};
				janela.getModelo().addRow(dados);
				janela.getModelo().removeRow(linha);
				dispose();
				break;
			case "Cancelar":
				dispose();
				break;
			}
		}
	}
}