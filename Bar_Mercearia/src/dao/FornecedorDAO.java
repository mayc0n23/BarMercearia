package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.FornecedorDTO;
import model.FornecedorJaCadastradoException;

public class FornecedorDAO implements IFornecedorDAO{
	private ConexaoPostgres conexao;
	private ResultSet rs;
	private PreparedStatement preparador;
	

	@Override
	/**
	 * Método que cadastra um fornecedor
	 */
	public void cadastrarFornecedor(FornecedorDTO f) throws FornecedorJaCadastradoException{
		conexao = ConexaoPostgres.getInstance();
		String sql = "insert into fornecedor(nome, cnpj, rua, numero, bairro, cidade) values (?, ?, ?, ?, ?, ?)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, f.getNome());
			preparador.setString(2, f.getCnpj());
			preparador.setString(3, f.getRua());
			preparador.setInt(4, f.getNumero());
			preparador.setString(5, f.getBairro());
			preparador.setString(6, f.getCidade());
			preparador.execute();
		}catch(SQLException e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		sql = "select id from fornecedor where cnpj = ?";
		int id = 0;
		try {
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, f.getCnpj());
			rs = preparador.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			}
		}catch(SQLException e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		sql = "insert into telefone_fornecedor (id_fornecedor, numero) values (?, ?)";
		try {
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, id);
			preparador.setString(2, f.getTelefone());
			preparador.execute();
		}catch(SQLException e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que deleta um fornecedor
	 */
	public void deletarFornecedor(FornecedorDTO f) {
		conexao =  ConexaoPostgres.getInstance();
		String sql = "delete from fornecedor where cnpj = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, f.getCnpj());
			preparador.executeUpdate();
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que edita os dados de um fornecedor
	 */
	public void editarFornecedor(FornecedorDTO f) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "update fornecedor set nome = ?, rua = ?, numero = ?, bairro = ?, cidade = ? where cnpj = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, f.getNome());
			preparador.setString(2, f.getRua());
			preparador.setInt(3, f.getNumero());
			preparador.setString(4, f.getBairro());
			preparador.setString(5, f.getCidade());
			preparador.setString(6, f.getCnpj());
			preparador.execute();
			sql = "select f.id from fornecedor as f inner join telefone_fornecedor as tf on (f.id = tf.id_fornecedor)";
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			int id = 0;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			sql = "update telefone_fornecedor set numero = ? where id_fornecedor= ?";
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, f.getTelefone());
			preparador.setInt(2, id);
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que lista todos os fornecedores
	 */
	public FornecedorDTO listarFornecedores() {
		FornecedorDTO fornecedores = new FornecedorDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select f.cnpj, f.nome, f.rua, f.numero, f.bairro, f.cidade, t.numero from fornecedor as f, telefone_fornecedor as t where f.id = t.id_fornecedor";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				FornecedorDTO fornecedor = new FornecedorDTO();
				fornecedor.setCnpj(rs.getString(1));
				fornecedor.setNome(rs.getString(2));
				fornecedor.setRua(rs.getString(3));
				fornecedor.setNumero(rs.getInt(4));
				fornecedor.setBairro(rs.getString(5));
				fornecedor.setCidade(rs.getString(6));
				fornecedor.setTelefone(rs.getString(7));
				fornecedores.addFornecedor(fornecedor);
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return fornecedores;
	}

	@Override
	/**
	 * Metodo que faz a busca por um fornecedor e retorna os dados do mesmo
	 */
	public FornecedorDTO pesquisarFornecedor(String cnpj) {
		FornecedorDTO fornecedor = new FornecedorDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select f.id, tf.numero from fornecedor as f, telefone_fornecedor as tf where (f.cnpj = ?) and (f.id = tf.id_fornecedor)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, cnpj);
			rs = preparador.executeQuery();
			int id = 0;
			String num = "";
			if(rs.next()) {
				id = rs.getInt(1);
				num = rs.getString(2);
			}
			fornecedor.setId(id);
			sql = "select cnpj, nome, rua, numero, bairro, cidade from fornecedor where cnpj = ?";
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, cnpj);
			rs = preparador.executeQuery();
			if(rs.next()) {
				fornecedor.setCnpj(rs.getString(1));
				fornecedor.setNome(rs.getString(2));
				fornecedor.setRua(rs.getString(3));
				fornecedor.setNumero(rs.getInt(4));
				fornecedor.setBairro(rs.getString(5));
				fornecedor.setCidade(rs.getString(6));
				fornecedor.setTelefone(num);
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return fornecedor;
	}
}