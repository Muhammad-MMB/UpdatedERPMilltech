package gui.manufacturing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.icon.EmptyIcon;

import dao.DAO_Bom_Route;
import dao.DAO_Bom_Route_Metadata;
import entities.tbl_bom_route;
import extras.AppConstants;
import extras.Generics;
import extras.LoadResource;
import extras.MessageWindow;
import extras.MessageWindow.MessageType;
import javax.swing.tree.TreeModel;

public class BOMSetup extends JFrame {
	private static final long serialVersionUID = 1L;

	/** COMPONENTS / CONTROLS **/
	JPanel pnlTop, pnlMiddle;
	JCheckBox chckBoxSubLvl;
	JLabel lblEndItem, lblMachineName, lblInFeedItem;
	JComboBox<String> cmbBoxEndItem, cmboBoxMachineName, cmboBoxInFeedItem;
	JButton btnAddTree;

	/** VARIABLES **/
	int endItemStockID = 0, inFeedStockID = 0, selectedMachineID = 0;
	boolean isBomRouteExistCheck = false;

	/** CLASSES OBJECTS **/
	DAO_Bom_Route daoBomRouteObject;
	DAO_Bom_Route_Metadata daoBomRouteMetadataObject;

	public BOMSetup() {

		/** FRAME PROPERTIES **/
		this.setTitle("Setup Bill of Materials - (BOM)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoBomRouteObject = new DAO_Bom_Route();
		daoBomRouteMetadataObject = new DAO_Bom_Route_Metadata();

		/** IN CLASS METHODS **/
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		pnlTop = new JPanel();
		pnlTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTop.setBounds(10, 11, 884, 161);
		getContentPane().add(pnlTop);
		pnlTop.setLayout(null);

		lblEndItem = new JLabel("End Item:");
		lblEndItem.setBounds(20, 38, 93, 14);
		pnlTop.add(lblEndItem);

		cmbBoxEndItem = Generics.createComboBox(getAllListStockCodes());
		cmbBoxEndItem.setBounds(123, 30, 238, 22);
		AutoCompleteDecorator.decorate(cmbBoxEndItem);
		cmbBoxEndItem.setEditable(true);
		pnlTop.add(cmbBoxEndItem);

		lblInFeedItem = new JLabel("In-Feed Item:");
		lblInFeedItem.setBounds(485, 38, 93, 14);
		pnlTop.add(lblInFeedItem);

		cmboBoxInFeedItem = Generics.createComboBox(getAllListStockCodes());
		cmboBoxInFeedItem.setBounds(123, 87, 238, 22);
		cmboBoxInFeedItem.setBounds(599, 30, 238, 22);
		AutoCompleteDecorator.decorate(cmboBoxInFeedItem);
		cmboBoxInFeedItem.setEditable(true);
		pnlTop.add(cmboBoxInFeedItem);

		lblMachineName = new JLabel("Machine Name:");
		lblMachineName.setBounds(20, 97, 93, 14);
		pnlTop.add(lblMachineName);

		cmboBoxMachineName = Generics.createComboBox(getMachineName());
		cmboBoxMachineName.setBounds(123, 93, 238, 22);
		AutoCompleteDecorator.decorate(cmboBoxMachineName);
		cmboBoxMachineName.setEditable(true);
		pnlTop.add(cmboBoxMachineName);

		btnAddTree = new JButton("Add into tree");
		ActionListener addTreeListener = new addTreeListener();
		btnAddTree.addActionListener(addTreeListener);
		btnAddTree.setBounds(680, 87, 157, 35);
		pnlTop.add(btnAddTree);

		chckBoxSubLvl = new JCheckBox("Add into Sub level");
		chckBoxSubLvl.setBounds(485, 93, 130, 23);
		pnlTop.add(chckBoxSubLvl);

		pnlMiddle = new JPanel();
		pnlMiddle.setBorder(new TitledBorder(null, "Routes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMiddle.setBounds(10, 183, 884, 516);
		getContentPane().add(pnlMiddle);
		pnlMiddle.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 26, 359, 479);
		pnlMiddle.add(scrollPane);

		JTree BomTree = new JTree(createTreeModel());
		BomTree.setCellRenderer(new userRendererForTree());
		scrollPane.setViewportView(BomTree);
		setEmptyTreeIcons();
	}

	/** SET ALL TREE ICONS EMPTY **/
	private void setEmptyTreeIcons() {
		EmptyIcon empty = new EmptyIcon();
		UIManager.put("Tree.closedIcon", empty);
		UIManager.put("Tree.openIcon", empty);
		UIManager.put("Tree.collapsedIcon", empty);
		UIManager.put("Tree.expandedIcon", empty);
		UIManager.put("Tree.leafIcon", empty);
	}

	/** RETRIEVE LIST OF ALL STOCK CODES **/
	private ArrayList<String> getAllListStockCodes() {
		ArrayList<String> itemsList = new ArrayList<>();
		try {
			ArrayList<tbl_bom_route> stockesArray = daoBomRouteObject.getAllStockInfo(1, "");
			for (int item = 0; item < stockesArray.size(); item++) {
				itemsList.add(stockesArray.get(item).getStockCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	/** RETRIEVE MACHINE INFO **/
	private ArrayList<String> getMachineName() {
		ArrayList<String> itemsList = new ArrayList<>();
		try {
			ArrayList<tbl_bom_route> machinesArray = daoBomRouteObject.getAllMachineInfo(1, "");
			for (int item = 0; item < machinesArray.size(); item++) {
				itemsList.add(machinesArray.get(item).getMachineName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	/** RETRIEVE ALL SELECTED COMBOBOX ITEMS ID'S **/
	private void getSelectedComboBoxItemsID() {
		try {
			ArrayList<tbl_bom_route> machinesArray = daoBomRouteObject.getAllMachineInfo(2,
					cmboBoxMachineName.getSelectedItem().toString());
			for (int item = 0; item < machinesArray.size(); item++) {
				selectedMachineID = machinesArray.get(item).getMachineID();
			}
			ArrayList<tbl_bom_route> stockesArrayEndItem = daoBomRouteObject.getAllStockInfo(2,
					cmbBoxEndItem.getSelectedItem().toString());
			for (int item = 0; item < stockesArrayEndItem.size(); item++) {
				endItemStockID = stockesArrayEndItem.get(item).getStockID();
			}
			ArrayList<tbl_bom_route> stockesArrayInFeedItem = daoBomRouteObject.getAllStockInfo(2,
					cmboBoxInFeedItem.getSelectedItem().toString());
			for (int item = 0; item < stockesArrayInFeedItem.size(); item++) {
				inFeedStockID = stockesArrayInFeedItem.get(item).getStockID();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** SUBMIT BUTTON ACTION **/
	private void submitRecords() {
		try {
			getSelectedComboBoxItemsID();
			daoBomRouteObject.setBomRoute(endItemStockID, inFeedStockID, selectedMachineID, true);
			daoBomRouteMetadataObject.setBomRouteMetadata(
					cmbBoxEndItem.getSelectedItem().toString() + "_" + cmboBoxMachineName.getSelectedItem().toString(),
					chckBoxSubLvl.isSelected(), true);

			/*
			 * isBomRouteExistCheck = daoBomRouteObject.isBomRouteExist(endItemStockID,
			 * inFeedStockID, selectedMachineID); if (isBomRouteExistCheck) {
			 * MessageWindow.showMessage("This route already exists in a system",
			 * MessageType.ERROR); } else { daoBomRouteObject.setBomRoute(endItemStockID,
			 * selectedMachineID, true); }
			 */
		} catch (SQLException e) {
			MessageWindow.showMessage(e.getMessage(), MessageType.ERROR);
		}
	}

	private DefaultTreeModel createTreeModel() {
		DefaultTreeModel model = null;
		DefaultMutableTreeNode nodes = null;
		try {
			ArrayList<tbl_bom_route> bomRoutesArray = daoBomRouteObject.fetchAllBomRoutes();
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(AppConstants.BOM_TREE_NAME);
			for (int item = 0; item < bomRoutesArray.size(); item++) {
				nodes = new DefaultMutableTreeNode(bomRoutesArray.get(item).getRouteName());
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
						bomRoutesArray.get(item).getInFeedStockCode());
				nodes.add(childNode);
				root.add(nodes);
			}
			model = new DefaultTreeModel(root);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** LOAD IMAGE ICON FROM RESOURCES **/
	private ImageIcon setImageIcon(String path) {
		Image image = null;
		ImageIcon icon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(path);
			image = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			return icon;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}

	/** CHANGE JTREE DEFAULT ICONS **/
	private class userRendererForTree extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			String nodeValue = node.getUserObject().toString();
			if (AppConstants.BOM_TREE_NAME == nodeValue) {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_ROOT));
			} else {
				if (leaf) {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_LEAF));
				} else if (expanded) {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_EXPAND));
				} else {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_RIGHT));
				}
			}
			return this;
		}
	}

	/** ACTION LISTENER OF SUBMIT BUTTON **/
	class addTreeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					submitRecords();
				}
			});
		}
	}
}
