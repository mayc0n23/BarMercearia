package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import controller.ControllerCliente;
import dto.ClienteDTO;

public class RelatorioSaldoDevedor implements StrategyRelatorio{
	@Override
	public void gerarRelatorio(String mes, int id) {
		ControllerCliente cc = new ControllerCliente();
		ClienteDTO relatorio = cc.relatorioCliente();
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("clientes_saldo_devedor.pdf"));
			doc.open();
			Paragraph p = new Paragraph("Clientes com saldo devedor");
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			p = new Paragraph("");
			doc.add(p);
			for(int i = 0; i < relatorio.getRelatorio().size(); i++) {
				String linha = "Cliente: " + relatorio.getRelatorio().get(i).get(0) + " - Telefone: " + relatorio.getRelatorio().get(i).get(1) + " - Saldo devedor: " + relatorio.getRelatorio().get(i).get(2);
				p = new Paragraph(linha);
				doc.add(p);
			}
			doc.close();
			Desktop desk = Desktop.getDesktop();
			try {
				desk.open(new File("clientes_saldo_devedor.pdf"));
			} catch (IOException e) {}
		}catch(FileNotFoundException | DocumentException e) {}
	}
}