package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import controller.ControllerPedido;
import dto.PedidoDTO;

public class RelatorioLucroMesBar implements StrategyRelatorio{
	@Override
	public void gerarRelatorio(String mes, int id) {
		ControllerPedido cp = new ControllerPedido();
		PedidoDTO relatorio = cp.pegarRelatorio(mes, id);
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		try {
			float total = 0;
			PdfWriter.getInstance(doc, new FileOutputStream("lucro_mes_bar.pdf"));
			doc.open();
			Paragraph p = new Paragraph("Lucro do mês de " + mes + " do Bar");
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			p = new Paragraph("");
			doc.add(p);
			for(int i = 0; i < relatorio.getRelatorio().size(); i++) {
				total += (float) relatorio.getRelatorio().get(i).get(1);
				String linha = "Produto: " + relatorio.getRelatorio().get(i).get(0) + " - Lucro: " + relatorio.getRelatorio().get(i).get(1);
				p = new Paragraph(linha);
				doc.add(p);
			}
			p = new Paragraph("");
			doc.add(p);
			p = new Paragraph("Lucro total: " + total);
			doc.add(p);
			doc.close();
			Desktop desk = Desktop.getDesktop();
			try {
				desk.open(new File("lucro_mes_bar.pdf"));
			}catch(Exception e) {}
		}catch(FileNotFoundException | DocumentException e) {}
	}
}