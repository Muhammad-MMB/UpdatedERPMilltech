package appConfig;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatIntelliJLaf;
import dao.DaoUserRoles;
import dao.DataSource;
import entities.TblUserRoles;
import extras.AppConstants;
import extras.AppGenerics;
import extras.LoadResource;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;

public class AuthenticateUser {

	private JFrame applicationWindow;
	private JTextField textFieldUserName;
	private JButton btnSignIn;
	private JComboBox<TblUserRoles> comboBoxRole;
	private JLabel lblWelcome, lblUserSymbol, lblAngleLogo, lblUserNameSymbol, lblPassword, lblUserRole;
	private JPanel pnlMain;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_SIGN_IN
	}

	private DaoUserRoles daoUserRolesObject;
	private JPasswordField textFieldPassword;

	/** MAIN METHOD INVOKE **/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatIntelliJLaf.setup();
					// FlatLightFlatIJTheme.setup();
					AuthenticateUser window = new AuthenticateUser();
					window.applicationWindow.setVisible(true);
					DataSource.getConnectionLevel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** DEFAULT CONSTRUCTOR **/
	public AuthenticateUser() {
		this.mainFrameProperties();
	}

	/** SETUP MAIN FRAME PROPERTIES **/
	private void mainFrameProperties() {

		try {
			daoUserRolesObject = new DaoUserRoles();

			applicationWindow = new JFrame("Milltech Martin Bright");
			applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// setIconImage(setFrameBannerIcon());
			applicationWindow.setBounds(100, 100, 560, 700);
			applicationWindow.setLocationRelativeTo(null);
			applicationWindow.setResizable(false);
			applicationWindow.getContentPane().setBackground(new Color(255, 255, 255));
			applicationWindow.setContentPane(new JPanelWithBackground(AppConstants.LOGIN_BACKGROUND));

			createAndShowGUI();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/** SET FRAME GUI & PROPERTIES **/
	private void createAndShowGUI() {

		btnSignIn = new JButton("Sign In");
		btnSignIn.setForeground(Color.decode("#2ebaec"));
		btnSignIn.setBackground(Color.decode("#0161ad"));
		btnSignIn.setIcon(AppGenerics.getImageIcon(AppConstants.LOGIN_SYMBOL, 35, 35));
		btnSignIn.setIconTextGap(10);
		btnSignIn.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnSignIn.setBounds(60, 487, 411, 35);
		ActionListener signInAction = new AllUserActionListeners();
		btnSignIn.addActionListener(signInAction);
		btnSignIn.setActionCommand(UserActions.BTN_SIGN_IN.name());

		applicationWindow.getContentPane().setLayout(null);
		applicationWindow.getContentPane().add(btnSignIn);

		lblWelcome = new JLabel("Welcome - Automation System");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblWelcome.setForeground(Color.decode("#0cb2f0"));
		lblWelcome.setBounds(60, 140, 305, 30);
		applicationWindow.getContentPane().add(lblWelcome);

		pnlMain = new JPanel();
		pnlMain.setBounds(60, 181, 412, 286);
		pnlMain.setBackground(new Color(46, 186, 236, 10));
		applicationWindow.getContentPane().add(pnlMain);
		pnlMain.setLayout(null);

		lblUserSymbol = new JLabel();
		lblUserSymbol.setBounds(147, 11, 103, 101);
		lblUserSymbol.setHorizontalAlignment(JLabel.CENTER);
		lblUserSymbol.setIcon(AppGenerics.getImageIcon(AppConstants.USER_SYMBOL, 90, 90));
		pnlMain.add(lblUserSymbol);

		lblAngleLogo = new JLabel();
		lblAngleLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAngleLogo.setBounds(0, 0, 412, 286);
		ImageIcon originalIcon = AppGenerics.getImageIcon(AppConstants.MMB_ANGLE_LOGO, 400, 290);
		ImageIcon fadedIcon = new ImageIcon(makeImageTranslucent(originalIcon.getImage(), 0.07f));
		lblAngleLogo.setIcon(fadedIcon);
		pnlMain.add(lblAngleLogo);

		lblUserNameSymbol = new JLabel();
		lblUserNameSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameSymbol.setIcon(AppGenerics.getImageIcon(AppConstants.USER_NAME_SYMBOL, 40, 37));
		lblUserNameSymbol.setBounds(42, 121, 40, 37);
		pnlMain.add(lblUserNameSymbol);

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(92, 134, 250, 25);
		textFieldUserName.setUI(new HintTextFieldUI("  User Name", true));
		pnlMain.add(textFieldUserName);
		textFieldUserName.setColumns(10);

		lblPassword = new JLabel();
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setIcon(AppGenerics.getImageIcon(AppConstants.PASSWORD_SYMBOL, 37, 37));
		lblPassword.setBounds(42, 180, 37, 37);
		pnlMain.add(lblPassword);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(92, 185, 250, 25);
		pnlMain.add(textFieldPassword);

		lblUserRole = new JLabel();
		lblUserRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserRole.setIcon(AppGenerics.getImageIcon(AppConstants.USER_ROLE, 35, 35));
		lblUserRole.setBounds(48, 238, 35, 35);
		pnlMain.add(lblUserRole);

		comboBoxRole = new JComboBox<>();
		comboBoxRole.setBounds(92, 240, 250, 25);
		AutoCompleteDecorator.decorate(comboBoxRole);
		bindComboBox(comboBoxRole, getAllUserRoles());
		pnlMain.add(comboBoxRole);
		
		
	}

	/** RETRIEVE LIST OF ALL USER ROLES **/
	private List<TblUserRoles> getAllUserRoles() {
		List<TblUserRoles> listItems = null;
		try {
			listItems = daoUserRolesObject.fetchAllUserRoles();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listItems;
	}

	/** SET IMAGE TRANSPARENCY ADJUSTMENT **/
	private static Image makeImageTranslucent(Image source, float alpha) {
		int width = source.getWidth(null);
		int height = source.getHeight(null);
		BufferedImage translucentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = translucentImage.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.drawImage(source, 0, 0, null);
		g2d.dispose();

		return translucentImage;
	}

	/** BIND COMBO BOX WITH USER ROLE ID & USER ROLE NAME **/
	private void bindComboBox(JComboBox<TblUserRoles> comboBox, List<TblUserRoles> items) {
		DefaultComboBoxModel<TblUserRoles> model = new DefaultComboBoxModel<>(items.toArray(new TblUserRoles[0]));
		comboBox.setModel(model);
	}

	/** SET USER AUTHENTICATEION PROCESS **/
	private void authenticateUser() {
		try {
			new AppWelcome();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		applicationWindow.dispose();
	}

	/** SET MAIN PANEL BACKGROUND IMAGE **/
	public class JPanelWithBackground extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image backgroundImage;

		public JPanelWithBackground(String fileName) throws IOException {
			try {
				backgroundImage = LoadResource.getImageFromResourceAsURL(fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class AllUserActionListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == UserActions.BTN_SIGN_IN.name()) {
				authenticateUser();
				;
			}
		}
	}

	/** CLASS FOR SET USER FIELDS TEXT WATERMARKS / HINTS **/
	public class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {

		private String hint;
		private boolean hideOnFocus;
		private Color color;

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
			repaint();
		}

		private void repaint() {
			if (getComponent() != null) {
				getComponent().repaint();
			}
		}

		public boolean isHideOnFocus() {
			return hideOnFocus;
		}

		public void setHideOnFocus(boolean hideOnFocus) {
			this.hideOnFocus = hideOnFocus;
			repaint();
		}

		public String getHint() {
			return hint;
		}

		public void setHint(String hint) {
			this.hint = hint;
			repaint();
		}

		public HintTextFieldUI(String hint) {
			this(hint, false);
		}

		public HintTextFieldUI(String hint, boolean hideOnFocus) {
			this(hint, hideOnFocus, null);
		}

		public HintTextFieldUI(String hint, boolean hideOnFocus, Color color) {
			this.hint = hint;
			this.hideOnFocus = hideOnFocus;
			this.color = color;
		}

		@Override
		protected void paintSafely(Graphics g) {
			super.paintSafely(g);
			JTextComponent comp = getComponent();
			if (hint != null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus()))) {
				if (color != null) {
					g.setColor(color);
				} else {
					g.setColor(comp.getForeground().brighter().brighter().brighter());
				}
				int padding = (comp.getHeight() - comp.getFont().getSize()) / 2;
				g.drawString(hint, 2, comp.getHeight() - padding - 1);
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
			if (hideOnFocus)
				repaint();

		}

		@Override
		public void focusLost(FocusEvent e) {
			if (hideOnFocus)
				repaint();
		}

		@Override
		protected void installListeners() {
			super.installListeners();
			getComponent().addFocusListener(this);
		}

		@Override
		protected void uninstallListeners() {
			super.uninstallListeners();
			getComponent().removeFocusListener(this);
		}
	}
}
