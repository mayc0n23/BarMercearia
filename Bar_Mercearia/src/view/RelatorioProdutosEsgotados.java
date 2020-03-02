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

public class RelatorioProdutosEsgotados implements StrategyRelatorio{
	@Override
	public void gerarRelatorio(String mes, int id) {
		ControllerProduto cp = new ControllerProduto();
		ProdutoDTO relatorio = cp.produtosEsgotados();
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("produtos_em_falta.pdf"));
			doc.open();
			Paragraph p = new Paragraph("Produtos em falta");
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			for(int i = 0; i < relatorio.getRelatorio().size(); i++) {
				String linha = "Produto: " + relatorio.getRelatorio().get(i).get(0) + " - Preço da compra: " + relatorio.getRelatorio().get(i).get(1);
				p = new Paragraph(linha);
				doc.add(p);
			}
			doc.close();
			Desktop desk = Desktop.getDesktop();
			desk.open(new File("produtos_em_falta.pdf"));
		}catch(Exception e) {}
	}
}