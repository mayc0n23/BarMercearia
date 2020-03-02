package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ManterMesas {
	private static XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private static File arquivo = new File("mesas.xml");
	private ArrayList<Mesa> mesas = recuperarMesas();
	private int codMesa = mesas.size();
	
	public int getCodMesa() {
		return codMesa;
	}

	public void setCodMesa(int codMesa) {
		this.codMesa = codMesa;
	}

	public void salvarMesas(ArrayList<Mesa> mesa) {
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		xml += xstream.toXML(mesa);
		try {
			if(!arquivo.exists()) {
				arquivo.createNewFile();
			}
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Mesa> recuperarMesas(){
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<Mesa>)xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<Mesa>();
	}
	public ArrayList<Mesa> getMesas(){
		return mesas;
	}
	public Mesa recuperarMesa(int n) {
		Mesa m = null;
		for(int i = 0; i < mesas.size(); i++) {
			if(mesas.get(i).getNumMesa() == n) {
				m = mesas.get(i);
				break;
			}
		}
		return m;
	}
}