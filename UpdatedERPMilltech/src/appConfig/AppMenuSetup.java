package appConfig;

import dao.DataSource;
import dao.ImportUserEntities;
import extras.AppConstants;
import extras.LoadResource;
import gui.AboutProduct;
import gui.RptSales;
import gui.manufacturing.BOM_Route_Setup;
import gui.manufacturing.SetupMachine;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AppMenuSetup extends JFrame {

	private static final long serialVersionUID = 1L;
	private RptSales rptSalesObject = null;
	private AboutProduct abtPrdctObject = null;

	public JMenuBar createAppMenu() throws Exception {

		JMenuBar menuBar;
		JMenu menu, subMenu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();

		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		appMenuProperties.mainMenuSize(menu);
		menuBar.add(menu);

		menuItem = new JMenuItem("New", appMenuProperties.setIconImage(AppConstants.NEW_FILE));
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("New File");
		appMenuProperties.menuItemSize(menuItem);
		menu.add(menuItem);

		menu.addSeparator();
		menuItem = new JMenuItem("Exit", appMenuProperties.setIconImage(AppConstants.EXIT_APP));
		menuItem.setMnemonic(KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Exit Application");
		appMenuProperties.menuItemSize(menuItem);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					if (!DataSource.checkConnectionStatus()) {
						DataSource.closeConnection();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});

		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription("Edit Menu");
		appMenuProperties.mainMenuSize(menu);
		menuBar.add(menu);

		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription("Get Reports");
		appMenuProperties.mainMenuSize(menu);

		menuItem = new JMenuItem("Menu - 01", appMenuProperties.setIconImage(AppConstants.NEW_FILE));
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("New File");
		appMenuProperties.menuItemSize(menuItem);
		menu.add(menuItem);
		menu.addSeparator();

		subMenu = new JMenu("Inventory");
		subMenu.setIcon(appMenuProperties.setIconImage(AppConstants.MAIN_INVENTORY));
		subMenu.setMnemonic(KeyEvent.VK_I);
		subMenu.setPreferredSize(new Dimension(180, 25));

		menuItem = new JMenuItem("Transactions", appMenuProperties.setIconImage(AppConstants.INVENTORY_TRANSACTIONS));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_T);
		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);
		menu.addSeparator();

		subMenu = new JMenu("Sales");
		subMenu.setMnemonic(KeyEvent.VK_S);
		subMenu.setIcon(appMenuProperties.setIconImage(AppConstants.SALES));
		subMenu.setPreferredSize(new Dimension(180, 25));
		menuItem = new JMenuItem("Historical Sales", appMenuProperties.setIconImage(AppConstants.HISTORICAL_SALE));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_H);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);

		menuItem = new JMenuItem("Sales Report", appMenuProperties.setIconImage(AppConstants.HISTORICAL_SALE));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_S);
		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (rptSalesObject != null && rptSalesObject.isVisible()) {
						rptSalesObject.setExtendedState(JFrame.NORMAL);
						rptSalesObject.toFront();
						rptSalesObject.requestFocus();
					} else {

						rptSalesObject = new RptSales();
						rptSalesObject.setVisible(true);
					}
				} catch (SQLException excpt) {
					excpt.printStackTrace();
				}
			}
		});

		menu = new JMenu("Admin");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("Administration Panel");
		menu.setPreferredSize(new Dimension(70, 30));
		appMenuProperties.mainMenuSize(menu);
		menuBar.add(menu);

		menuItem = new JMenuItem("Test Menu", appMenuProperties.setIconImage(AppConstants.IMPORT_TABLES));
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Import Tables");
		appMenuProperties.menuItemSize(menuItem);
		menu.add(menuItem);
		menu.addSeparator();

		subMenu = new JMenu("Entities");
		subMenu.setMnemonic(KeyEvent.VK_A);
		subMenu.setIcon(appMenuProperties.setIconImage(AppConstants.ENTITIES));
		subMenu.setPreferredSize(new Dimension(180, 25));
		menu.add(subMenu);

		menuItem = new JMenuItem("Import", appMenuProperties.setIconImage(AppConstants.IMPORT));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_I);
		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ImportUserEntities();
			}
		});

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menu.getAccessibleContext().setAccessibleDescription("Get Help");
		appMenuProperties.mainMenuSize(menu);
		menuBar.add(menu);
		menuItem = new JMenuItem("About", appMenuProperties.setIconImage(AppConstants.ABOUT));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_A);
		appMenuProperties.menuItemSize(menuItem);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (abtPrdctObject != null && abtPrdctObject.isVisible()) {
					abtPrdctObject.setExtendedState(JFrame.NORMAL);
					abtPrdctObject.toFront();
					abtPrdctObject.requestFocus();
				} else {

					abtPrdctObject = new AboutProduct();
					abtPrdctObject.setVisible(true);
				}
			}
		});

		menu = new JMenu("Manufacturing");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.getAccessibleContext().setAccessibleDescription("Manufacturing");
		menu.setPreferredSize(new Dimension(100, 20));

		menuItem = new JMenuItem("Machines", appMenuProperties.setIconImage(AppConstants.NEW_FILE));
		menuItem.setMnemonic(KeyEvent.VK_M);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Machines");
		appMenuProperties.menuItemSize(menuItem);
		menu.add(menuItem);
		menu.addSeparator();

		subMenu = new JMenu("Machines Settings");
		subMenu.setIcon(appMenuProperties.setIconImage(AppConstants.MAIN_INVENTORY));
		subMenu.setMnemonic(KeyEvent.VK_S);
		subMenu.setPreferredSize(new Dimension(180, 25));

		menuItem = new JMenuItem("Setup Status", appMenuProperties.setIconImage(AppConstants.INVENTORY_TRANSACTIONS));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_S);

		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SetupMachine obj = new SetupMachine();
				if (obj != null && obj.isVisible()) {
					obj.setExtendedState(JFrame.NORMAL);
					obj.toFront();
					obj.requestFocus();
				} else {

					obj = new SetupMachine();
					obj.setVisible(true);
				}
			}
		});

		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);
		menu.addSeparator();

		subMenu = new JMenu("BOM Routes");
		subMenu.setMnemonic(KeyEvent.VK_S);
		subMenu.setIcon(appMenuProperties.setIconImage(AppConstants.SALES));
		subMenu.setPreferredSize(new Dimension(180, 25));
		menuItem = new JMenuItem("Setup BOM", appMenuProperties.setIconImage(AppConstants.HISTORICAL_SALE));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_H);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BOM_Route_Setup obj = new BOM_Route_Setup();
				if (obj != null && obj.isVisible()) {
					obj.setExtendedState(JFrame.NORMAL);
					obj.toFront();
					obj.requestFocus();
				} else {

					obj = new BOM_Route_Setup();
					obj.setVisible(true);
				}
			}
		});
		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);

		menuItem = new JMenuItem("Sales Report", appMenuProperties.setIconImage(AppConstants.HISTORICAL_SALE));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.setMnemonic(KeyEvent.VK_S);
		appMenuProperties.menuItemSize(menuItem);
		menuBar.add(menu);
		subMenu.add(menuItem);
		menu.add(subMenu);

		return menuBar;
	}
}

/** MAIN MENU PROPERTIES **/
class appMenuProperties {

	public static JMenuItem menuItemSize(JMenuItem item) {
		item.setPreferredSize(new Dimension(180, 25));
		return item;
	}

	public static JMenu mainMenuSize(JMenu mainMenu) {
		mainMenu.setPreferredSize(new Dimension(60, 20));
		return mainMenu;
	}

	public static ImageIcon setIconImage(String path) {
		Image customImage;
		ImageIcon newIcon = null;
		try {
			customImage = LoadResource.getImageFromResourceAsURL(path);
			customImage = customImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
			newIcon = new ImageIcon(customImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newIcon;
	}
}
