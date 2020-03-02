package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import dto.VendedorDTO;

public class VendedorDAO implements IVendedorDAO{
	private ConexaoPostgres conexao;
	private ResultSet rs;
	private PreparedStatement preparador;

	@Override
	public VendedorDTO recuperarVendedor(String nome, String senha) {
		VendedorDTO v = null;
		conexao = ConexaoPostgres.getInstance();
		String sql = "select id ,nome from vendedor where nome = ? and senha = ?";
		try {
			conexao.conectar();
			preparador = conexao.getConnection().prepareStatement(sql);
			preparador.setString(1, nome);
			preparador.setString(2, senha);
			rs = preparador.executeQuery();
			if(rs.next()) {
				v = new VendedorDTO();
				v.setId(rs.getInt(1));
				v.setNome(rs.getString(2));
			}
		}catch(Exception e) {
			Logger.getLogger(VendedorDAO.class.getName()).log(Level.SEVERE, null, e);
		}finally {
			conexao.desconectar();
		}
		return v;
	}
}