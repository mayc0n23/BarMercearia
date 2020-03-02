package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ControllerCliente;
import controller.ControllerPagamento;
import controller.ControllerPedido;
import controller.ControllerProduto;
import dto.ClienteDTO;
import dto.PagamentoDTO;
import dto.PedidoDTO;
import dto.ProdutoDTO;
import dto.VendedorDTO;

public class JanelaPedidoBar extends JanelaPadraoMedia{
	private DefaultTableModel modeloProduto;
	private JTable tabelaProduto;
	private DefaultTableModel modeloCarrinho;
	private JTable tabelaCarrinho;
	private int mesa;
	private CustomLabel lblValor;
	private JanelaBar janela;
	
	public JanelaPedidoBar(int m, JanelaBar janela) {
		this.mesa = m;
		this.janela = janela;
		this.setTitle("Pedido Bar");
		inserirDados();
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblProduto = new CustomLabel("Produtos");
		CustomLabel lblCarrinho = new CustomLabel("Carrinho");
		lblProduto.setBounds(100, 10, 100, 30);
		lblCarrinho.setBounds(380, 10, 100, 30);
		add(lblProduto);
		add(lblCarrinho);
		CustomLabel lblTotal = new CustomLabel("Total:");
		lblTotal.setBounds(310, 310, 80, 30);
		add(lblTotal);
		lblValor = new CustomLabel("0");
		lblValor.setBounds(380, 310, 100, 30);
		add(lblValor);
	}

	@Override
	public void addBotoes() {
		CustomButton btFinalizar = new CustomButton("Finalizar pedido");
		btFinalizar.setBounds(310, 350, 200, 30);
		btFinalizar.addActionListener(new OuvintePedidoBar());
		add(btFinalizar);
		JButton btInserir = new JButton(",");
		btInserir.setIcon(new ImageIcon(getClass().getResource("/images/icons8-inserir-coluna-à-direita-30.png")));
		btInserir.addActionListener(new OuvintePedidoBar());
		btInserir.setBounds(270, 120, 34, 34);
		btInserir.setToolTipText("Inserir no carrinho");
		add(btInserir);
		JButton btRemover = new JButton(".");
		btRemover.setIcon(new ImageIcon(getClass().getResource("/images/icons8-inserir-coluna-esquerda-30.png")));
		btRemover.addActionListener(new OuvintePedidoBar());
		btRemover.setBounds(270, 165, 34, 34);
		btRemover.setToolTipText("Remover do carrinho");
		add(btRemover);
	}

