package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.ProdutoDTO;

public class ProdutoDAO implements IProdutoDAO{
	private ConexaoPostgres conexao;
	private ResultSet rs;
	private PreparedStatement preparador;

	@Override
	/**
	 * Metodo que cadastra um produto no sistema
	 */
	public void cadastrarProduto(ProdutoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "insert into produto (nome, quantidade_estoque, unidade_medida, preco_unidade) values (?, ?, ?, ?)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, p.getNome());
			preparador.setInt(2, 0);
			preparador.setString(3, p.getUnidadeMedida());
			preparador.setFloat(4, p.getPrecoUnidade());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que deleta um produto do sistema
	 */
	public void deletarProduto(ProdutoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "delete from produto where nome = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, p.getNome());
			preparador.executeUpdate();
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que edita um produto no banco
	 */
	public void editarProduto(ProdutoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "update produto set nome = ?, unidade_medida = ?, preco_unidade = ? where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(2, p.getUnidadeMedida());
			preparador.setFloat(3, p.getPrecoUnidade());
			preparador.setInt(4, p.getId());
			preparador.setString(1, p.getNome());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que lista todos os produtos
	 */
	public ProdutoDTO listarProdutos() {
		ProdutoDTO produtos = new ProdutoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select * from produto";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				ProdutoDTO produto = new ProdutoDTO();
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setQntdEstoque(rs.getInt(3));
				produto.setUnidadeMedida(rs.getString(4));
				produto.setPrecoUnidade(rs.getFloat(5));
				produtos.addProduto(produto);
			}
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return produtos;
	}

	@Override
	/**
	 * Metodo que faz a busca por um produto e o retorna
	 */
	public ProdutoDTO buscarProduto(String nome) {
		ProdutoDTO produto = new ProdutoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select * from produto where nome = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, nome);
			rs = preparador.executeQuery();
			if(rs.next()) {
				produto.setId(rs.getInt(1));
				produto.setNome(rs.getString(2));
				produto.setQntdEstoque(rs.getInt(3));
				produto.setUnidadeMedida(rs.getString(4));
				produto.setPrecoUnidade(rs.getFloat(5));
			}
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return produto;
	}

	@Override
	public void cadastrarLote(int id, String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		conexao = ConexaoPostgres.getInstance();
		try {
			java.util.Date datta = sdf.parse(data);
			java.sql.Date datte = new java.sql.Date(datta.getTime());
			conexao.conectar();
			String sql = "insert into lote (data_validade, id_produto) values (?, ?)";
			try {
				preparador = conexao.getConnection().prepareStatement(sql);
				preparador.setDate(1, datte);
				preparador.setInt(2, id);
				preparador.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				conexao.desconectar();
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void compraProduto(int idProduto, int idForn, float precoCompra, String dataCompra, int qtd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		conexao = ConexaoPostgres.getInstance();
		try {
			java.util.Date datta = sdf.parse(dataCompra);
			java.sql.Date datte = new java.sql.Date(datta.getTime());
			conexao.conectar();
			String sql = "insert into compra_produto (id_produto, id_fornecedor, preco_compra, data_compra, quantidade_comprada) values (?, ?, ?, ?, ?)";
			try {
				preparador = conexao.getConnection().prepareStatement(sql);
				preparador.setInt(1, idProduto);
				preparador.setInt(2, idForn);
				preparador.setFloat(3, precoCompra);
				preparador.setDate(4, datte);
				preparador.setInt(5, qtd);
				preparador.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				conexao.desconectar();
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ProdutoDTO produtosEsgotados() {
		ProdutoDTO relatorio = new ProdutoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select p.nome, avg(cp.preco_compra) from produto as p inner join compra_produto as cp on (p.id = cp.id_produto) where p.quantidade_estoque = 0 group by p.id;";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				ArrayList<Object> dado = new ArrayList<>();
				dado.add(rs.getString(1));
				dado.add(rs.getFloat(2));
				relatorio.getRelatorio().add(dado);
			}
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return relatorio;
	}

	@Override
	public ProdutoDTO produtosEstoque() {
		ProdutoDTO relatorio = new ProdutoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select id, nome, quantidade_estoque, preco_unidade from produto where quantidade_estoque > 0";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				ArrayList<Object> dado = new ArrayList<>();
				dado.add(rs.getInt(1));
				dado.add(rs.getString(2));
				dado.add(rs.getInt(3));
				dado.add(rs.getFloat(4));
				relatorio.getRelatorio().add(dado);
			}
		}catch(Exception e) {
			Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return relatorio;
	}
}