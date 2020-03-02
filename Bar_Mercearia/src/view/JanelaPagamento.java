package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import controller.ControllerCliente;
import controller.ControllerPagamento;
import dto.ClienteDTO;
import dto.PagamentoDTO;
import dto.VendedorDTO;

public class JanelaPagamento extends JanelaPadraoPequena{
	private JTextField txtRecebedor;
	private JTextField txtCliente;
	private CustomLabel lblValor;
	private JTextField txtRecebido;
	private VendedorDTO vendedor;
	private ClienteDTO cliente;
	
	public JanelaPagamento(VendedorDTO v, ClienteDTO d) {
		this.vendedor = v;
		this.cliente = d;
		this.setTitle("Realizar pagamento");
		inserirDados();
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblRecebedor = new CustomLabel("Recebedor:");
		lblRecebedor.setBounds(10, 15, 130, 30);
		add(lblRecebedor);
		CustomLabel lblCliente = new CustomLabel("Cliente:");
		lblCliente.setBounds(10, 60, 130, 30);
		add(lblCliente);
		CustomLabel lblTotal = new CustomLabel("Saldo devedor:");
		lblTotal.setBounds(10, 105, 190, 30);
		add(lblTotal);
		lblValor = new CustomLabel("0");
		lblValor.setBounds(200, 105, 50, 30);
		add(lblValor);
		CustomLabel lblQuantia = new CustomLabel("Quantia recebida:");
		lblQuantia.setBounds(10, 150, 210, 30);
		add(lblQuantia);
	}

	@Override
	public void addBotoes() {
		CustomButton btPagar = new CustomButton("Pagar");
		btPagar.setBounds(120, 250, 120, 30);
		btPagar.addActionListener(new OuvintePagamento());
		add(btPagar);
		CustomButton btCancelar = new CustomButton("Cancelar");
		btCancelar.addActionListener(new OuvintePagamento());
		btCancelar.setBounds(120, 300, 120, 30);
		add(btCancelar);
	}

	@Override
	public void addTextField() {
		txtRecebedor = new CustomTextField();
		txtRecebedor.setBounds(135, 15, 220, 30);
		txtRecebedor.setEnabled(false);
		add(txtRecebedor);
		txtCliente = new CustomTextField();
		txtCliente.setBounds(135, 60, 220, 30);
		txtCliente.setEnabled(false);
		add(txtCliente);
		txtRecebido = new CustomTextField();
		txtRecebido.addKeyListener(new OuvinteAceitarNumeros(txtRecebido));
		txtRecebido.setBounds(200, 150, 120, 30);
		add(txtRecebido);
	}

	@Override
	public void addComboBox() {}
	@Override
	public void addTabela() {}
	
	public void inserirDados() {
		txtRecebedor.setText(vendedor.getNome());
		txtCliente.setText(cliente.getNome());
		lblValor.setText(Float.toString(cliente.getSaldoDevedor()));
	}
	
	private class OuvintePagamento implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			if(botao.equals("Pagar")) {
				PagamentoDTO p = new PagamentoDTO();
				p.setCliente(cliente.getId());
				p.setVendedor(vendedor.getId());
				float receb = Float.parseFloat(txtRecebido.getText());
				p.setValor(receb);
				ControllerPagamento cp = new ControllerPagamento();
				cp.pagar(p);
				ControllerCliente cc = new ControllerCliente();
				cliente.setSaldoDevedor(cliente.getSaldoDevedor() - receb);
				cc.editar(cliente);
				dispose();
			}else {
				dispose();
			}
		}
	}
}