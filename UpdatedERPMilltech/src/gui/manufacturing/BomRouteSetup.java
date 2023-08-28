package gui.manufacturing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;
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

public class BomRouteSetup extends JFrame {

	private static final long serialVersionUID = 1L;

	/** COMPONENTS / CONTROLS **/
	private JPanel pnlTop, pnlViewRoutes, pnlSandBox, pnlUpdateTonsPerHour, pnlDeactiveRoutes;
	private JCheckBox chckBoxAddChild, chckbxDeactiveRoute;
	private JFormattedTextField txtFldTonsPerHour, txtFldNewTonsPerHour;
	private JLabel lblEndItem, lblMachineName, lblInFeedItem, lblMachineStateIcon, lblExistingEndFeedItem,
	lblShowExistingEndItem, lblExistingMachineName, lblShowExistingMachineName, lblNewTonsPer,
	lblExistingInFeedItem, lblShowExistingInfeedItem, lblTonsPerHour;
	private JComboBox<String> cmbBoxEndItem, cmboBoxMachineName, cmboBoxInFeedItem;
	private JButton btnCollapseAll, btnAddTree, btnAddSandbox, btnCancel, btnUpdateTonePerHour;
	private ActionListener chckBoxActionListener, addSandboxtreeListener, addBtnTreeListener, addBtnCancelListener,
	btnCollapseActionListener, updateToneListener, chckBoxDeactiveListener;
	private ListSelectionListener myActiveListListener, myInActiveListListener;
	private MouseListener jTreeClickListener;
	private JScrollPane scrollPaneSandBox;
	private JTree sandboxJTree, activeRouteJTree, inActiveRouteTree;
	private JList<TblBomRoute> activeRouteList, deactiveRoutesList;
	private JScrollPane scrollPaneList, scrollPaneRoute, scrollPaneListDeactiveRoutes, scrollPaneInActive;
	private DefaultMutableTreeNode activeRouteJTreeRootNode, inActiveRouteJTreeRootNode, sandboxJTreeRootNode;
	private NumberFormatter tonsPerHourFormatter;
	private DecimalFormat _numberFormat;

	/** VARIABLES **/
	private int endItemStockID = 0, inFeedStockID = 0, selectedMachineID = 0;
	private boolean isFaultyMachineFound = false;
	private String previousComboBoxInFeedSelectedItem = null;
	static String SANDBOX_ROOT_NAME = null;
	static final String FIRST_CONCAT_PART = " @ ";
	static final String SECOND_CONCAT_PART = " --- ";
	static int ACTIVE_SANDBOX_GROUP_ID = -1, INACTIVE_SANDBOX_GROUP_ID = -1, EXTRACTED_ROUTE_ID_FROM_ROUTEJTREE = -1;;

	/** USER ALERT MESSAGES **/
	private final String OK_NEW_RECORD_SAVE_ALERT = " New record successfully inserted! ";
	private final String OK_UPDATE_RECORD_ALERT = "Record updated successfully";
	private final String ERROR_SANDBOX_ORPHAN_ENTRIES_ALERT = "Orphan entries are not allowed! You must select this entry as a child!";
	private final String ERROR_SANDBOX_PARENT_NOT_FOUND_ALERT = "No parent record found against this entry!";
	private final String ERROR_DUPLICATE_RECORD_ALERT = "Route already exist! You are not allowed to add duplicate route!";
	private final String ERROR_UPDATE_BOM_ROUTE_ID_ALERT = "Something went wrong! Record not updated!";
	private final String CONFIRM_BOM_UPDATE_TONS_ALERT = " Are you sure you want to update thsi record ? ";
	private final String CONFIRM_BOM_NEW_ROUTE_ALERT = " Are you sure you want to add this as new route ? ";
	private final String CONFIRM_BOM_ROUTE_DEACTIVE_ALERT = " Are you sure you want to deactivate this route ? ";

	/** CLASSES OBJECTS **/
	DaoBomRoute daoBomRouteObject;
	DaoBomSandbox daoSandboxObject;
	DaoMachines daoMachinesObject;
	ActiveListModel activeListModel;
	InActiveListModel inActiveListModel;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum Actions {
		CHKBOX_ADD_SANDBOX, CHKBOX_DEACTIVE, BTN_ADD_SANDBOX, BTN_ADD_ROUTE, BTN_COLLAPSE_ALL, CANCEL,
		UPDATE_TONE_PER_HOUR
	}

