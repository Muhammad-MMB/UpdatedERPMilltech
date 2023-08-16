package gui.manufacturing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.icon.EmptyIcon;
import dao.DAO_Bom_Route;
import dao.DAO_Bom_Route_Metadata;
import dao.DAO_Bom_Sandbox;
import entities.tbl_bom_route;
import entities.tbl_bom_sandbox;
import extras.AppConstants;
import extras.Generics;
import extras.LoadResource;
import extras.MessageWindow;
import extras.MessageWindow.MessageType;

public class BOM_Route_Setup extends JFrame {
	private static final long serialVersionUID = 1L;

	/** COMPONENTS / CONTROLS **/
	private JPanel pnlTop, pnlViewRoutes, pnlSandBox;
	private JCheckBox chckBoxAddChild;
	private JLabel lblEndItem, lblMachineName, lblInFeedItem;
	private JComboBox<String> cmbBoxEndItem, cmboBoxMachineName, cmboBoxInFeedItem;
	private JButton btnCollapseAll, btnAddTree, btnAddSandbox, btnCancel;
	private JScrollPane scrollPaneSandBox;
	private JTree bomRouteJTree, sandboxJTree;
	private DefaultMutableTreeNode routeJTreeRootNode, routeJTreeChildNodes, sandboxJTreeRootNode;

	/** VARIABLES **/
	private int endItemStockID = 0, inFeedStockID = 0, selectedMachineID = 0;
	private String previousComboBoxInFeedSelectedItem = null;
	static String SANDBOX_ROOT_NAME = null;
	static int MAXIMUM_SANDBOX_TREE_DEPTH = 0;

	/** CLASSES OBJECTS **/
	DAO_Bom_Route daoBomRouteObject;
	DAO_Bom_Sandbox daoSandboxObject;
	DAO_Bom_Route_Metadata daoBomRouteMetadataObject;

	/** ENUM FOR USER ACTIONS **/
	private enum Actions {
		CHKBOX_ADD_SANDBOX, BTN_ADD_SANDBOX, BTN_ADD_ROUTE, BTN_COLLAPSE_ALL, CANCEL
	}

