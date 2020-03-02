package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.ControllerCliente;
import dto.ClienteDTO;
import model.ClienteJaCadastradoException;

public class JanelaCadastroCliente extends JanelaPadraoPequena{
	private JanelaClientes janela;
	private JTextField txtNome;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JFormattedTextField txtTel;
	private JFormattedTextField txtCpf;
	
	public JanelaCadastroCliente(JanelaClientes janela) {
		this.janela = janela;
		this.setTitle("Cadastrar cliente");
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
		CustomLabel lblCpf = new CustomLabel("CPF");
		lblCpf.setBounds(15, 275, 100, 30);
		add(lblCpf);
	}

	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Cadastrar");
		btCadastrar.setBounds(30, 325, 160, 30);
		btCadastrar.addActionListener(new OuvinteCadastro());
		add(btCadastrar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.setBounds(195, 325, 160, 30);
		btCancelar.addActionListener(new OuvinteCadastro());
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
			mask = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(mask);
			txtCpf.setBounds(125, 275, 230, 30);
			txtCpf.setFont(new Font("Times New Roman", Font.PLAIN, 22));
			add(txtCpf);
		}catch(ParseException e) {}
	}

	@Override
	public void addComboBox() {}
	@Override
	public void addTabela() {}
	
	private class OuvinteCadastro implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerCliente cc = new ControllerCliente();
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cadastrar":
				ClienteDTO cliente = new ClienteDTO();
				cliente.setNome(txtNome.getText());
				cliente.setSaldoDevedor(0);
				cliente.setRua(txtRua.getText());
				cliente.setNumero(Integer.parseInt(txtNumero.getText()));
				cliente.setBairro(txtBairro.getText());
				cliente.setCidade(txtCidade.getText());
				cliente.setTelefone(txtTel.getText());
				cliente.setCpf(txtCpf.getText());
				try {
					cc.cadastrar(cliente);
					int id = cc.pegarId(cliente.getNome(), cliente.getRua());
					Object[] dados = {id, cliente.getNome(), cliente.getSaldoDevedor(), cliente.getRua(), cliente.getNumero(), cliente.getBairro(), cliente.getCidade(), cliente.getTelefone()};
					janela.getModelo().addRow(dados);
					dispose();
				} catch (ClienteJaCadastradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				break;
			case "Cancelar":
				dispose();
				break;
			}
		}
	}
}