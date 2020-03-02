package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import controller.ControllerProduto;
import dto.ProdutoDTO;

public class RelatorioProdutosEmEstoque implements StrategyRelatorio{
	@Override
	public void gerarRelatorio(String mes, int id) {
		ControllerProduto cp = new ControllerProduto();
		ProdutoDTO relatorio = cp.produtosEstoque();
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("produtos_em_estoque.pdf"));
			doc.open();
			Paragraph p = new Paragraph("Produtos em estoque");
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			for(int i = 0; i < relatorio.getRelatorio().size(); i++) {
				String linha = "ID: " + relatorio.getRelatorio().get(i).get(0) + " - Produto: " + relatorio.getRelatorio().get(i).get(1) + " - Quantidade em estoque: " + relatorio.getRelatorio().get(i).get(2) + " - Preço: " + relatorio.getRelatorio().get(i).get(3);
				p = new Paragraph(linha);
				doc.add(p);
			}
			doc.close();
			Desktop desk = Desktop.getDesktop();
			desk.open(new File("produtos_em_estoque.pdf"));
		}catch(Exception e) {}
	}
}