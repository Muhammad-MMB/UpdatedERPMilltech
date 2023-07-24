package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import extras.*;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

public class AboutProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ReadResources readResObject;

	public AboutProduct() {
		
		readResObject = new ReadResources();
		setTitle("About Product");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 322);
		setResizable(false);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 434, 261);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCmpnyNme = new JLabel("Copyright @ Milltech Martin Bright Pty Ltd");
		lblCmpnyNme.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCmpnyNme.setBounds(109, 206, 210, 22);
		panel.add(lblCmpnyNme);

		JLabel lblCmpnyLogo = new JLabel("");
		lblCmpnyLogo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCmpnyLogo.setBounds(66, 25, 300, 100);
		Image image;
		try {
			
			image = readResObject.getImageFromResourceAsURL(AppConstants.MMBLOGO);
			image = image.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
			lblCmpnyLogo.setIcon(new ImageIcon(image));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel.add(lblCmpnyLogo);

		JSeparator separatorLeft = new JSeparator();
		separatorLeft.setBounds(66, 156, 80, 8);
		panel.add(separatorLeft);

		JLabel lblProductInfo = new JLabel("Product Information");
		lblProductInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblProductInfo.setBounds(158, 142, 121, 22);
		panel.add(lblProductInfo);

		JSeparator separatoRight = new JSeparator();
		separatoRight.setBounds(276, 156, 90, 8);
		panel.add(separatoRight);

		JLabel lblMilltechErpSystem = new JLabel("ERP System Version1.0 - August 2023");
		lblMilltechErpSystem.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMilltechErpSystem.setBounds(117, 175, 188, 22);
		panel.add(lblMilltechErpSystem);
	}
}
