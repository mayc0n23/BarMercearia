package dao;

import dto.PagamentoDTO;

public interface IPagamentoDAO {
	public void pagar(PagamentoDTO p);
	public PagamentoDTO listarPagamentos();
}