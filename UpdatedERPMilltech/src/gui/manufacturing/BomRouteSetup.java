package gui.manufacturing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.icon.EmptyIcon;
import dao.DaoBomRoute;
import dao.DaoBomSandbox;
import dao.DaoMachines;
import entities.TblBomRoute;
import entities.TblBomSandbox;
import entities.TblMachines;
import extras.AppConstants;
import extras.AppGenerics;
import extras.LoadResource;
import extras.MessageWindow;
import extras.MessageWindow.MessageType;
import java.awt.Font;
import java.awt.Graphics;

public class BomRouteSetup extends JFrame {

	private static final long serialVersionUID = 1L;

	/** COMPONENTS / CONTROLS **/
	private JPanel pnlTop, pnlViewRoutes, pnlSandBox;
	private JCheckBox chckBoxAddChild;
	private JLabel lblEndItem, lblMachineName, lblInFeedItem,
			lblMachineStateIcon;
	private JComboBox<String> cmbBoxEndItem, cmboBoxMachineName,
			cmboBoxInFeedItem;
	private JButton btnCollapseAll, btnAddTree, btnAddSandbox, btnCancel;
	private JScrollPane scrollPaneSandBox, scrollPaneRoute;
	private JTree sandboxJTree, routeJTree;
	private JList<TblBomRoute> routeList;
	private JScrollPane scrollPaneList;
	private DefaultMutableTreeNode routeJTreeRootNode, sandboxJTreeRootNode;

	/** VARIABLES **/
	private int endItemStockID = 0, inFeedStockID = 0, selectedMachineID = 0;
	private boolean isFaultyMachineFound = false;
	private String previousComboBoxInFeedSelectedItem = null;
	static String SANDBOX_ROOT_NAME = null;
	static int SANDBOX_GROUP_ID = 0;

	/** ARRAYS **/
	ArrayList<Integer> allSndboxGroupIDArray = new ArrayList<>();

	/** CLASSES OBJECTS **/
	DaoBomRoute daoBomRouteObject;
	DaoBomSandbox daoSandboxObject;
	DaoMachines daoMachinesObject;
	CustomRouteJListModel routeListModel;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum Actions {
		CHKBOX_ADD_SANDBOX, BTN_ADD_SANDBOX, BTN_ADD_ROUTE, BTN_COLLAPSE_ALL, CANCEL
	}

