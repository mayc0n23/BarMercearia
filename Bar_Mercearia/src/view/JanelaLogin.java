package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ControllerCliente;
import controller.ControllerVendedor;
import dto.VendedorDTO;

public class JanelaLogin extends JanelaPadraoPequena{
	private JTextField txtNome;
	private JPasswordField txtSenha;
	
	public JanelaLogin() {
		super();
		this.setTitle("Bar e Mercearia do Flamengo");
		this.setVisible(true);
	}

	@Override
	public void addLabel() {
		CustomLabel lblBemVindo = new CustomLabel("Bar e Mercearia do Flamengo");
		lblBemVindo.setBounds(25, 15, 320, 30);
		add(lblBemVindo);
		CustomLabel lblNome = new CustomLabel("");
		lblNome.setBounds(46, 75, 32, 32);
		lblNome.setIcon(new ImageIcon(getClass().getResource("/images/man.png")));
		add(lblNome);
		CustomLabel lblSenha = new CustomLabel("");
		lblSenha.setIcon(new ImageIcon(getClass().getResource("/images/key.png")));
		lblSenha.setBounds(46, 135, 32, 32);
		add(lblSenha);
	}

	@Override
	public void addBotoes() {
		CustomButton btEntrar = new CustomButton("Acessar !");
		btEntrar.setBounds(46, 220, 275, 30);
		btEntrar.addActionListener(new OuvinteLogar());
		add(btEntrar);
		CustomButton btEsqueceu = new CustomButton("Esqueci a senha");
		btEsqueceu.addActionListener(new OuvinteRecuperarSenha());
		btEsqueceu.setBounds(46, 265, 275, 30);
		add(btEsqueceu);
	}

	@Override
	public void addTextField() {
		txtNome = new JTextField();
		txtNome.setBounds(80, 75, 240, 32);
		txtNome.setText("Nome de usuário");
		txtNome.addMouseListener((MouseListener) new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				txtNome.setText("");
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		txtNome.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		add(txtNome);
		txtSenha = new JPasswordField();
		txtSenha.setBounds(80, 135, 240, 32);
		add(txtSenha);
	}

	@Override
	public void addComboBox() {}

	@Override
	public void addTabela() {}
	
	private class OuvinteLogar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerVendedor cv = new ControllerVendedor();
			VendedorDTO vendedor = cv.buscarVendedor(txtNome.getText(), new String(txtSenha.getPassword()));
			JanelasFactory abrir = new JanelasFactory();
			if(vendedor != null) {
				abrir.abrirJanela(vendedor.getNome());
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Dados inválidos");
			}
		}
	}
	private class OuvinteRecuperarSenha implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerCliente cc = new ControllerCliente();
			String msg = cc.recuperarSenha();
			JavaMail email = new JavaMail();
			email.enviarEmail("986576514", "may.karvalhooo@gmail.com", "sandra.pcarvalhosousa@gmail.com", msg, "Dados");
		}
	}
}