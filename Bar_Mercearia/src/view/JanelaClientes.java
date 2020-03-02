package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ControllerCliente;
import dto.ClienteDTO;

public class JanelaClientes extends JanelaPadraoMedia{
	private DefaultTableModel modelo;
	private JTable tabela;

	public JanelaClientes() {
		this.setTitle("Clientes");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {}
	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Cadastrar");
		btCadastrar.setToolTipText("Cadastrar cliente");
		btCadastrar.setBounds(440, 40, 120, 30);
		btCadastrar.addActionListener(new OuvinteClientes(this));
		add(btCadastrar);
		CustomButton btEditar = new CustomButton("Editar");
		btEditar.setToolTipText("Editar cliente");
		btEditar.addActionListener(new OuvinteClientes(this));
		btEditar.setBounds(440, 100, 120, 30);
		add(btEditar);
		CustomButton btExcluir = new CustomButton("Excluir");
		btExcluir.setToolTipText("Excluir cliente");
		btExcluir.setBounds(440, 160, 120, 30);
		btExcluir.addActionListener(new OuvinteClientes(this));
		add(btExcluir);
	}
	@Override
	public void addTextField() {}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerCliente cc = new ControllerCliente();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"Id" ,"Nome", "Saldo devedor", "Rua", "Número", "Bairro", "Cidade", "Telefone"};
		for(String coluna: colunas) {
			modelo.addColumn(coluna);
		}
		for(IteratorOfLists it = new IteratorOfLists(cc.listar().getTodosClientes()); !it.isDone(); it.next()) {
			ClienteDTO cliente = (ClienteDTO) it.currentItem();
			Object[] dados = {cliente.getId(), cliente.getNome(), cliente.getSaldoDevedor(), cliente.getRua(), cliente.getNumero()
					, cliente.getBairro(), cliente.getCidade(), cliente.getTelefone()};
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
	private class OuvinteClientes implements ActionListener {
		private JanelaClientes janela;
		
		public OuvinteClientes(JanelaClientes janela) {
			this.janela = janela;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerCliente cc = new ControllerCliente();
			String botao = e.getActionCommand();
			switch(botao) {
			case "Excluir":
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					int id = (int) modelo.getValueAt(linha, 0);
				ClienteDTO cliente = new ClienteDTO();
				cliente.setId(id);
				cc.deletar(cliente);
				modelo.removeRow(linha);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado");
				}
				break;
			case "Cadastrar":
				new JanelaCadastroCliente(janela);
				break;
			case "Editar":
				linha = tabela.getSelectedRow();
				if(linha >= 0) {
					int id = (int) modelo.getValueAt(linha, 0);
					ClienteDTO cliente = cc.buscar(id);
					new JanelaEditarCliente(janela, cliente, linha);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado");
				}
			}
		}
	}
}