	public BomRouteSetup() {

		/** FRAME PROPERTIES **/
		this.setTitle("Setup Bill of Materials Routes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1235, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoBomRouteObject = new DaoBomRoute();
		daoSandboxObject = new DaoBomSandbox();
		daoMachinesObject = new DaoMachines();

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

		cmbBoxEndItem = AppGenerics.createComboBox(getAllListStockCodes());
		cmbBoxEndItem.setBounds(123, 30, 238, 22);
		AutoCompleteDecorator.decorate(cmbBoxEndItem);
		pnlTop.add(cmbBoxEndItem);

		lblInFeedItem = new JLabel("In-Feed Item:");
		lblInFeedItem.setBounds(485, 38, 93, 14);
		pnlTop.add(lblInFeedItem);

		cmboBoxInFeedItem = AppGenerics.createComboBox(getAllListStockCodes());
		cmboBoxInFeedItem.setBounds(599, 30, 238, 22);
		AutoCompleteDecorator.decorate(cmboBoxInFeedItem);
		pnlTop.add(cmboBoxInFeedItem);

		lblMachineName = new JLabel("Machine Name:");
		lblMachineName.setBounds(20, 97, 93, 14);
		pnlTop.add(lblMachineName);

		cmboBoxMachineName = AppGenerics.createComboBox(getMachineName());
		cmboBoxMachineName.setBounds(123, 93, 238, 22);
		AutoCompleteDecorator.decorate(cmboBoxMachineName);
		pnlTop.add(cmboBoxMachineName);

		chckBoxAddChild = new JCheckBox("Add this as child");
		chckBoxAddChild.setBounds(1000, 38, 130, 23);
		chckBoxAddChild.setActionCommand(Actions.CHKBOX_ADD_SANDBOX.name());
		ActionListener chckBoxActionListener = new AllUserActionListeners();
		chckBoxAddChild.addActionListener(chckBoxActionListener);
		pnlTop.add(chckBoxAddChild);

		btnAddSandbox = new JButton("Add to sandbox");
		btnAddSandbox.setActionCommand(Actions.BTN_ADD_SANDBOX.name());
		ActionListener addSandboxtreeListener = new AllUserActionListeners();
		btnAddSandbox.addActionListener(addSandboxtreeListener);
		btnAddSandbox.setBounds(1000, 97, 150, 35);
		pnlTop.add(btnAddSandbox);

		pnlSandBox = new JPanel();
		pnlSandBox.setBorder(new TitledBorder(null, "Sandbox of Routes",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSandBox.setBounds(10, 183, 1199, 229);
		getContentPane().add(pnlSandBox);
		pnlSandBox.setLayout(null);

		scrollPaneSandBox = new JScrollPane();
		scrollPaneSandBox.setBounds(45, 36, 685, 170);
		pnlSandBox.add(scrollPaneSandBox);

		sandboxJTree = new JTree();
		sandboxJTree.setModel(setSandboxJTreeModel());
		sandboxJTree.setCellRenderer(new userRendererJTree());
		scrollPaneSandBox.setViewportView(sandboxJTree);

		btnAddTree = new JButton("Add to route");
		btnAddTree.setBounds(840, 171, 150, 35);
		btnAddTree.setActionCommand(Actions.BTN_ADD_ROUTE.name());
		ActionListener addBtnTreeListener = new AllUserActionListeners();
		btnAddTree.addActionListener(addBtnTreeListener);
		pnlSandBox.add(btnAddTree);

		btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand(Actions.CANCEL.name());
		ActionListener addBtnCancelListener = new AllUserActionListeners();
		btnCancel.addActionListener(addBtnCancelListener);
		btnCancel.setBounds(1019, 171, 150, 35);
		pnlSandBox.add(btnCancel);

		btnCollapseAll = new JButton("Collapse all");
		btnCollapseAll.setBounds(1019, 36, 150, 35);
		pnlSandBox.add(btnCollapseAll);
		btnCollapseAll.setActionCommand(Actions.BTN_COLLAPSE_ALL.name());
		ActionListener btnCollapseActionListener = new AllUserActionListeners();
		btnCollapseAll.addActionListener(btnCollapseActionListener);

		pnlViewRoutes = new JPanel();
		pnlViewRoutes.setBackground(new Color(255, 255, 255));
		pnlViewRoutes.setBorder(
				new TitledBorder(null, "View Bill of Materials Routes",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlViewRoutes.setBounds(10, 423, 1199, 527);
		getContentPane().add(pnlViewRoutes);
		pnlViewRoutes.setLayout(null);

		scrollPaneList = new JScrollPane();
		scrollPaneList.setBounds(26, 21, 307, 481);
		pnlViewRoutes.add(scrollPaneList);

		routeListModel = new CustomRouteJListModel(getListOfRoutes());
		routeList = new JList<TblBomRoute>(routeListModel);
		routeList.setFixedCellHeight(25);
		routeList.setBackground(SystemColor.control);
		routeList.setCellRenderer(new RouteListCellRenderer());
		ListSelectionListener myListener = new RouteListListener();
		routeList.addListSelectionListener(myListener);
		scrollPaneList.setViewportView(routeList);

		routeJTree = new JTree();
		routeJTree.setFont(new Font("Tahoma", Font.PLAIN, 12));
		routeJTree.setModel(setRouteJTreeModel());
		routeJTree.setCellRenderer(new userRendererJTree());

		scrollPaneRoute = new JScrollPane();
		scrollPaneRoute.setBounds(354, 22, 715, 201);
		scrollPaneRoute.setViewportView(routeJTree);
		pnlViewRoutes.add(scrollPaneRoute);

		lblMachineStateIcon = new JLabel("-");
		lblMachineStateIcon.setBounds(1105, 42, 35, 35);
		pnlViewRoutes.add(lblMachineStateIcon);

		/** SET ALL TREE ICONS EMPTY **/
		setEmptyTreeIcons();
	}

	/** SET MACHINE STATUS LABEL ICON **/
	private void setMachineStatusIcon() {
		boolean isFaultFound = getMachineStateOfBomRoute();
		try {
			if (isFaultFound) {
				lblMachineStateIcon.setIcon(new ImageIcon(BomRouteSetup.class
						.getClassLoader().getResource(AppConstants.RED_BLINK)));
			} else {
				lblMachineStateIcon.setIcon(
						new ImageIcon(BomRouteSetup.class.getClassLoader()
								.getResource(AppConstants.GREEN_BLINK)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** RETRIEVE MACHINES STATE OF BOM ROUTE **/
	private boolean getMachineStateOfBomRoute() {
		ArrayList<TblMachines> itemsList = new ArrayList<>();
		try {
			itemsList = daoMachinesObject
					.getMachineStatusOfBomRoute(SANDBOX_GROUP_ID);
			isFaultyMachineFound = false;
			for (int item = 0; item < itemsList.size(); item++) {
				if (itemsList.get(item)
						.getMachineOperatingStatusID() == AppConstants.MAINTENANCE) {
					isFaultyMachineFound = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFaultyMachineFound;
	}

	/** RETRIEVE MODEL FOR LIST of ALL ROUTES **/
	private ArrayList<TblBomRoute> getListOfRoutes() {
		ArrayList<TblBomRoute> itemsList = new ArrayList<>();
		try {
			itemsList = daoBomRouteObject.getAllListOfRoutes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	/** CHECK NEW BOM ROUTE ALREADY EXIST OR NOT **/
	private boolean isBomRouteAlreadyExist() {
		boolean isRouteAlreadyExist = false;
		try {
			getSelectedComboBoxItemsID();
			isRouteAlreadyExist = daoBomRouteObject.isBomRouteAlreadyExist(
					endItemStockID, inFeedStockID, selectedMachineID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isRouteAlreadyExist;
	}

	/** JLIST SELEECTED ITEM LISTENER **/
	private class RouteListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				int selectedIndex = routeList.getSelectedIndex();
				if (selectedIndex != -1) {
					TblBomRoute selectedListValue = routeList
							.getSelectedValue();
					SANDBOX_GROUP_ID = selectedListValue.getRouteGroupID();
					routeJTree.setModel(setRouteJTreeModel());
					expandAllNodes(routeJTree);
					setMachineStatusIcon();
				}
			}
		}
	}

	/** RETRIEVE LIST OF ALL STOCK CODES **/
	private ArrayList<String> getAllListStockCodes() {
		ArrayList<String> itemsList = new ArrayList<>();
		try {
			ArrayList<TblBomRoute> stockesArray = daoBomRouteObject
					.getAllStockInfo(1, "");
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
			ArrayList<TblBomRoute> machinesArray = daoBomRouteObject
					.getAllMachineInfo(1, "");
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
			ArrayList<TblBomRoute> machinesArray = daoBomRouteObject
					.getAllMachineInfo(2,
							cmboBoxMachineName.getSelectedItem().toString());
			for (int item = 0; item < machinesArray.size(); item++) {
				selectedMachineID = machinesArray.get(item).getMachineID();
			}
			ArrayList<TblBomRoute> stockesArrayEndItem = daoBomRouteObject
					.getAllStockInfo(2,
							cmbBoxEndItem.getSelectedItem().toString());
			for (int item = 0; item < stockesArrayEndItem.size(); item++) {
				endItemStockID = stockesArrayEndItem.get(item).getStockID();
			}
			ArrayList<TblBomRoute> stockesArrayInFeedItem = daoBomRouteObject
					.getAllStockInfo(2,
							cmboBoxInFeedItem.getSelectedItem().toString());
			for (int item = 0; item < stockesArrayInFeedItem.size(); item++) {
				inFeedStockID = stockesArrayInFeedItem.get(item).getStockID();
				previousComboBoxInFeedSelectedItem = cmboBoxInFeedItem
						.getSelectedItem().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** SUBMIT BOM ROUTE BUTTON ACTION **/
	private void addRecordsBomRoute() {
		try {
			int endItem, inFeedItem, machineItem, sandBoxGroupIdItem;
			String routeNameItem;
			ArrayList<TblBomSandbox> allSandboxRecordsxArray = daoSandboxObject
					.fetchAllSandboxRoutes();
			for (int item = 0; item < allSandboxRecordsxArray.size(); item++) {
				endItem = allSandboxRecordsxArray.get(item).getEndItemID();
				inFeedItem = allSandboxRecordsxArray.get(item)
						.getInFeedItemID();
				machineItem = allSandboxRecordsxArray.get(item).getMachineID();
				routeNameItem = allSandboxRecordsxArray.get(item)
						.getRouteName();
				sandBoxGroupIdItem = allSandboxRecordsxArray.get(0)
						.getSandboxGroupID();
				daoBomRouteObject.setBomRoute(endItem, inFeedItem, machineItem,
						routeNameItem, sandBoxGroupIdItem, true);
			}
			MessageWindow.showMessage("New route has been added successfully!",
					MessageType.INFORMATION);
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
	private ImageIcon setImageIcon(String path, int width, int height) {
		Image image = null;
		ImageIcon icon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(path);
			image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}

	/** COLLAPSE/CLOSE ALL OPEN JTREE NODES **/
	private void closeAllOpenNodes(JTree tree, DefaultMutableTreeNode node) {
		Enumeration<?> enumeration = node.depthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) enumeration
					.nextElement();
			if (currentNode != node
					&& tree.isExpanded(new TreePath(currentNode.getPath()))) {
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
			if (previousComboBoxInFeedSelectedItem != null) {
				cmbBoxEndItem
						.setSelectedItem(previousComboBoxInFeedSelectedItem);
			}
			cmbBoxEndItem.setEnabled(false);
		} else {
			cmbBoxEndItem.setEnabled(true);
		}
	}

	/** ADD TO SANDBOX BUTTON ACTION COMMAND **/
	private void addToSandboxJTree() {
		try {
			getSelectedComboBoxItemsID();
			SANDBOX_ROOT_NAME = cmbBoxEndItem.getSelectedItem().toString() + "_"
					+ cmboBoxMachineName.getSelectedItem().toString();
			ArrayList<TblBomSandbox> sandboxParentArray = daoSandboxObject
					.getSandboxParentStockCode();
			if (sandboxParentArray.size() == 0
					&& chckBoxAddChild.isSelected()) {
				MessageWindow.showMessage(
						"No parent record found against this entry!",
						MessageType.ERROR);
			} else if (sandboxParentArray.size() != 0
					&& !chckBoxAddChild.isSelected()) {
				MessageWindow.showMessage(
						"Orphan entries are not allowed! You must select this entry as a child!",
						MessageType.ERROR);
			} else if (chckBoxAddChild.isSelected()) {
				daoSandboxObject.setSandboxRoute(endItemStockID, inFeedStockID,
						selectedMachineID, chckBoxAddChild.isSelected(),
						sandboxParentArray.get(0).getInFeedItemID(), "");
				previousComboBoxInFeedSelectedItem = cmboBoxInFeedItem
						.getSelectedItem().toString();
				cmbBoxEndItem
						.setSelectedItem(previousComboBoxInFeedSelectedItem);
				MessageWindow.showMessage("Record successfully inserted!",
						MessageType.INFORMATION);
			} else {
				daoSandboxObject.setSandboxRoute(endItemStockID, inFeedStockID,
						selectedMachineID, chckBoxAddChild.isSelected(), 0,
						SANDBOX_ROOT_NAME);
				MessageWindow.showMessage("Record successfully inserted!",
						MessageType.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** SET BOM ROUTE JTREE MODEL **/
	private DefaultTreeModel setRouteJTreeModel() {
		routeJTreeRootNode = new DefaultMutableTreeNode(
				AppConstants.BOM_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(routeJTreeRootNode);
		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		try {
			ArrayList<TblBomRoute> routeArray = daoBomRouteObject
					.fetchAllBomRoutes(SANDBOX_GROUP_ID);
			int routeTreeDepth = routeArray.size();
			ArrayList<String> customItems = new ArrayList<>(routeArray.size());
			for (int item = 0; item < routeArray.size(); item++) {
				customItems.add(routeArray.get(item).getInFeedStockCode());
			}
			if (routeTreeDepth != 0) {
				rootNodeName = routeArray.get(0).getRouteName();
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				routeJTreeRootNode.add(rootNode);
				populate(rootNode, customItems, 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** SET RECURSIVE SANDBOX JTREE POPULATE RECORDS **/
	protected void populate(DefaultMutableTreeNode parent,
			ArrayList<String> items, int index) {
		if (index >= items.size()) {
			return;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				items.get(index));
		parent.add(node);
		populate(node, items, ++index);
	}

	/** SET SANDBOX JTREE MODEL **/
	private DefaultTreeModel setSandboxJTreeModel() {
		sandboxJTreeRootNode = new DefaultMutableTreeNode(
				AppConstants.SANDBOX_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(sandboxJTreeRootNode);
		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		try {
			ArrayList<TblBomSandbox> sandboxArray = daoSandboxObject
					.fetchAllSandboxRoutes();
			int sandboxTreeDepth = sandboxArray.size();
			ArrayList<String> customItems = new ArrayList<>(
					sandboxArray.size());
			for (int item = 0; item < sandboxArray.size(); item++) {
				customItems.add(sandboxArray.get(item).getInFeedItemName());
			}
			if (sandboxTreeDepth != 0) {
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

	/** EXPAND ALL NODES OF JTREE **/
	private void expandAllNodes(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel()
				.getRoot();
		expandAll(tree, new TreePath(root));
	}

	/** SET NODES EXAPND STATUS OF JTREE **/
	private void expandAll(JTree tree, TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node
					.getChildAt(i);
			TreePath childPath = path.pathByAddingChild(childNode);
			expandAll(tree, childPath);
		}
		try {
			tree.expandPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** CHANGE SANDBOX JTREE DEFAULT ICONS **/
	private class userRendererJTree extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
					row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			String nodeValue = node.getUserObject().toString();
			if (AppConstants.BOM_TREE_NAME == nodeValue) {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_ROOT, 12, 12));
			} else {
				if (leaf) {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_LEAF, 10,
							10));
				} else if (expanded) {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_EXPAND, 10,
							10));
				} else {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_RIGHT, 10,
							10));
				}
			}
			return this;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.BLACK);
			g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
		}
	}

	/** ROUTE JLIST CELL RENDERER **/
	class RouteListCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;
		private final Border separatorBorder = BorderFactory
				.createMatteBorder(0, 0, 1, 0, Color.BLACK);

		@Override
		public Component getListCellRendererComponent(JList<?> list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value instanceof TblBomRoute) {
				TblBomRoute item = (TblBomRoute) value;
				setText(item.getRouteName());
			}
			if (index < list.getModel().getSize() - 1) {
				setBorder(separatorBorder);
			} else {
				setBorder(null);
			}
			return this;
		}
	}

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class AllUserActionListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					try {
						if (e.getActionCommand() == Actions.CHKBOX_ADD_SANDBOX
								.name()) {
							setChildBoxCheckBoxAction();
						} else if (e
								.getActionCommand() == Actions.BTN_ADD_SANDBOX
										.name()) {
							if (isBomRouteAlreadyExist()) {
								MessageWindow.showMessage(
										"Route already exist! You are not allowed to add duplicate route!",
										MessageType.ERROR);
							} else {
								addToSandboxJTree();
								clearAllTreeItems(sandboxJTree);
								sandboxJTree.setModel(setSandboxJTreeModel());
								expandAllNodes(sandboxJTree);
							}
						} else if (e
								.getActionCommand() == Actions.BTN_COLLAPSE_ALL
										.name()) {
							closeAllOpenNodes(sandboxJTree,
									sandboxJTreeRootNode);
						} else if (e.getActionCommand() == Actions.BTN_ADD_ROUTE
								.name()) {
							int userResponse = MessageWindow
									.createConfirmDialogueWindow(
											"Are you sure you want to add this new route ?",
											" Confirm action");
							if (userResponse == 0) {
								addRecordsBomRoute();
								daoSandboxObject.deleteAllTblSandboxRecords();
								clearAllTreeItems(sandboxJTree);
								sandboxJTree.setModel(setSandboxJTreeModel());
								routeListModel.refreshData();
							}
						} else if (e.getActionCommand() == Actions.CANCEL
								.name()) {
							int userResponse = MessageWindow
									.createConfirmDialogueWindow(
											"Are you sure you want to cancel this ?",
											" Confirm action");
							if (userResponse == 0) {
								daoSandboxObject.deleteAllTblSandboxRecords();
								clearAllTreeItems(sandboxJTree);
								sandboxJTree.setModel(setSandboxJTreeModel());
							}
						}
					} catch (Exception expt) {
						expt.printStackTrace();
					}
				}
			});
		}
	}

	/** MODEL OF ROUTE JLIST **/
	class CustomRouteJListModel extends AbstractListModel<TblBomRoute> {

		private static final long serialVersionUID = 1L;
		private ArrayList<TblBomRoute> items;

		public CustomRouteJListModel(ArrayList<TblBomRoute> items) {
			this.items = items;
		}

		public void refreshData() {
			try {
				items = daoBomRouteObject.getAllListOfRoutes();
				fireContentsChanged(this, 0, getSize() - 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getSize() {
			return items.size();
		}

		@Override
		public TblBomRoute getElementAt(int index) {
			return items.get(index);
		}
	}
}
