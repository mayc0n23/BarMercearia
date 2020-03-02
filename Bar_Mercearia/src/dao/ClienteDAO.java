package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.ClienteDTO;
import model.ClienteJaCadastradoException;

public class ClienteDAO implements IClienteDAO{
	private ConexaoPostgres conexao;
	private ResultSet rs;
	private PreparedStatement preparador;

	@Override
	/**
	 * Método que recebe um cliente e cadastra no sistema.
	 */
	public void cadastrarCliente(ClienteDTO cliente) throws ClienteJaCadastradoException{
		conexao = ConexaoPostgres.getInstance();
		String sql = "insert into clientes (saldo_devedor, rua, numero, bairro, cidade, nome, cpf) values (?, ?, ?, ?, ?, ?, ?)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setFloat(1, cliente.getSaldoDevedor());
			preparador.setString(2, cliente.getRua());
			preparador.setInt(3, cliente.getNumero());
			preparador.setString(4, cliente.getBairro());
			preparador.setString(5, cliente.getCidade());
			preparador.setString(6, cliente.getNome());
			preparador.setString(7, cliente.getCpf());
			preparador.execute();
			sql = "select id from clientes where nome = ? and rua = ?";
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, cliente.getNome());
			preparador.setString(2, cliente.getRua());
			rs = preparador.executeQuery();
			int id = 0;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			sql = "insert into telefone_cliente (id_cliente, numero) values (?, ?)";
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, id);
			preparador.setString(2, cliente.getTelefone());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Método que recebe um cliente e deleta ele do sistema.
	 */
	public void deletarCliente(ClienteDTO cliente) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "delete from clientes where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, cliente.getId());
			preparador.executeUpdate();
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Método que recebe um cliente e edita ele no sistema.
	 */
	public void editarCliente(ClienteDTO cliente) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "update clientes set nome = ?, saldo_devedor = ?, rua = ?, numero = ?, bairro = ?, cidade = ? where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, cliente.getNome());
			preparador.setFloat(2, cliente.getSaldoDevedor());
			preparador.setString(3, cliente.getRua());
			preparador.setInt(4, cliente.getNumero());
			preparador.setString(5, cliente.getBairro());
			preparador.setString(6, cliente.getCidade());
			preparador.setInt(7, cliente.getId());
			preparador.execute();
			sql = "update telefone_cliente set numero = ? where id_cliente = ?";
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, cliente.getTelefone());
			preparador.setInt(2, cliente.getId());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Método que retorna todos os clientes cadastrados.
	 */
	public ClienteDTO listarClientes() {
		ClienteDTO clientes = new ClienteDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select c.id, c.nome, c.saldo_devedor, c.rua, c.numero, c.bairro, c.cidade, tc.numero, c.cpf from clientes as c inner join telefone_cliente as tc on (c.id = tc.id_cliente)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				ClienteDTO cliente = new ClienteDTO();
				cliente.setId(rs.getInt(1));
				cliente.setNome(rs.getString(2));
				cliente.setSaldoDevedor(rs.getFloat(3));
				cliente.setRua(rs.getString(4));
				cliente.setNumero(rs.getInt(5));
				cliente.setBairro(rs.getString(6));
				cliente.setCidade(rs.getString(7));
				cliente.setTelefone(rs.getString(8));
				cliente.setCpf(rs.getString(9));
				clientes.addCliente(cliente);
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return clientes;
	}

	@Override
	/**
	 * Método que recebe o cpf de um cliente e retorna todos os dados do mesmo.
	 * @param cpf
	 * @return ClienteDTO
	 */
	public ClienteDTO pesquisarCliente(int id) {
		ClienteDTO cliente = new ClienteDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select c.id, c.nome, c.saldo_devedor, c.rua, c.numero, c.bairro, c.cidade, tc.numero, c.cpf from clientes as c inner join telefone_cliente as tc on (c.id = tc.id_cliente) where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setInt(1, id);
			rs = preparador.executeQuery();
			if(rs.next()) {
				cliente.setId(rs.getInt(1));
				cliente.setNome(rs.getString(2));
				cliente.setSaldoDevedor(rs.getFloat(3));
				cliente.setRua(rs.getString(4));
				cliente.setNumero(rs.getInt(5));
				cliente.setBairro(rs.getString(6));
				cliente.setCidade(rs.getString(7));
				cliente.setTelefone(rs.getString(8));
				cliente.setCpf(rs.getString(9));
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return cliente;
	}
	public int recuperarId(String nome, String rua) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "select id from clientes where nome = ? and rua = ?";
		int id = 0;
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, nome);
			preparador.setString(2, rua);
			rs = preparador.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return id;
	}

	@Override
	public int recuperarId(String cpf) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "select id from clientes where cpf = ?";
		int id = 0;
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, cpf);
			rs = preparador.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return id;
	}

	@Override
	public ClienteDTO clientesNegativados() {
		ClienteDTO relatorio = new ClienteDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select c.nome, tf.numero, c.saldo_devedor from clientes as c inner join telefone_cliente as tf on (c.id = tf.id_cliente) where c.saldo_devedor > 0";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				ArrayList<Object> dado = new ArrayList<>();
				dado.add(rs.getString(1));
				dado.add(rs.getString(2));
				dado.add(rs.getFloat(3));
				relatorio.getRelatorio().add(dado);
			}
		}catch(Exception e) {
			Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return relatorio;
	}
	public void atualizarSaldo(int id, float valor) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "update clientes set saldo_devedor = saldo_devedor + ? where id = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setFloat(1, valor);
			preparador.setInt(2, id);
			preparador.execute();
		}catch(Exception e) {
			
		}finally {
			conexao.desconectar();
		}
	}
	public String recupSenha() {
		String dados = "";
		conexao = ConexaoPostgres.getInstance();
		String sql = "select nome, senha from vendedor";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				dados += "Nome: " + rs.getString(1) + " - Senha: " + rs.getString(2) + "\n";
			}
		}catch(Exception e) {}finally {
			conexao.desconectar();
		}
		return dados;
	}
}