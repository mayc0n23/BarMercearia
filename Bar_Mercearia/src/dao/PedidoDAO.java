package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.ClienteDTO;
import dto.PedidoDTO;
import dto.VendedorDTO;

public class PedidoDAO implements IPedidoDAO{
	private ConexaoPostgres conexao;
	private ResultSet rs;
	private PreparedStatement preparador;
	
	@Override
	/**
	 * Metodo que cadastra um pedido no sistema
	 */
	public void cadastrarPedido(PedidoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "insert into pedido (data_pedido, valor, id_vendedor, id_cliente) values (?, ?, ?, ?)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setDate(1, p.getData());
			preparador.setFloat(2, p.getValor());
			preparador.setInt(3, p.getVendedor().getId());
			preparador.setInt(4, p.getCliente().getId());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que deleta um pedido do sistema
	 */
	public void deletarPedido(PedidoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "delete from pedido where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, p.getId());
			preparador.executeUpdate();
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que edita um pedido no sistema, falta terminar
	 */
	public void editarPedido(PedidoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "update from pedido set valor = ? where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setFloat(1, p.getValor());
			preparador.setInt(2, p.getId());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que lista os pedidos no sistema
	 */
	public PedidoDTO listarPedidos() {
		PedidoDTO pedidos = new PedidoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select p.id, p.data_pedido, p.valor, c.nome, v.nome, v.id from pedido as p inner join cliente as c on (c.id = p.id_cliente) inner join vendedor as v on(p.id_vendedor = v.id)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				PedidoDTO pedido = new PedidoDTO();
				pedido.setId(rs.getInt(1));
				pedido.setData(rs.getDate(2));
				pedido.setValor(rs.getFloat(3));
				VendedorDTO vendedor = new VendedorDTO();
				vendedor.setNome(rs.getString(5));
				vendedor.setId(rs.getInt(6));
				pedido.setVendedor(vendedor);
				ClienteDTO cliente = new ClienteDTO();
				cliente.setNome(rs.getString(4));
				pedido.setCliente(cliente);
				pedidos.addPedido(pedido);
			}
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return pedidos;
	}

	@Override
	public void pedidoProduto(int idProduto, int idPedido, int qtd) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "insert into pedido_produto (id_pedido, id_produto, quantidade_comprada) values (?, ?, ?)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, idPedido);
			preparador.setInt(2, idProduto);
			preparador.setInt(3, qtd);
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	public int recuperarId() {
		
		int id = 0;
		conexao = ConexaoPostgres.getInstance();
		String sql = "select id from pedido where id = (select max(id) from pedido)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			}	
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return id;
	}

	@Override
	public PedidoDTO relatorioLucro(String mes, int id) {
		PedidoDTO relatorio = new PedidoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select p.nome ,sum((p.preco_unidade - cp.preco_compra)) as lucro from produto as p inner join compra_produto as cp on (p.id = cp.id_produto) inner join pedido_produto as pp on (p.id = pp.id_produto) inner join pedido as pe on (pp.id_pedido = pe.id) where pe.data_pedido ";
		switch(mes) {
		case "Janeiro":
			sql += "between '2019-01-01' and '2019-01-31'";
			break;
		case "Fevereiro":
			sql += "between '2019-02-01' and '2019-02-27'";
			break;
		case "Março":
			sql += "between '2019-03-01' and '2019-03-31'";
			break;
		case "Abril":
			sql += "between '2019-04-01' and '2019-04-30'";
			break;
		case "Maio":
			sql += "between '2019-05-01' and '2019-05-31'";
			break;
		case "Junho":
			sql += "between '2019-06-01' and '2019-06-30'";
			break;
		case "Julho":
			sql += "between '2019-07-01' and '2019-07-31'";
			break;
		case "Agosto":
			sql += "between '2019-08-01' and '2019-08-31'";
			break;
		case "Setembro":
			sql += "between '2019-09-01' and '2019-09-30'";
			break;
		case "Outubro":
			sql += "between '2019-10-01' and '2019-10-31'";
			break;
		case "Novembro":
			sql += "between '2019-11-01' and '2019-11-30'";
			break;
		case "Dezembro":
			sql += "between '2019-12-01' and '2019-12-31'";
			break;
		}
		sql += " and pe.id_vendedor = ? group by p.nome";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, id);
			rs = preparador.executeQuery();
			while(rs.next()) {
				ArrayList<Object> dado = new ArrayList<>();
				dado.add(rs.getString(1));
				dado.add(rs.getFloat(2));
				relatorio.getRelatorio().add(dado);
			}
		}catch(Exception e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return relatorio;
	}
}