package view;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class CustomButton extends JButton{
	public CustomButton(String name) {
		this.setText(name);
		this.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.BLACK);
		
	}
}