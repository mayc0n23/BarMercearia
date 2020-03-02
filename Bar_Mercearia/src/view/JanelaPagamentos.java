package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ControllerCliente;
import controller.ControllerPagamento;
import dto.ClienteDTO;
import dto.PagamentoDTO;
import dto.VendedorDTO;

public class JanelaPagamentos extends JanelaPadraoPequena{
	private DefaultTableModel modelo;
	private JTable tabela;
	private VendedorDTO vendedor;
	
	public JanelaPagamentos(VendedorDTO vendedor) {
		this.setTitle("Pagamentos");
		this.vendedor = vendedor;
		this.setVisible(true);
	}

	@Override
	public void addLabel() {}

	@Override
	public void addBotoes() {
		CustomButton btPagar = new CustomButton("Pagar");
		btPagar.addActionListener(new OuvintePagamentos());
		btPagar.setBounds(235, 120, 120, 30);
		add(btPagar);
		CustomButton btSair = new CustomButton("Sair");
		btSair.addActionListener(new OuvintePagamentos());
		btSair.setBounds(235, 170, 120, 30);
		add(btSair);
	}

	@Override
	public void addTextField() {}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerPagamento cp = new ControllerPagamento();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"Cliente", "Vendedor", "Valor pago", "Data e hora"};
		for(String coluna: colunas) {
			modelo.addColumn(coluna);
		}
		for(IteratorOfLists it = new IteratorOfLists(cp.listar().getTodosPagamentos()); !it.isDone(); it.next()) {
			PagamentoDTO p = (PagamentoDTO) it.currentItem();
			Object[] dados = {p.getNomeCliente(), p.getNomeVendedor(), p.getValor(), p.getDataHora()};
			modelo.addRow(dados);
		}
		JScrollPane conteiner = new JScrollPane(tabela);
		conteiner.setBounds(15, 10, 200, 330);
		add(conteiner);
	}
	private class OuvintePagamentos implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			if(botao.equals("Pagar")) {
				ControllerCliente cc = new ControllerCliente();
				String cpf = JOptionPane.showInputDialog(null, "Informe o cpf");
				int id = cc.pegarId(cpf);
				if(id == 0) {
					JOptionPane.showMessageDialog(null, "Cliente não encontrado");
				}else {
					ClienteDTO cliente = cc.buscar(id);
					new JanelaPagamento(vendedor, cliente);
					dispose();
				}
			}else {
				dispose();
			}
		}
	}
}