	/** DEFAULT CONSTRUCTOR - STARTUP POINT **/
	public BomRouteSetup() {

		/** FRAME PROPERTIES **/
		this.setTitle("Setup Bill of Materials Routes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1651, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoBomRouteObject = new DaoBomRoute();
		daoSandboxObject = new DaoBomSandbox();
		daoMachinesObject = new DaoMachines();

		/** SETUP GUI **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/** CREATE AND SETUP GUI **/
	private void createAndShowGUI() {

		pnlTop = new JPanel();
		pnlTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTop.setBounds(10, 11, 1191, 161);
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
		chckBoxAddChild.setBounds(1019, 38, 130, 23);
		chckBoxAddChild.setActionCommand(Actions.CHKBOX_ADD_SANDBOX.name());
		chckBoxActionListener = new AllUserActionListeners();
		chckBoxAddChild.addActionListener(chckBoxActionListener);
		pnlTop.add(chckBoxAddChild);

		btnAddSandbox = new JButton("Add to sandbox");
		btnAddSandbox.setActionCommand(Actions.BTN_ADD_SANDBOX.name());
		addSandboxtreeListener = new AllUserActionListeners();
		btnAddSandbox.addActionListener(addSandboxtreeListener);
		btnAddSandbox.setBounds(1019, 97, 150, 35);
		pnlTop.add(btnAddSandbox);

		lblTonsPerHour = new JLabel("Tons Per Hour:");
		lblTonsPerHour.setBounds(485, 97, 93, 14);
		pnlTop.add(lblTonsPerHour);

		_numberFormat = new DecimalFormat("#0.0");
		tonsPerHourFormatter = new NumberFormatter(_numberFormat);
		tonsPerHourFormatter.setValueClass(Float.class);
		tonsPerHourFormatter.setAllowsInvalid(false);
		tonsPerHourFormatter.setMinimum(0.0f);
		txtFldTonsPerHour = new JFormattedTextField(tonsPerHourFormatter);
		txtFldTonsPerHour.setText("0.0");
		txtFldTonsPerHour.setColumns(3);
		txtFldTonsPerHour.setBounds(599, 93, 238, 22);
		pnlTop.add(txtFldTonsPerHour);

		pnlSandBox = new JPanel();
		pnlSandBox.setBorder(
				new TitledBorder(null, "Sandbox of Routes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlSandBox.setBounds(10, 183, 1191, 229);
		getContentPane().add(pnlSandBox);
		pnlSandBox.setLayout(null);

		scrollPaneSandBox = new JScrollPane();
		scrollPaneSandBox.setBounds(10, 25, 729, 193);
		pnlSandBox.add(scrollPaneSandBox);

		sandboxJTree = new JTree();
		sandboxJTree.setModel(setSandboxJTreeModel());
		sandboxJTree.setCellRenderer(new userRendererJTree());
		scrollPaneSandBox.setViewportView(sandboxJTree);

		btnAddTree = new JButton("Add to route");
		btnAddTree.setBounds(840, 183, 150, 35);
		btnAddTree.setActionCommand(Actions.BTN_ADD_ROUTE.name());
		addBtnTreeListener = new AllUserActionListeners();
		btnAddTree.addActionListener(addBtnTreeListener);
		pnlSandBox.add(btnAddTree);

		btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand(Actions.CANCEL.name());
		addBtnCancelListener = new AllUserActionListeners();
		btnCancel.addActionListener(addBtnCancelListener);
		btnCancel.setBounds(1019, 183, 150, 35);
		pnlSandBox.add(btnCancel);

		btnCollapseAll = new JButton("Collapse all");
		btnCollapseAll.setBounds(1019, 25, 150, 35);
		pnlSandBox.add(btnCollapseAll);
		btnCollapseAll.setActionCommand(Actions.BTN_COLLAPSE_ALL.name());
		btnCollapseActionListener = new AllUserActionListeners();
		btnCollapseAll.addActionListener(btnCollapseActionListener);

		pnlViewRoutes = new JPanel();
		pnlViewRoutes.setBackground(new Color(255, 255, 255));
		pnlViewRoutes.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"View All Active Routes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlViewRoutes.setBounds(10, 423, 1191, 527);
		getContentPane().add(pnlViewRoutes);
		pnlViewRoutes.setLayout(null);

		scrollPaneList = new JScrollPane();
		scrollPaneList.setBounds(10, 23, 415, 493);
		pnlViewRoutes.add(scrollPaneList);

		activeListModel = new ActiveListModel(getListOfRoutes(true));
		activeRouteList = new JList<TblBomRoute>(activeListModel);
		activeRouteList.setFixedCellHeight(25);
		activeRouteList.setBackground(SystemColor.control);
		activeRouteList.setBorder(new EmptyBorder(0, 5, 0, 5));
		activeRouteList.setCellRenderer(new RouteListCellRenderer());
		myActiveListListener = new ActiveRouteListListener();
		activeRouteList.addListSelectionListener(myActiveListListener);
		scrollPaneList.setViewportView(activeRouteList);

		lblMachineStateIcon = new JLabel("-");
		lblMachineStateIcon.setBounds(1124, 74, 40, 40);
		pnlViewRoutes.add(lblMachineStateIcon);

		scrollPaneRoute = new JScrollPane();
		scrollPaneRoute.setBounds(435, 23, 663, 312);
		scrollPaneRoute.setViewportBorder(new LineBorder(new Color(255, 255, 255)));
		pnlViewRoutes.add(scrollPaneRoute);

		activeRouteJTree = new JTree();
		activeRouteJTree.setFont(new Font("Tahoma", Font.PLAIN, 12));
		activeRouteJTree.setRowHeight(25);
		activeRouteJTree.setModel(setActiveRouteJTreeModel());
		activeRouteJTree.setCellRenderer(new userRendererJTree());
		jTreeClickListener = new routeJTreeMouseClickListener();
		activeRouteJTree.addMouseListener(jTreeClickListener);
		this.clearAllTreeItems(activeRouteJTree);
		scrollPaneRoute.setViewportView(activeRouteJTree);

		pnlUpdateTonsPerHour = new JPanel();
		pnlUpdateTonsPerHour.setBounds(435, 346, 663, 170);
		pnlUpdateTonsPerHour.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Update Route Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlViewRoutes.add(pnlUpdateTonsPerHour);
		pnlUpdateTonsPerHour.setLayout(null);

		lblExistingEndFeedItem = new JLabel("End Item:");
		lblExistingEndFeedItem.setBounds(27, 26, 63, 14);
		pnlUpdateTonsPerHour.add(lblExistingEndFeedItem);

		lblShowExistingEndItem = new JLabel("-");
		lblShowExistingEndItem.setHorizontalAlignment(SwingConstants.LEFT);
		lblShowExistingEndItem.setBounds(122, 26, 187, 14);
		pnlUpdateTonsPerHour.add(lblShowExistingEndItem);

		lblExistingInFeedItem = new JLabel("In-Feed Item:");
		lblExistingInFeedItem.setBounds(319, 26, 93, 14);
		pnlUpdateTonsPerHour.add(lblExistingInFeedItem);

		lblShowExistingInfeedItem = new JLabel("-");
		lblShowExistingInfeedItem.setHorizontalAlignment(SwingConstants.LEFT);
		lblShowExistingInfeedItem.setBounds(435, 26, 187, 14);
		pnlUpdateTonsPerHour.add(lblShowExistingInfeedItem);

		lblExistingMachineName = new JLabel("Machine Name:");
		lblExistingMachineName.setBounds(27, 68, 84, 14);
		pnlUpdateTonsPerHour.add(lblExistingMachineName);

		lblShowExistingMachineName = new JLabel("-");
		lblShowExistingMachineName.setHorizontalAlignment(SwingConstants.LEFT);
		lblShowExistingMachineName.setBounds(122, 68, 187, 14);
		pnlUpdateTonsPerHour.add(lblShowExistingMachineName);

		lblNewTonsPer = new JLabel("Tons Per Hour:");
		lblNewTonsPer.setBounds(319, 68, 107, 14);
		pnlUpdateTonsPerHour.add(lblNewTonsPer);

		txtFldNewTonsPerHour = new JFormattedTextField(tonsPerHourFormatter);
		txtFldNewTonsPerHour.setHorizontalAlignment(SwingConstants.LEFT);
		txtFldNewTonsPerHour.setText("0.0");
		txtFldNewTonsPerHour.setColumns(3);
		txtFldNewTonsPerHour.setBounds(435, 64, 187, 22);
		pnlUpdateTonsPerHour.add(txtFldNewTonsPerHour);

		btnUpdateTonePerHour = new JButton("Update Record");
		updateToneListener = new AllUserActionListeners();
		btnUpdateTonePerHour.addActionListener(updateToneListener);
		btnUpdateTonePerHour.setActionCommand(Actions.UPDATE_TONE_PER_HOUR.name());
		btnUpdateTonePerHour.setBounds(472, 114, 150, 35);
		pnlUpdateTonsPerHour.add(btnUpdateTonePerHour);

		chckbxDeactiveRoute = new JCheckBox("Deactivate whole route");
		chckbxDeactiveRoute.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckBoxDeactiveListener = new AllUserActionListeners();
		chckbxDeactiveRoute.addActionListener(chckBoxDeactiveListener);
		chckbxDeactiveRoute.setActionCommand(Actions.CHKBOX_DEACTIVE.name());
		chckbxDeactiveRoute.setBounds(27, 126, 176, 23);
		pnlUpdateTonsPerHour.add(chckbxDeactiveRoute);

		pnlDeactiveRoutes = new JPanel();
		pnlDeactiveRoutes.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"View All Deactive Routes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlDeactiveRoutes.setBounds(1211, 11, 414, 939);
		getContentPane().add(pnlDeactiveRoutes);
		pnlDeactiveRoutes.setLayout(null);

		scrollPaneListDeactiveRoutes = new JScrollPane();
		scrollPaneListDeactiveRoutes.setBounds(10, 21, 394, 538);
		pnlDeactiveRoutes.add(scrollPaneListDeactiveRoutes);

		inActiveListModel = new InActiveListModel(getListOfRoutes(false));
		deactiveRoutesList = new JList<TblBomRoute>(inActiveListModel);
		deactiveRoutesList.setFixedCellHeight(25);
		deactiveRoutesList.setBackground(SystemColor.control);
		deactiveRoutesList.setBorder(new EmptyBorder(0, 5, 0, 5));
		deactiveRoutesList.setCellRenderer(new RouteListCellRenderer());
		myInActiveListListener = new InActiveRouteListListener();
		deactiveRoutesList.addListSelectionListener(myInActiveListListener);
		scrollPaneListDeactiveRoutes.setViewportView(deactiveRoutesList);

		scrollPaneInActive = new JScrollPane();
		scrollPaneInActive.setBounds(10, 571, 394, 357);
		pnlDeactiveRoutes.add(scrollPaneInActive);

		inActiveRouteTree = new JTree();
		inActiveRouteTree.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inActiveRouteTree.setRowHeight(25);
		inActiveRouteTree.setModel(setInActiveRouteJTreeModel());
		inActiveRouteTree.setCellRenderer(new userRendererJTree());
		this.clearAllTreeItems(inActiveRouteTree);
		scrollPaneInActive.setViewportView(inActiveRouteTree);

		/** SET ALL TREE ICONS EMPTY **/
		setEmptyTreeIcons();

		/** DELETE ALL RECORDS OF SANDBOX ROUTE **/
		deleteAllSandboxRecords();
	}

	/** UPDATE TONS PER HOUR VALUE **/
	private boolean updateTonePerHour() {
		boolean isUpdated = false;
		try {
			isUpdated = daoBomRouteObject.updateTonePerHour(Double.parseDouble(txtFldNewTonsPerHour.getText()),
					EXTRACTED_ROUTE_ID_FROM_ROUTEJTREE);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	/** SET MACHINE STATUS LABEL ICON **/
	private void setMachineStatusIcon() {
		boolean isFaultFound = getMachineStateOfBomRoute();
		try {
			if (isFaultFound) {
				lblMachineStateIcon.setIcon(
						new ImageIcon(BomRouteSetup.class.getClassLoader().getResource(AppConstants.RED_BLINK)));
			} else {
				lblMachineStateIcon.setIcon(
						new ImageIcon(BomRouteSetup.class.getClassLoader().getResource(AppConstants.GREEN_BLINK)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** RETRIEVE MACHINES STATE OF BOM ROUTE **/
	private boolean getMachineStateOfBomRoute() {
		ArrayList<TblMachines> itemsList = new ArrayList<>();
		try {
			itemsList = daoMachinesObject.getMachineStatusOfBomRoute(ACTIVE_SANDBOX_GROUP_ID);
			isFaultyMachineFound = false;
			for (int item = 0; item < itemsList.size(); item++) {
				if (itemsList.get(item).getMachineOperatingStatusID() == AppConstants.MAINTENANCE) {
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
	private ArrayList<TblBomRoute> getListOfRoutes(boolean activeRecords) {
		ArrayList<TblBomRoute> itemsList = new ArrayList<>();
		try {
			itemsList = daoBomRouteObject.getAllListOfRoutes(activeRecords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	/** DELETE ALL RECORDS OF SANDBOX ROUTE **/
	private void deleteAllSandboxRecords() {
		try {
			daoSandboxObject.deleteAllTblSandboxRecords();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** CHECK NEW BOM ROUTE ALREADY EXIST OR NOT **/
	private boolean isBomRouteAlreadyExist() {
		boolean isRouteAlreadyExist = false;
		try {
			getSelectedComboBoxItemsID();
			isRouteAlreadyExist = daoBomRouteObject.isBomRouteAlreadyExist(endItemStockID, inFeedStockID,
					selectedMachineID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isRouteAlreadyExist;
	}
	
	/** ACTIVE JLIST SELECTED ITEM LISTENER **/
	private class ActiveRouteListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			{
				if (e.getValueIsAdjusting()) {
					int selectedIndex = activeRouteList.getSelectedIndex();
					if (selectedIndex != -1) {
						TblBomRoute selectedListValue = activeRouteList.getSelectedValue();
						ACTIVE_SANDBOX_GROUP_ID = selectedListValue.getRouteGroupID();
						activeRouteJTree.setModel(setActiveRouteJTreeModel());
						expandAllNodes(activeRouteJTree);
						setMachineStatusIcon();
					}
				}
			}
		}
	}

	/** IN-ACTIVE JLIST SELECTED ITEM LISTENER **/
	private class InActiveRouteListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				int selectedIndex = deactiveRoutesList.getSelectedIndex();
				if (selectedIndex != -1) {
					TblBomRoute selectedListValue = deactiveRoutesList.getSelectedValue();
					INACTIVE_SANDBOX_GROUP_ID = selectedListValue.getRouteGroupID();
					inActiveRouteTree.setModel(setInActiveRouteJTreeModel());
					expandAllNodes(inActiveRouteTree);
				}
			}
		}
	}

	/** RETRIEVE LIST OF ALL STOCK CODES **/
	private ArrayList<String> getAllListStockCodes() {
		ArrayList<String> itemsList = new ArrayList<>();
		try {
			ArrayList<TblBomRoute> stockesArray = daoBomRouteObject.getAllStockInfo(1, "");
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
			ArrayList<TblBomRoute> machinesArray = daoBomRouteObject.getAllMachineInfo(1, "");
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
			ArrayList<TblBomRoute> machinesArray = daoBomRouteObject.getAllMachineInfo(2,
					cmboBoxMachineName.getSelectedItem().toString());
			for (int item = 0; item < machinesArray.size(); item++) {
				selectedMachineID = machinesArray.get(item).getMachineID();
			}
			ArrayList<TblBomRoute> stockesArrayEndItem = daoBomRouteObject.getAllStockInfo(2,
					cmbBoxEndItem.getSelectedItem().toString());
			for (int item = 0; item < stockesArrayEndItem.size(); item++) {
				endItemStockID = stockesArrayEndItem.get(item).getStockID();
			}
			ArrayList<TblBomRoute> stockesArrayInFeedItem = daoBomRouteObject.getAllStockInfo(2,
					cmboBoxInFeedItem.getSelectedItem().toString());
			for (int item = 0; item < stockesArrayInFeedItem.size(); item++) {
				inFeedStockID = stockesArrayInFeedItem.get(item).getStockID();
				previousComboBoxInFeedSelectedItem = cmboBoxInFeedItem.getSelectedItem().toString();
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
			Double tonsPerHour;
			ArrayList<TblBomSandbox> allSandboxRecordsxArray = daoSandboxObject.fetchAllSandboxRoutes();
			for (int item = 0; item < allSandboxRecordsxArray.size(); item++) {
				endItem = allSandboxRecordsxArray.get(item).getEndItemID();
				inFeedItem = allSandboxRecordsxArray.get(item).getInFeedItemID();
				machineItem = allSandboxRecordsxArray.get(item).getMachineID();
				routeNameItem = allSandboxRecordsxArray.get(item).getRouteName();
				tonsPerHour = allSandboxRecordsxArray.get(item).getTonsPerHour();
				sandBoxGroupIdItem = allSandboxRecordsxArray.get(0).getSandboxGroupID();
				if (item == 0) {
					daoBomRouteObject.setBomRoute(endItem, inFeedItem, machineItem, routeNameItem, sandBoxGroupIdItem,
							true, true, tonsPerHour);
				} else {
					daoBomRouteObject.setBomRoute(endItem, inFeedItem, machineItem, routeNameItem, sandBoxGroupIdItem,
							true, false, tonsPerHour);
				}
			}
			MessageWindow.showMessage(OK_NEW_RECORD_SAVE_ALERT, MessageType.INFORMATION);
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
			if (previousComboBoxInFeedSelectedItem != null) {
				cmbBoxEndItem.setSelectedItem(previousComboBoxInFeedSelectedItem);
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
			ArrayList<TblBomSandbox> sandboxParentArray = daoSandboxObject.getSandboxParentStockCode();
			if (sandboxParentArray.size() == 0 && chckBoxAddChild.isSelected()) {
				MessageWindow.showMessage(ERROR_SANDBOX_PARENT_NOT_FOUND_ALERT, MessageType.ERROR);
			} else if (sandboxParentArray.size() != 0 && !chckBoxAddChild.isSelected()) {
				MessageWindow.showMessage(ERROR_SANDBOX_ORPHAN_ENTRIES_ALERT, MessageType.ERROR);
			} else if (chckBoxAddChild.isSelected()) {
				daoSandboxObject.setSandboxRoute(endItemStockID, inFeedStockID, selectedMachineID,
						chckBoxAddChild.isSelected(), sandboxParentArray.get(0).getInFeedItemID(), "",
						Double.parseDouble(txtFldTonsPerHour.getText()));
				previousComboBoxInFeedSelectedItem = cmboBoxInFeedItem.getSelectedItem().toString();
				cmbBoxEndItem.setSelectedItem(previousComboBoxInFeedSelectedItem);
				MessageWindow.showMessage(OK_NEW_RECORD_SAVE_ALERT, MessageType.INFORMATION);
			} else {
				daoSandboxObject.setSandboxRoute(endItemStockID, inFeedStockID, selectedMachineID,
						chckBoxAddChild.isSelected(), 0, SANDBOX_ROOT_NAME,
						Double.parseDouble(txtFldTonsPerHour.getText()));
				MessageWindow.showMessage(OK_NEW_RECORD_SAVE_ALERT, MessageType.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** SET ACTIVE BOM ROUTE JTREE MODEL **/
	private DefaultTreeModel setActiveRouteJTreeModel() {

		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		activeRouteJTreeRootNode = new DefaultMutableTreeNode(AppConstants.ACTIVE_BOM_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(activeRouteJTreeRootNode);
		try {
			ArrayList<TblBomRoute> routeArray = daoBomRouteObject.fetchAllBomRoutes(ACTIVE_SANDBOX_GROUP_ID, 0, 1);
			int routeTreeDepth = routeArray.size();
			ArrayList<String> customItems = new ArrayList<>(routeArray.size());
			for (int item = 0; item < routeArray.size(); item++) {
				if (item != routeTreeDepth - 1) {
					customItems.add(routeArray.get(item).getInFeedStockCode() + FIRST_CONCAT_PART
							+ routeArray.get(item).getMachineName() + SECOND_CONCAT_PART
							+ routeArray.get(item + 1).getTonsPerHour() + "(" + routeArray.get(item + 1).getRouteID()
							+ ")");
				} else {
					customItems.add(routeArray.get(item).getInFeedStockCode());
				}
			}
			if (routeTreeDepth != 0) {
				rootNodeName = routeArray.get(0).getRouteName() + SECOND_CONCAT_PART
						+ routeArray.get(0).getTonsPerHour() + "(" + routeArray.get(0).getRouteID() + ")";
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				activeRouteJTreeRootNode.add(rootNode);
				populateNodes(rootNode, customItems, 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** SET In-ACTIVE BOM ROUTE JTREE MODEL **/
	private DefaultTreeModel setInActiveRouteJTreeModel() {

		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		inActiveRouteJTreeRootNode = new DefaultMutableTreeNode(AppConstants.INACTIVE_BOM_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(inActiveRouteJTreeRootNode);
		try {
			ArrayList<TblBomRoute> routeArray = daoBomRouteObject.fetchAllBomRoutes(INACTIVE_SANDBOX_GROUP_ID, 0, 1);
			int routeTreeDepth = routeArray.size();
			ArrayList<String> customItems = new ArrayList<>(routeArray.size());
			for (int item = 0; item < routeArray.size(); item++) {
				if (item != routeTreeDepth - 1) {
					customItems.add(routeArray.get(item).getInFeedStockCode() + FIRST_CONCAT_PART
							+ routeArray.get(item).getMachineName() + SECOND_CONCAT_PART
							+ routeArray.get(item + 1).getTonsPerHour() + "(" + routeArray.get(item + 1).getRouteID()
							+ ")");
				} else {
					customItems.add(routeArray.get(item).getInFeedStockCode());
				}
			}
			if (routeTreeDepth != 0) {
				rootNodeName = routeArray.get(0).getRouteName() + SECOND_CONCAT_PART
						+ routeArray.get(0).getTonsPerHour() + "(" + routeArray.get(0).getRouteID() + ")";
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				inActiveRouteJTreeRootNode.add(rootNode);
				populateNodes(rootNode, customItems, 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** SET RECURSIVE SANDBOX JTREE POPULATE RECORDS **/
	protected void populateNodes(DefaultMutableTreeNode parent, ArrayList<String> items, int index) {
		if (index >= items.size()) {
			return;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(items.get(index));
		parent.add(node);
		populateNodes(node, items, ++index);
	}

	/** SET SANDBOX JTREE MODEL **/
	private DefaultTreeModel setSandboxJTreeModel() {

		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		sandboxJTreeRootNode = new DefaultMutableTreeNode(AppConstants.SANDBOX_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(sandboxJTreeRootNode);
		try {
			ArrayList<TblBomSandbox> sandboxArray = daoSandboxObject.fetchAllSandboxRoutes();
			int sandboxTreeDepth = sandboxArray.size();
			ArrayList<String> customItems = new ArrayList<>(sandboxArray.size());
			for (int item = 0; item < sandboxArray.size(); item++) {
				if (item != sandboxTreeDepth - 1) {
					customItems.add(sandboxArray.get(item).getInFeedItemName() + FIRST_CONCAT_PART
							+ sandboxArray.get(item).getMachineName() + SECOND_CONCAT_PART
							+ sandboxArray.get(item + 1).getTonsPerHour());
				} else {
					customItems.add(sandboxArray.get(item).getInFeedItemName());
				}
			}
			if (sandboxTreeDepth != 0) {
				rootNodeName = sandboxArray.get(0).getRouteName() + SECOND_CONCAT_PART
						+ sandboxArray.get(0).getTonsPerHour();
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				sandboxJTreeRootNode.add(rootNode);
				populateNodes(rootNode, customItems, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** EXPAND ALL NODES OF JTREE **/
	private void expandAllNodes(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root));
	}

	/** SET NODES EXAPND STATUS OF JTREE **/
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

	/** CHANGE SANDBOX JTREE DEFAULT ICONS **/
	private class userRendererJTree extends DefaultTreeCellRenderer {
		Font boldFont;
		private static final long serialVersionUID = 1L;

		public userRendererJTree() {
			boldFont = new Font("SansSerif", Font.BOLD, 12);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			String nodeValue = node.getUserObject().toString();
			if (row == 0) {
				setFont(boldFont);
			} else {
				setFont(getFont().deriveFont(Font.PLAIN));
			}
			if (AppConstants.ACTIVE_BOM_TREE_NAME == nodeValue) {
				setIcon(setImageIcon(AppConstants.ACTIVE_BOM_ROUTE_ROOT, 12, 12));
			} else if (AppConstants.INACTIVE_BOM_TREE_NAME == nodeValue) {
				setIcon(setImageIcon(AppConstants.INACTIVE_BOM_ROUTE_ROOT, 12, 12));
			} else {
				if (leaf) {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_LEAF, 10, 10));
				} else if (expanded) {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_EXPAND, 10, 10));
				} else {
					setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_RIGHT, 10, 10));
				}
			}
			nodeValue = nodeValue.replaceAll("\\(.*?\\)", "");
			setText(nodeValue.trim());
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
	class RouteListCellRenderer extends JPanel implements ListCellRenderer<TblBomRoute> {

		private static final long serialVersionUID = 1L;
		private final Border separatorBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
		private JLabel serialNumberLabel, valueLabel;

		public RouteListCellRenderer() {
			setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
			serialNumberLabel = new JLabel();
			valueLabel = new JLabel();
			add(serialNumberLabel);
			add(valueLabel);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends TblBomRoute> list, TblBomRoute value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (value instanceof TblBomRoute) {
				TblBomRoute item = (TblBomRoute) value;
				serialNumberLabel.setText(String.valueOf(index + 1));
				valueLabel.setText(item.getRouteName());
			}
			if (index < list.getModel().getSize() - 1) {
				setBorder(separatorBorder);
			} else {
				setBorder(null);
			}
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			return this;
		}
	}

	/** SET COMPONENTS STATES TO DEFAULT VALUES **/
	private void setComponentStatesToDefault() {
		lblShowExistingEndItem.setText("-");
		lblShowExistingInfeedItem.setText("-");
		lblShowExistingMachineName.setText("-");
		txtFldNewTonsPerHour.setText("0.0");
	}

	/** SET DE-ACTIVATE WHOLE BOM ROUTE **/
	private void setDeactiveRoute() {
		try {
			boolean result = false;
			if (chckbxDeactiveRoute.isSelected() && ACTIVE_SANDBOX_GROUP_ID != -1) {
				int userResponse = MessageWindow.createConfirmDialogueWindow(CONFIRM_BOM_ROUTE_DEACTIVE_ALERT,
						"Confirm Action");
				if (userResponse == 0) {
					result = daoBomRouteObject.updateBomRouteStatus(false, ACTIVE_SANDBOX_GROUP_ID);
					if (result) {
						MessageWindow.showMessage(OK_UPDATE_RECORD_ALERT, MessageType.INFORMATION);
						activeListModel.refreshData(true);
						inActiveListModel.refreshData(false);
						clearAllTreeItems(activeRouteJTree);
						chckbxDeactiveRoute.setSelected(false);
						lblMachineStateIcon.setIcon(new EmptyIcon());
					} else {
						MessageWindow.showMessage(ERROR_UPDATE_BOM_ROUTE_ID_ALERT, MessageType.ERROR);
						chckbxDeactiveRoute.setSelected(false);
					}
				} else {
					chckbxDeactiveRoute.setSelected(false);
				}
			}
		} catch (Exception excpt) {
			excpt.printStackTrace();
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
						if (e.getActionCommand() == Actions.CHKBOX_ADD_SANDBOX.name()) {
							setChildBoxCheckBoxAction();
						} else if (e.getActionCommand() == Actions.CHKBOX_DEACTIVE.name()) {
							setDeactiveRoute();
						} else if (e.getActionCommand() == Actions.UPDATE_TONE_PER_HOUR.name()) {
							if (lblShowExistingEndItem.getText() != "-") {
								int userResponse = MessageWindow
										.createConfirmDialogueWindow(CONFIRM_BOM_UPDATE_TONS_ALERT, "Confirm action");
								if (userResponse == 0) {
									if (!updateTonePerHour()) {
										MessageWindow.showMessage(ERROR_UPDATE_BOM_ROUTE_ID_ALERT, MessageType.ERROR);
									} else {
										MessageWindow.showMessage(OK_UPDATE_RECORD_ALERT, MessageType.INFORMATION);
										clearAllTreeItems(activeRouteJTree);
										activeRouteJTree.setModel(setActiveRouteJTreeModel());
										expandAllNodes(activeRouteJTree);
										setComponentStatesToDefault();
									}
								}
							}
						} else if (e.getActionCommand() == Actions.BTN_ADD_SANDBOX.name()) {
							if (isBomRouteAlreadyExist()) {
								MessageWindow.showMessage(ERROR_DUPLICATE_RECORD_ALERT, MessageType.ERROR);
							} else {
								addToSandboxJTree();
								clearAllTreeItems(sandboxJTree);
								sandboxJTree.setModel(setSandboxJTreeModel());
								expandAllNodes(sandboxJTree);
							}
						} else if (e.getActionCommand() == Actions.BTN_COLLAPSE_ALL.name()) {
							closeAllOpenNodes(sandboxJTree, sandboxJTreeRootNode);
						} else if (e.getActionCommand() == Actions.BTN_ADD_ROUTE.name()) {
							if (SANDBOX_ROOT_NAME != null) {
								int userResponse = MessageWindow
										.createConfirmDialogueWindow(CONFIRM_BOM_NEW_ROUTE_ALERT, "Confirm action");
								if (userResponse == 0) {
									addRecordsBomRoute();
									daoSandboxObject.deleteAllTblSandboxRecords();
									clearAllTreeItems(sandboxJTree);
									sandboxJTree.setModel(setSandboxJTreeModel());
									activeListModel.refreshData(true);
								}
							}
						} else if (e.getActionCommand() == Actions.CANCEL.name()) {
							int userResponse = MessageWindow.createConfirmDialogueWindow(
									"Are you sure you want to cancel this ?", " Confirm action");
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

	/** ACTIVE JLIST MODEL **/
	class ActiveListModel extends AbstractListModel<TblBomRoute> {

		private static final long serialVersionUID = 1L;
		private ArrayList<TblBomRoute> items;

		public ActiveListModel(ArrayList<TblBomRoute> items) {
			this.items = items;
		}

		public void refreshData(boolean activeRecords) {
			try {
				items = daoBomRouteObject.getAllListOfRoutes(activeRecords);
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

	/** IN-ACTIVE JLIST MODEL **/
	class InActiveListModel extends AbstractListModel<TblBomRoute> {

		private static final long serialVersionUID = 1L;
		private ArrayList<TblBomRoute> items;

		public InActiveListModel(ArrayList<TblBomRoute> items) {
			this.items = items;
		}

		public void refreshData(boolean activeRecords) {
			try {
				items = daoBomRouteObject.getAllListOfRoutes(activeRecords);
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

	/** EXTRACT BON ROUTE ID FOR UPDATE RECORD FROM BOM ROUTE JTREE **/
	private static int extractBomRouteIDFromTreePath(String input) {
		int startIndex = input.indexOf("(");
		int endIndex = input.indexOf(")");
		if (startIndex >= 0 && endIndex > startIndex) {
			String extractedText = input.substring(startIndex + 1, endIndex);
			try {
				return Integer.parseInt(extractedText.trim());
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		return -1;
	}

	/** IMPLEMENT MOUSE LISTENER FOR BOM ROUTE JTREE **/
	private class routeJTreeMouseClickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
				int selectedRow = activeRouteJTree.getRowForLocation(e.getX(), e.getY());
				try {
					if (selectedRow != -1) {
						TreePath selectedPath = activeRouteJTree.getPathForLocation(e.getX(), e.getY());
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath
								.getLastPathComponent();
						if (selectedNode.isLeaf() || selectedNode.isRoot()) {
							setComponentStatesToDefault();
						} else {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run(){
									try {
										String extractedRouteID = (String) selectedNode.getUserObject();
										EXTRACTED_ROUTE_ID_FROM_ROUTEJTREE = extractBomRouteIDFromTreePath(
												extractedRouteID);
										ArrayList<TblBomRoute> routeArray;
										routeArray = daoBomRouteObject.fetchAllBomRoutes(0,
												EXTRACTED_ROUTE_ID_FROM_ROUTEJTREE, 2);
										lblShowExistingEndItem.setText(routeArray.get(0).getStockCode());
										lblShowExistingInfeedItem.setText(routeArray.get(0).getInFeedStockCode());
										lblShowExistingMachineName.setText(routeArray.get(0).getMachineName());
										txtFldNewTonsPerHour.setText(routeArray.get(0).getTonsPerHour().toString());
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							});
						}
					}
				} catch (IndexOutOfBoundsException indexExp) {
					indexExp.printStackTrace();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
