package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class OuvinteAceitarNumeros implements KeyListener{
	private JTextField txtField;
	
	public OuvinteAceitarNumeros(JTextField txt) {
		txtField = txt;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		String caracters = "0987654321.";
		if(!caracters.contains(e.getKeyChar()+"")) {
			e.consume();
		}
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}