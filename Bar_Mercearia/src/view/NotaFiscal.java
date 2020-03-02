package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dto.ClienteDTO;
import dto.ProdutoDTO;

public class NotaFiscal {
	public void gerarNota(ClienteDTO cliente, ArrayList<ProdutoDTO> produtos, float total) {
		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("nota_fiscal.pdf"));
			doc.open();
			Paragraph p = new Paragraph("Cupom fiscal");
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			p = new Paragraph("Comprovante de compra no Bar e Mercearia do Flamengo\n");
			doc.add(p);
			p = new Paragraph("Consumidor: " + cliente.getNome() + "\n");
			doc.add(p);
			p = new Paragraph("Produtos:");
			doc.add(p);
			for(ProdutoDTO pp: produtos) {
				p = new Paragraph("Produto: " + pp.getNome());
				doc.add(p);
			}
			p = new Paragraph("Valor total: " + total);
			doc.add(p);
			doc.close();
			Desktop desk = Desktop.getDesktop();
			try {
				desk.open(new File("nota_fiscal.pdf"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(FileNotFoundException | DocumentException e) {}		
	}
}