package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ControllerFornecedor;
import dto.FornecedorDTO;

public class JanelaFornecedores extends JanelaPadraoMedia{
	private DefaultTableModel modelo;
	private JTable tabela;
	
	public JanelaFornecedores() {
		this.setTitle("Fornecedores");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {}

	@Override
	public void addBotoes() {
		CustomButton btCadastrar = new CustomButton("Cadastrar");
		btCadastrar.setToolTipText("Cadastrar fornecedor");
		btCadastrar.setBounds(440, 40, 120, 30);
		btCadastrar.addActionListener(new OuvinteFornecedores(this));
		add(btCadastrar);
		CustomButton btEditar = new CustomButton("Editar");
		btEditar.setToolTipText("Editar fornecedor");
		btEditar.setBounds(440, 100, 120, 30);
		btEditar.addActionListener(new OuvinteFornecedores(this));
		add(btEditar);
		CustomButton btExcluir = new CustomButton("Excluir");
		btExcluir.setToolTipText("Excluir fornecedor");
		btExcluir.setBounds(440, 160, 120, 30);
		btExcluir.addActionListener(new OuvinteFornecedores(this));
		add(btExcluir);
	}

	@Override
	public void addTextField() {}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerFornecedor cf = new ControllerFornecedor();
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		String[] colunas = {"Nome", "CNPJ", "Rua", "Número", "Bairro", "Cidade"," Telefone"};
		for(String coluna: colunas) {
			modelo.addColumn(coluna);
		}
		for(IteratorOfLists it = new IteratorOfLists(cf.listar().getListaFornecedor()); !it.isDone(); it.next()) {
			FornecedorDTO f = (FornecedorDTO) it.currentItem();
			Object[] dados = {f.getNome(), f.getCnpj(), f.getRua(), f.getNumero(), f.getBairro(), f.getCidade(), f.getTelefone()};
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
	private class OuvinteFornecedores implements ActionListener{
		private JanelaFornecedores janela;
		
		public OuvinteFornecedores(JanelaFornecedores janela) {
			this.janela = janela;
		}
		
		public void actionPerformed(ActionEvent e) {
			String botao = e.getActionCommand();
			switch(botao) {
			case "Cadastrar":
				new JanelaCadastroFornecedor(janela);
				break;
			case "Excluir":
				int linha = tabela.getSelectedRow();
				if(linha >= 0) {
					FornecedorDTO f = new FornecedorDTO();
					String cnpj = (String) modelo.getValueAt(linha, 1);
					f.setCnpj(cnpj);
					ControllerFornecedor cf = new ControllerFornecedor();
					cf.deletar(f);
					modelo.removeRow(linha);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum fornecedor foi selecionado");
				}
				break;
			case "Editar":
				linha = tabela.getSelectedRow();
				if(linha >= 0) {
					ControllerFornecedor cf = new ControllerFornecedor();
					String cnpj = (String) modelo.getValueAt(linha, 1);
					FornecedorDTO f = cf.buscar(cnpj);
					new JanelaEditarFornecedor(f, janela, linha);
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum fornecedor foi selecionado");
				}
				break;
			}
		}
	}
}