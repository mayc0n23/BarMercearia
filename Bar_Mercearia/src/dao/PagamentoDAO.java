package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.PagamentoDTO;

public class PagamentoDAO implements IPagamentoDAO{
	private ConexaoPostgres conexao;
	private ResultSet rs;
	private PreparedStatement preparador;

	@Override
	/**
	 * Metodo que cadastra o pagamento de um cliente
	 */
	public void pagar(PagamentoDTO p) {
		conexao = ConexaoPostgres.getInstance();
		String sql = "insert into pagamento (valor_pagamento, data_hora_pagamento, id_cliente, id_vendedor) values (?, ?, ?, ?)";
		Timestamp dataHora = new Timestamp(System.currentTimeMillis());
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setFloat(1, p.getValor());
			preparador.setTimestamp(2, dataHora);
			preparador.setInt(3, p.getCliente());
			preparador.setInt(4, p.getVendedor());
			preparador.execute();
		}catch(Exception e) {
			Logger.getLogger(PagamentoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		finally {
			conexao.desconectar();
		}
	}

	@Override
	/**
	 * Metodo que lista todos os pagamentos realizados
	 */
	public PagamentoDTO listarPagamentos() {
		PagamentoDTO pag = new PagamentoDTO();
		conexao = ConexaoPostgres.getInstance();
		String sql = "select c.nome, v.nome, p.valor_pagamento, p.data_hora_pagamento from pagamento as p inner join clientes as c on (p.id_cliente = c.id) inner join vendedor as v on (p.id_vendedor = v.id)";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			rs = preparador.executeQuery();
			while(rs.next()) {
				PagamentoDTO p = new PagamentoDTO();
				p.setNomeCliente(rs.getString(1));
				p.setNomeVendedor(rs.getString(2));
				p.setValor(rs.getFloat(3));
				p.setDataHora(rs.getTimestamp(4));
				pag.addPagamento(p);
			}
		}catch(Exception e) {
			Logger.getLogger(PagamentoDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return pag;
	}
}