package view;

import javax.swing.JOptionPane;

public class JanelasFactory {
	public void abrirJanela(String tipo) {
		if(tipo.equals("Edcarlos")) {
			new JanelaBar();
		}else if(tipo.equals("Sandra")){
			new JanelaMercearia();
		}
	}
}