	@Override
	public void addTextField() {}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {
		ControllerProduto cp = new ControllerProduto();
		modeloProduto = new DefaultTableModel();
		tabelaProduto = new JTable(modeloProduto);
		String[] colunas = {"Id", "Nome", "Preço", "Estoque"};
		for(String s: colunas) {
			modeloProduto.addColumn(s);
		}
		for(IteratorOfLists it = new IteratorOfLists(cp.listar().getListaProdutos()); !it.isDone(); it.next()) {
			ProdutoDTO p = (ProdutoDTO) it.currentItem();
			Object[] dados = {p.getId(), p.getNome(), p.getPrecoUnidade(), p.getQntdEstoque()};
			modeloProduto.addRow(dados);
		}
		JScrollPane conteiner = new JScrollPane(tabelaProduto);
		conteiner.setBounds(10, 50, 250, 330);
		add(conteiner);
	}
	public void inserirDados() {
		ManterMesas mm = new ManterMesas();
		Mesa m = mm.recuperarMesa(mesa);
		float total = 0;
		modeloCarrinho = new DefaultTableModel();
		tabelaCarrinho = new JTable(modeloCarrinho);
		String[] colunass = {"Id", "Nome", "Qtd", "Total"};
		for(String s: colunass) {
			modeloCarrinho.addColumn(s);
		}
		for(int i = 0; i < m.getProdutos().size(); i++) {
			ProdutoDTO p = (ProdutoDTO) m.getProdutos().get(i).get(0);
			int qtd = (int) m.getProdutos().get(i).get(1);
			total += p.getPrecoUnidade() * qtd;
			float l = p.getPrecoUnidade() * qtd;
			Object[] dados = {p.getId(), p.getNome(), qtd, l};
			modeloCarrinho.addRow(dados);
		}
		JScrollPane contein = new JScrollPane(tabelaCarrinho);
		contein.setBounds(320, 50, 250, 250);
		add(contein);
		lblValor.setText(Float.toString(total));
	}
	private class OuvintePedidoBar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ManterMesas mm = new ManterMesas();
			ControllerProduto cp = new ControllerProduto();
			String botao = e.getActionCommand();
			int linha = tabelaProduto.getSelectedRow();
			if(botao.equals(",")) {
				if(linha >= 0) {
					int qtdDesejada = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantidade que deseja"));
					int qtdEstoque = (int) modeloProduto.getValueAt(linha, 3);
					if(qtdDesejada > qtdEstoque) {
						JOptionPane.showMessageDialog(null, "Quantidade inválida");
					}else {
						float preco = (float)modeloProduto.getValueAt(linha, 2);
						float total = preco * qtdDesejada;
						String nome = (String) modeloProduto.getValueAt(linha, 1);
						ProdutoDTO p = cp.buscar(nome);
						ArrayList<Object> produto = new ArrayList<>();
						produto.add(p);
						produto.add(qtdDesejada);
						for(int i = 0; i < mm.getMesas().size(); i++) {
							if(mm.getMesas().get(i).getNumMesa() == mesa) {
								mm.getMesas().get(i).add(produto);
								mm.getMesas().get(i).setStatus("Ocupado");
								break;
							}
						}
						int tamTabelaMesa = janela.getTabela().getColumnCount();
						for(int i = 0; i < tamTabelaMesa; i++) {
							int numMesaLa = (int) janela.getModelo().getValueAt(i, 0);
							if(numMesaLa == mesa) {
								janela.getModelo().setValueAt("Ocupado", i, 1);
								janela.repaint();
								break;
							}
						}
						Object[] dados = {p.getId(), p.getNome(), qtdDesejada, total};
						modeloCarrinho.addRow(dados);
						float precoLbl = Float.parseFloat(lblValor.getText());
						precoLbl += total;
						lblValor.setText(Float.toString(precoLbl));
						mm.salvarMesas(mm.getMesas());
					}
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
			}else if(botao.equals(".")) {
				int linhaa = tabelaCarrinho.getSelectedRow();
				if(linhaa >= 0) {
					float precoLinha = (float) modeloCarrinho.getValueAt(linhaa, 3);
					modeloCarrinho.removeRow(linhaa);
					for(int i = 0; i < mm.getMesas().size(); i++) {
						if(mm.getMesas().get(i).getNumMesa() == mesa) {
							mm.getMesas().remove(i);
							break;
						}
					}
					mm.salvarMesas(mm.getMesas());
					float valor = Float.parseFloat(lblValor.getText());
					valor -= precoLinha;
					lblValor.setText(Float.toString(valor));
				}else {
					JOptionPane.showMessageDialog(null, "Nenhum produto está selecionado");
				}
			}else {
				if(Float.parseFloat(lblValor.getText()) != 0) {
					ControllerCliente cc = new ControllerCliente();
					VendedorDTO v = new VendedorDTO();
					v.setId(1);
					v.setNome("Edcarlos");
					int idCliente = cc.pegarId(JOptionPane.showInputDialog(null, "Digite o cpf do cliente"));
					if(idCliente == 0) {
						JOptionPane.showMessageDialog(null, "Cliente não cadastrado");
					}else {
						PedidoDTO pedido = new PedidoDTO();
						ClienteDTO cliente = cc.buscar(idCliente);
						cliente.setId(idCliente);
						pedido.setCliente(cliente);
						pedido.setVendedor(v);
						pedido.setValor(Float.parseFloat(lblValor.getText()));
						ControllerPedido ccp = new ControllerPedido();
						ccp.cadastrar(pedido);
						int idPedido = ccp.pegarId();
						int qtdLinhas = tabelaCarrinho.getRowCount();
						for(int i = 0; i < qtdLinhas; i++) {
							int idProduto = (int) modeloCarrinho.getValueAt(i, 0);
							int qtd = (int) modeloCarrinho.getValueAt(i, 2);
							ccp.inserirPedido(idProduto, idPedido, qtd);
						}
						String[] op = {"Pagar agora", "Deixar fiado"};
						String res = (String) JOptionPane.showInputDialog(null, "Opções de pagamento", "Pagamento", JOptionPane.QUESTION_MESSAGE,null, op, op[0]);
						if(res.equals("Pagar agora")) {
							ControllerPagamento cpp = new ControllerPagamento();
							PagamentoDTO pag = new PagamentoDTO();
							pag.setCliente(idCliente);
							pag.setVendedor(v.getId());
							pag.setValor(pedido.getValor());
							cpp.pagar(pag);
						}else {
							cc.atualizarSaldo(idCliente, pedido.getValor());
						}
						NotaFiscal nf = new NotaFiscal();
						ArrayList<ProdutoDTO> produtos = new ArrayList<>();
						for(int i = 0; i < mm.getMesas().get(mesa).getProdutos().size(); i++) {
							ProdutoDTO p = (ProdutoDTO) mm.getMesas().get(mesa).getProdutos().get(0).get(i);
							produtos.add(p);
						}
						nf.gerarNota(cliente,produtos, pedido.getValor());
						for(int i = 0; i < mm.getMesas().size(); i++) {
							if(mm.getMesas().get(i).getNumMesa() == mesa) {
								mm.getMesas().get(i).setStatus("Disponivel");
								mm.getMesas().get(i).setProdutos(new ArrayList<ArrayList<Object>>());
								mm.salvarMesas(mm.getMesas());
								break;
							}
						}
						int tamTabelaMesa = janela.getTabela().getColumnCount();
						for(int i = 0; i < tamTabelaMesa; i++) {
							int numMesaLa = (int) janela.getModelo().getValueAt(i, 0);
							if(numMesaLa == mesa) {
								janela.getModelo().setValueAt("Disponivel", i, 1);
								janela.repaint();
								break;
							}
						}
						dispose();
					}
				}
			}
		}
	}
}