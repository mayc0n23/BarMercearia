package controller;

import dto.PagamentoDTO;
import model.Pagamento;

public class ControllerPagamento {
	public void pagar(PagamentoDTO p) {
		Pagamento pa = new Pagamento();
		pa.pagar(p);
	}
	public PagamentoDTO listar() {
		Pagamento pa = new Pagamento();
		return pa.listarPagamentos();
	}
}