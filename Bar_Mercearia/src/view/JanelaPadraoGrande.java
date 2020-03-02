package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public abstract class JanelaPadraoGrande extends JFrame{
	public JanelaPadraoGrande() {
		this.setSize(800, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.decode("#FF0000"));
		this.setLocationRelativeTo(null);
		desenharJanela();
	}
	
	//Template method
	public final void desenharJanela() {
		addLabel();
		addBotoes();
		addTextField();
		addComboBox();
		addTabela();
		addMenu();
		lookAndFeel();
	}
	public final void lookAndFeel() {
		ImageIcon icone = new ImageIcon(getClass().getResource("/images/escudo-flamengo.png"));
		this.setIconImage(icone.getImage());
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao alterar o tema");
		}
	}
	public abstract void addLabel();
	public abstract void addBotoes();
	public abstract void addTextField();
	public abstract void addComboBox();
	public abstract void addTabela();
	public abstract void addMenu();
}