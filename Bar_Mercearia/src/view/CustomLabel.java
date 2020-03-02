package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class CustomLabel extends JLabel{
	public CustomLabel(String name) {
		this.setText(name);
		this.setFont(new Font("Times New Roman", Font.PLAIN, 26));
		this.setForeground(Color.WHITE);
		this.setVisible(true);
	}
}