	public BOM_Route_Setup() {

		/** FRAME PROPERTIES **/
		this.setTitle("Setup Bill of Materials Routes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1235, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoBomRouteObject = new DAO_Bom_Route();
		daoSandboxObject = new DAO_Bom_Sandbox();
		daoBomRouteMetadataObject = new DAO_Bom_Route_Metadata();

		/** IN CLASS METHODS **/
		createAndShowGUI();

		/** DELETE SANDBOX TABLE ON LOAD **/
		try {
			daoSandboxObject.deleteAllTblSandboxRecords();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createAndShowGUI() {
		pnlTop = new JPanel();
		pnlTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTop.setBounds(10, 11, 1199, 161);
		getContentPane().add(pnlTop);
		pnlTop.setLayout(null);

		lblEndItem = new JLabel("End Item:");
		lblEndItem.setBounds(20, 38, 93, 14);
		pnlTop.add(lblEndItem);

		cmbBoxEndItem = Generics.createComboBox(getAllListStockCodes());
		cmbBoxEndItem.setBounds(123, 30, 238, 22);
		AutoCompleteDecorator.decorate(cmbBoxEndItem);
		pnlTop.add(cmbBoxEndItem);

		lblInFeedItem = new JLabel("In-Feed Item:");
		lblInFeedItem.setBounds(485, 38, 93, 14);
		pnlTop.add(lblInFeedItem);

		cmboBoxInFeedItem = Generics.createComboBox(getAllListStockCodes());
		cmboBoxInFeedItem.setBounds(599, 30, 238, 22);
		AutoCompleteDecorator.decorate(cmboBoxInFeedItem);
		pnlTop.add(cmboBoxInFeedItem);

		lblMachineName = new JLabel("Machine Name:");
		lblMachineName.setBounds(20, 97, 93, 14);
		pnlTop.add(lblMachineName);

		cmboBoxMachineName = Generics.createComboBox(getMachineName());
		cmboBoxMachineName.setBounds(123, 93, 238, 22);
		AutoCompleteDecorator.decorate(cmboBoxMachineName);
		pnlTop.add(cmboBoxMachineName);

		chckBoxAddChild = new JCheckBox("Add this as child");
		chckBoxAddChild.setBounds(1000, 38, 130, 23);
		chckBoxAddChild.setActionCommand(Actions.CHKBOX_ADD_SANDBOX.name());
		ActionListener chckBoxActionListener = new userActionListerners();
		chckBoxAddChild.addActionListener(chckBoxActionListener);
		pnlTop.add(chckBoxAddChild);

		btnAddSandbox = new JButton("Add to sandbox");
		btnAddSandbox.setActionCommand(Actions.BTN_ADD_SANDBOX.name());
		ActionListener addSandboxtreeListener = new userActionListerners();
		btnAddSandbox.addActionListener(addSandboxtreeListener);
		btnAddSandbox.setBounds(1000, 97, 150, 35);
		pnlTop.add(btnAddSandbox);

		pnlSandBox = new JPanel();
		pnlSandBox.setBorder(
				new TitledBorder(null, "Sandbox of Routes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSandBox.setBounds(10, 183, 1199, 229);
		getContentPane().add(pnlSandBox);
		pnlSandBox.setLayout(null);

		scrollPaneSandBox = new JScrollPane();
		scrollPaneSandBox.setBounds(45, 36, 533, 170);
		pnlSandBox.add(scrollPaneSandBox);

		sandboxJTree = new JTree();
		sandboxJTree.setModel(setSandboxJTreeModel());
		sandboxJTree.setCellRenderer(new userRendererJTree());
		scrollPaneSandBox.setViewportView(sandboxJTree);

		btnAddTree = new JButton("Add to route");
		btnAddTree.setBounds(832, 171, 150, 35);
		btnAddTree.setActionCommand(Actions.BTN_ADD_ROUTE.name());
		ActionListener addBtnTreeListener = new userActionListerners();
		btnAddTree.addActionListener(addBtnTreeListener);
		pnlSandBox.add(btnAddTree);

		btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand(Actions.CANCEL.name());
		ActionListener addBtnCancelListener = new userActionListerners();
		btnCancel.addActionListener(addBtnCancelListener);
		btnCancel.setBounds(999, 171, 150, 35);
		pnlSandBox.add(btnCancel);

		pnlViewRoutes = new JPanel();
		pnlViewRoutes.setBorder(new TitledBorder(null, "View Bill of Materials Routes", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		pnlViewRoutes.setBounds(10, 423, 1199, 527);
		getContentPane().add(pnlViewRoutes);
		pnlViewRoutes.setLayout(null);

		JScrollPane scrollPaneRoutes = new JScrollPane();
		scrollPaneRoutes.setBounds(28, 26, 359, 479);
		pnlViewRoutes.add(scrollPaneRoutes);

		bomRouteJTree = new JTree();
		bomRouteJTree.setModel(setRouteJTreeModel());
		setEmptyTreeIcons();
		bomRouteJTree.setCellRenderer(new userRendererJTree());
		scrollPaneRoutes.setViewportView(bomRouteJTree);

		btnCollapseAll = new JButton("Collapse all");
		btnCollapseAll.setBounds(1000, 18, 150, 35);
		btnCollapseAll.setActionCommand(Actions.BTN_COLLAPSE_ALL.name());
		ActionListener btnCollapseActionListener = new userActionListerners();
		btnCollapseAll.addActionListener(btnCollapseActionListener);
		pnlViewRoutes.add(btnCollapseAll);

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
				previousComboBoxInFeedSelectedItem = cmboBoxInFeedItem.getSelectedItem().toString();
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
					chckBoxAddChild.isSelected(), true);
		} catch (SQLException e) {
			MessageWindow.showMessage(e.getMessage(), MessageType.ERROR);
		}
	}

	/** REMOVEL ALL ITEMS OF JTREE **/
	private void clearAllTreeItems(JTree tree) {
		if (tree.toString() == null) {
			return;
		}
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		if (root != null) {
			root.removeAllChildren();
			model.reload();
		}
	}

	/** LOAD IMAGE ICON FROM RESOURCES **/
	private ImageIcon setImageIcon(String path) {
		Image image = null;
		ImageIcon icon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(path);
			image = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}

	/** SET BOM ROUTE JTREE MODEL **/
	private DefaultTreeModel setRouteJTreeModel() {
		DefaultTreeModel model = null;
		try {
			ArrayList<tbl_bom_route> bomRoutesArray = daoBomRouteObject.fetchAllBomRoutes();
			routeJTreeRootNode = new DefaultMutableTreeNode(AppConstants.BOM_TREE_NAME);
			for (int item = 0; item < bomRoutesArray.size(); item++) {
				routeJTreeChildNodes = new DefaultMutableTreeNode(bomRoutesArray.get(item).getRouteName());
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
						bomRoutesArray.get(item).getInFeedStockCode());
				routeJTreeChildNodes.add(childNode);
				routeJTreeRootNode.add(routeJTreeChildNodes);
			}
			model = new DefaultTreeModel(routeJTreeRootNode);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** COLLAPSE/CLOSE ALL OPEN JTREE NODES **/
	private void closeAllOpenNodes(JTree tree, DefaultMutableTreeNode node) {
		Enumeration<?> enumeration = node.depthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) enumeration.nextElement();
			if (currentNode != node && tree.isExpanded(new TreePath(currentNode.getPath()))) {
				try {
					tree.collapsePath(new TreePath(currentNode.getPath()));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/** SET ALL TREE ICONS EMPTY **/
	private void setEmptyTreeIcons() {
		EmptyIcon emptyIcon = new EmptyIcon();
		UIManager.put("Tree.closedIcon", emptyIcon);
		UIManager.put("Tree.openIcon", emptyIcon);
		UIManager.put("Tree.collapsedIcon", emptyIcon);
		UIManager.put("Tree.expandedIcon", emptyIcon);
		UIManager.put("Tree.leafIcon", emptyIcon);
	}

	/** SET CHILD CHECK BOX ACTIONS **/
	private void setChildBoxCheckBoxAction() {
		if (chckBoxAddChild.isSelected()) {
			System.out.println(chckBoxAddChild.isSelected());
			if (previousComboBoxInFeedSelectedItem != null) {
				cmbBoxEndItem.setSelectedItem(previousComboBoxInFeedSelectedItem);
			}
			cmbBoxEndItem.setEnabled(false);
		} else {
			System.out.println(chckBoxAddChild.isSelected());
			cmbBoxEndItem.setEnabled(true);
		}
	}

	/** ADD TO SANDBOX BUTTON ACTION COMMAND **/
	private void addToSandboxJTree() {
		try {
			getSelectedComboBoxItemsID();
			SANDBOX_ROOT_NAME = cmbBoxEndItem.getSelectedItem().toString() + "_"
					+ cmboBoxMachineName.getSelectedItem().toString();
			ArrayList<tbl_bom_sandbox> sandboxParentArray = daoSandboxObject.getSandboxParentStockCode();
			if (sandboxParentArray.size() == 0 && chckBoxAddChild.isSelected()) {
				MessageWindow.showMessage("No parent record found against this entry!", MessageType.ERROR);
			} else if (sandboxParentArray.size() != 0 && !chckBoxAddChild.isSelected()) {
				MessageWindow.showMessage("Orphan entries are not allowed! You must select this entry as a child!",
						MessageType.ERROR);
			} else if (chckBoxAddChild.isSelected()) {
				daoSandboxObject.setSandboxRoute(endItemStockID, inFeedStockID, selectedMachineID,
						chckBoxAddChild.isSelected(), sandboxParentArray.get(0).getInFeedItemID(), "-");
				previousComboBoxInFeedSelectedItem = cmboBoxInFeedItem.getSelectedItem().toString();
				cmbBoxEndItem.setSelectedItem(previousComboBoxInFeedSelectedItem);
				MessageWindow.showMessage("Record successfully inserted!", MessageType.INFORMATION);
			} else {
				daoSandboxObject.setSandboxRoute(endItemStockID, inFeedStockID, selectedMachineID,
						chckBoxAddChild.isSelected(), 0, SANDBOX_ROOT_NAME);
				MessageWindow.showMessage("Record successfully inserted!", MessageType.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** SET RECURSIVE SANDBOX JTREE POPULATE RECORDS **/
	protected void populate(DefaultMutableTreeNode parent, ArrayList<String> items, int index) {
		if (index >= items.size()) {
			return;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(items.get(index));
		parent.add(node);
		populate(node, items, ++index);
	}

	/** SET SANDBOX JTREE MODEL **/
	private DefaultTreeModel setSandboxJTreeModel() {
		sandboxJTreeRootNode = new DefaultMutableTreeNode(AppConstants.SANDBOX_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(sandboxJTreeRootNode);
		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		try {
			ArrayList<tbl_bom_sandbox> sandboxArray = daoSandboxObject.fetchAllSandboxRoutes();
			MAXIMUM_SANDBOX_TREE_DEPTH = sandboxArray.size();
			ArrayList<String> customItems = new ArrayList<>(sandboxArray.size());
			for (int item = 0; item < sandboxArray.size(); item++) {
				customItems.add(sandboxArray.get(item).getInFeedItemName());
			}
			if (MAXIMUM_SANDBOX_TREE_DEPTH != 0) {
				rootNodeName = sandboxArray.get(0).getRouteName();
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				sandboxJTreeRootNode.add(rootNode);
				populate(rootNode, customItems, 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	/** EXPAND ALL NODES OF TREE **/
	private void expandAllNodes(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root));
	}

	/** SET NODES EXAPND STATUS OF TREE **/
	private void expandAll(JTree tree, TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
			TreePath childPath = path.pathByAddingChild(childNode);
			expandAll(tree, childPath);
		}
		try {
			tree.expandPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** CHANGE JTREE DEFAULT ICONS **/
	private class userRendererJTree extends DefaultTreeCellRenderer {
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

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class userActionListerners implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (e.getActionCommand() == Actions.CHKBOX_ADD_SANDBOX.name()) {
						setChildBoxCheckBoxAction();
					} else if (e.getActionCommand() == Actions.BTN_ADD_SANDBOX.name()) {
						addToSandboxJTree();
						clearAllTreeItems(sandboxJTree);
						sandboxJTree.setModel(setSandboxJTreeModel());
						expandAllNodes(sandboxJTree);

					} else if (e.getActionCommand() == Actions.BTN_COLLAPSE_ALL.name()) {
						closeAllOpenNodes(bomRouteJTree, routeJTreeRootNode);
					} else if (e.getActionCommand() == Actions.BTN_ADD_ROUTE.name()) {
						submitRecords();
						clearAllTreeItems(bomRouteJTree);
						bomRouteJTree.setModel(setRouteJTreeModel());
					} else if (e.getActionCommand() == Actions.CANCEL.name()) {
						try {
							int userResponse = MessageWindow.createConfirmDialogueWindow(
									"Are you sure you want to cancel this ?", " Confirm action");
							if (userResponse == 0) {
								daoSandboxObject.deleteAllTblSandboxRecords();
								clearAllTreeItems(sandboxJTree);
								sandboxJTree.setModel(setSandboxJTreeModel());
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
	}
}
