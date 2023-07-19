package GUI;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import javax.swing.border.LineBorder;

import extras.AppConstants;
import extras.ReadResources;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import javax.swing.JSeparator;

public class AboutProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ReadResources readResObject = new ReadResources();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutProduct frame = new AboutProduct();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AboutProduct() {
		setTitle("About Product");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setBounds(100, 100, 470, 322);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 434, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		this.setVisible(true);
		
		JLabel lblCmpnyNme = new JLabel("Copyright @ Milltech Martin Bright Pty Ltd");
		lblCmpnyNme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCmpnyNme.setBounds(92, 206, 245, 22);
		panel.add(lblCmpnyNme);
		
		JLabel lblCmpnyLogo = new JLabel("");
		lblCmpnyLogo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCmpnyLogo.setBounds(66, 25, 300, 100);
		lblCmpnyLogo.setIcon(readResObject.getIcon(AppConstants.MMBLOGO));
		panel.add(lblCmpnyLogo);
		
		JSeparator separatorLeft = new JSeparator();
		separatorLeft.setBounds(66, 156, 90, 8);
		panel.add(separatorLeft);
		
		JLabel lblProductInfo = new JLabel("Product Information");
		lblProductInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProductInfo.setBounds(163, 142, 105, 22);
		panel.add(lblProductInfo);
		
		JSeparator separatoRight = new JSeparator();
		separatoRight.setBounds(267, 156, 99, 8);
		panel.add(separatoRight);
		
		JLabel lblMilltechErpSystem = new JLabel("Milltech ERP System Version1.0");
		lblMilltechErpSystem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMilltechErpSystem.setBounds(125, 173, 179, 22);
		panel.add(lblMilltechErpSystem);
	}
}
