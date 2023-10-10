package gui.manufacturing;

/**
 * @author Muhammad
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import dao.DaoBomRoute;
import dao.DaoCustomerOrder;
import dao.DaoJobCart;
import entities.TblBomRoute;
import entities.TblCustomerOrder;
import entities.TblJobCart;
import extras.AppConstants;
import extras.LoadResource;
import extras.MessageWindowType;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.JCheckBox;
import javax.swing.border.Border;

public class SetupJob extends JFrame {

	private static final long serialVersionUID = 1L;

	/** COMPONENTS / CONTROLS **/
	private JPanel pnlTop, pnlBottom, panelRight;
	private JTable tblShowRecords, tblJobCart;
	private DefaultTableModel ShowRecordsTableModel, jobCartTableModel;
	private JLabel lblSelectBomRoute, lblQuantityToMake, lblJobNotes, lblmax, lblshowtotalQty;
	private JTree treeBomRoute;
	private JCheckBox chckbxASAP;
	private JTextPane textPaneJobNotes;
	private JFormattedTextField textFieldQuantity;
	private NumberFormatter _quantityFormatter;
	private DecimalFormat _numberFormat;
	private JButton btnViewDetails, btnCreateNewJob, btnViewUnattendedJobs;
	private JScrollPane scrollPaneRouteJTree, scrollPaneJobNotes, scrollPaneShowRecords, scrollPaneJobCart;
	private DefaultMutableTreeNode routeJTreeRootNode;
	private JComboBox<TblBomRoute> cmboBoxShowBomroute;
	private ActionListener detailListener, setupJobListener, viewJobsActionListener;

	/** VARIABLES **/
	private final int maxNumberOfCharacters = 100;
	private int sandboxGroupID = -1, selectedRouteID = -1;
	public static final String FIRST_CONCAT_PART = " @ ";
	public static final String SECOND_CONCAT_PART = " || ";
	public static Boolean allow_ = true;
	static int UNIQUE_COLUMN_NO = 10;
	private int orderID, stockID, bomRouteID;

	/** CLASSES OBJECTS **/

	private DaoBomRoute daoBomRouteObject;
	private UnattendedJobs viewUnattendedJobsObject;
	private DaoCustomerOrder daoCustomerOrderObject;
	private DaoJobCart daoJobCartObject;

	private String showRecordsTblColNames[] = { "S. No", "Order No", "Customer Name", "End Item", "Order Qty",
			"On Hand Qty", "Customer Notes", "Order Date", "Exp. Delivery Date", "Select", "OrderID" };
	private String jobCartTblColNames[] = { "Order No", "Order Qty" };

	private String INFO_ALERT_MESSAGE = "No records found against this input!";

	/** USER ALERTS MESSAGES **/
	private final String OK_NEW_RECORD_SAVE_ALERT = " New Job created successfully! ";
	private final String CONFIRM_CREATE_NEW_JOB_ALERT = " Are you sure you want to add this new job? ";

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_VIEW_DETAILS, CREATE_NEW_JOB, BTN_VIEW_UNATTENDED_JOBS
	}

	/** CONSTRUCTOR & METHOD INVOKE **/
	public SetupJob() {

		/** CLASSES OBJECTS INITIALIZATION **/
		daoBomRouteObject = new DaoBomRoute();
		daoCustomerOrderObject = new DaoCustomerOrder();
		daoJobCartObject = new DaoJobCart();

		/** MAIN FRAME METHOD INVOKE **/
		this.mainFrameProperties();

		/** SETUP GUI **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/** SETUP MAIN FRAME PROPERTIES **/
	private void mainFrameProperties() {

		this.setTitle("Setup Job");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(setFrameBannerIcon());
		setBounds(100, 100, 1565, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
	}

	/** CREATE & SETUP GUI **/
	private void createAndShowGUI() {

		pnlTop = new JPanel();
		pnlTop.setBorder(new TitledBorder(null, "Setup Job", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlTop.setBounds(10, 11, 1296, 296);
		getContentPane().add(pnlTop);
		pnlTop.setLayout(null);

		lblSelectBomRoute = new JLabel("Select Job:");
		lblSelectBomRoute.setBounds(35, 45, 63, 14);
		pnlTop.add(lblSelectBomRoute);

		cmboBoxShowBomroute = new JComboBox<>();
		cmboBoxShowBomroute.setBounds(108, 37, 346, 28);
		AutoCompleteDecorator.decorate(cmboBoxShowBomroute);
		pnlTop.add(cmboBoxShowBomroute);
		bindComboBox(cmboBoxShowBomroute, getAllBomRoutes());

		btnViewDetails = new JButton("View Details");
		btnViewDetails.setBounds(479, 37, 109, 28);
		detailListener = new AllUserActionListeners();
		btnViewDetails.addActionListener(detailListener);
		btnViewDetails.setActionCommand(UserActions.BTN_VIEW_DETAILS.name());
		pnlTop.add(btnViewDetails);

		scrollPaneRouteJTree = new JScrollPane();
		scrollPaneRouteJTree.setBounds(38, 79, 550, 188);
		pnlTop.add(scrollPaneRouteJTree);

		treeBomRoute = new JTree();
		treeBomRoute.setFont(new Font("Tahoma", Font.PLAIN, 12));
		treeBomRoute.setRowHeight(25);
		treeBomRoute.setModel(setRouteJTreeModel());
		treeBomRoute.setCellRenderer(new UserRendererJTree());
		JTreeConfig.clearAllTreeItems(treeBomRoute);
		scrollPaneRouteJTree.setViewportView(treeBomRoute);

		lblQuantityToMake = new JLabel("Quantity (tons):");
		lblQuantityToMake.setBounds(669, 41, 103, 14);
		pnlTop.add(lblQuantityToMake);

		_numberFormat = new DecimalFormat("#0.0");
		_quantityFormatter = new NumberFormatter(_numberFormat);
		_quantityFormatter.setValueClass(Float.class);
		_quantityFormatter.setAllowsInvalid(false);
		_quantityFormatter.setMinimum(0.0f);
		textFieldQuantity = new JFormattedTextField(_quantityFormatter);
		textFieldQuantity.setText("0.0");
		textFieldQuantity.setColumns(3);
		textFieldQuantity.setBounds(773, 38, 266, 28);
		pnlTop.add(textFieldQuantity);

		lblJobNotes = new JLabel("Job Notes:");
		lblJobNotes.setBounds(669, 79, 94, 14);
		pnlTop.add(lblJobNotes);

		scrollPaneJobNotes = new JScrollPane();
		scrollPaneJobNotes.setBounds(773, 79, 266, 188);
		pnlTop.add(scrollPaneJobNotes);

		textPaneJobNotes = new CharacterLimitTextPane(maxNumberOfCharacters);
		scrollPaneJobNotes.setViewportView(textPaneJobNotes);

		btnCreateNewJob = new JButton("Create New job");
		btnCreateNewJob.setIcon(LoadResource.getImageIconFromImage(AppConstants.PLUS, 15, 15));
		btnCreateNewJob.setIconTextGap(10);
		setupJobListener = new AllUserActionListeners();
		btnCreateNewJob.addActionListener(setupJobListener);
		btnCreateNewJob.setActionCommand(UserActions.CREATE_NEW_JOB.name());
		btnCreateNewJob.setBounds(1084, 189, 184, 80);
		pnlTop.add(btnCreateNewJob);

		lblmax = new JLabel("(max 100)");
		lblmax.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblmax.setBounds(672, 95, 49, 14);
		pnlTop.add(lblmax);

		chckbxASAP = new JCheckBox("ASAP");
		chckbxASAP.setBounds(1084, 41, 63, 23);
		pnlTop.add(chckbxASAP);

		btnViewUnattendedJobs = new JButton("View Unattended Jobs");
		btnViewUnattendedJobs.setIcon(LoadResource.getImageIconFromImage(AppConstants.VIEW, 15, 15));
		btnViewUnattendedJobs.setIconTextGap(10);
		viewJobsActionListener = new AllUserActionListeners();
		btnViewUnattendedJobs.addActionListener(viewJobsActionListener);
		btnViewUnattendedJobs.setActionCommand(UserActions.BTN_VIEW_UNATTENDED_JOBS.name());
		btnViewUnattendedJobs.setBounds(1084, 82, 184, 80);
		pnlTop.add(btnViewUnattendedJobs);

		pnlBottom = new JPanel();
		pnlBottom.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"View Customer Orders", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlBottom.setBounds(10, 318, 1296, 632);
		getContentPane().add(pnlBottom);
		pnlBottom.setLayout(null);

		scrollPaneShowRecords = new JScrollPane();
		scrollPaneShowRecords.setBounds(10, 21, 1276, 600);
		pnlBottom.add(scrollPaneShowRecords);

		panelRight = new JPanel();
		panelRight.setBorder(new TitledBorder(null, "Job Cart", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRight.setBounds(1316, 11, 223, 817);
		getContentPane().add(panelRight);
		panelRight.setLayout(null);

		scrollPaneJobCart = new JScrollPane();
		scrollPaneJobCart.setBounds(10, 24, 203, 782);
		panelRight.add(scrollPaneJobCart);

		lblshowtotalQty = new JLabel("0.0");
		lblshowtotalQty.setForeground(new Color(0, 153, 255));
		Border blackline = BorderFactory.createLineBorder(Color.GRAY);
		lblshowtotalQty.setBorder(blackline);
		lblshowtotalQty.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblshowtotalQty.setHorizontalAlignment(SwingConstants.CENTER);
		lblshowtotalQty.setBounds(1316, 839, 223, 111);
		getContentPane().add(lblshowtotalQty);

		/** SET ALL TREE ICONS EMPTY **/
		JTreeConfig.setEmptyTreeIcons();

		/** SETUP & INVOKE SHOW JOBS TABLE **/
		this.createReceivedOrdersTable();

		/** SETUP & INVOKE JOBS CART TABLE **/
		this.createJobCartTable();

		/** REMOVE ALL JOB CART ITEMS **/
		this.removeAllJobCartItems();
	}

	/** SETUP TABLE FOR SHOW MAIN TABLE RECORDS **/
	private void createReceivedOrdersTable() {

		tblShowRecords = new JTable() {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				if (column == 9) {
					return Boolean.class;
				} else {
					return JLabel.class;
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 9) {
					return true;
				} else {
					return false;
				}
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent) c;
				if (isRowSelected(row))
					jc.setBorder(null);
				return c;
			}
		};

		scrollPaneShowRecords.setViewportView(tblShowRecords);
		tblShowRecords.setBackground(SystemColor.inactiveCaptionBorder);
		tblShowRecords.setFont(new Font("Calibri", Font.PLAIN, 12));
		tblShowRecords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ShowRecordsTableModel = (DefaultTableModel) tblShowRecords.getModel();
		tblShowRecords.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
		tblShowRecords.getTableHeader().setReorderingAllowed(false);
		ShowRecordsTableModel.setColumnIdentifiers(showRecordsTblColNames);
		tblShowRecords.setGridColor(Color.BLACK);
		tblShowRecords.setShowHorizontalLines(true);
		tblShowRecords.setShowVerticalLines(false);

		this.checkBoxClicked();

		tblShowRecords.getColumnModel().getColumn(0)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblShowRecords.getColumnModel().getColumn(1)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblShowRecords.getColumnModel().getColumn(2)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
		tblShowRecords.getColumnModel().getColumn(3)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
		tblShowRecords.getColumnModel().getColumn(4)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));

		tblShowRecords.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		setColumnWidth(tblShowRecords, 0, 60, JLabel.CENTER, 60, 60);
		setColumnWidth(tblShowRecords, 1, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 2, 180, JLabel.LEFT, 180, 180);
		setColumnWidth(tblShowRecords, 3, 180, JLabel.LEFT, 180, 180);
		setColumnWidth(tblShowRecords, 4, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 5, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 6, 200, JLabel.CENTER, 200, 200);
		setColumnWidth(tblShowRecords, 7, 140, JLabel.CENTER, 140, 140);
		setColumnWidth(tblShowRecords, 8, 140, JLabel.CENTER, 140, 140);

		/** HIDE UNIQUE ORDER ID COLUMN AGAINST EACH ROW NUMBER **/
		tblShowRecords.removeColumn(tblShowRecords.getColumnModel().getColumn(UNIQUE_COLUMN_NO));

		tblShowRecords.setRowHeight(30);
	}

	private void drawJobCartTable() {
		DefaultTableModel model = (DefaultTableModel) tblJobCart.getModel();
		model.setRowCount(0);
	}

	/** CHECKBOX INSIDE TABLE SELECTED **/
	private void checkBoxClicked() {
		tblShowRecords.getModel().addTableModelListener(e -> {
			int checkboxColumn = 9;
			int column = e.getColumn();
			try {
				if (column == tblShowRecords.getColumnCount() - 1) {
					orderID = (int) tblShowRecords.getModel().getValueAt(tblShowRecords.getSelectedRow(),
							UNIQUE_COLUMN_NO);
					Boolean checked = (Boolean) ShowRecordsTableModel.getValueAt(tblShowRecords.getSelectedRow(),
							checkboxColumn);
					if (orderID != 0) {
						if (checked) {
							boolean isSuccess = daoJobCartObject.createNewJobCart(orderID, stockID, bomRouteID, true);
							if (isSuccess) {
								drawJobCartTable();
								this.getAllJobCartRecords();
							}
						} else {
							this.removeCartItemsByOrderID(orderID);
							drawJobCartTable();
							this.getAllJobCartRecords();
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	}

	/** SETUP TABLE FOR SHOW RECORDS **/
	private void createJobCartTable() {

		tblJobCart = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent) c;
				if (isRowSelected(row))
					jc.setBorder(null);
				return c;
			}
		};

		scrollPaneJobCart.setViewportView(tblJobCart);
		tblJobCart.setBackground(SystemColor.inactiveCaptionBorder);
		tblJobCart.setFont(new Font("Calibri", Font.PLAIN, 12));
		tblJobCart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jobCartTableModel = (DefaultTableModel) tblJobCart.getModel();
		tblJobCart.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
		tblJobCart.getTableHeader().setReorderingAllowed(false);
		jobCartTableModel.setColumnIdentifiers(jobCartTblColNames);
		tblJobCart.setGridColor(Color.BLACK);
		tblJobCart.setShowHorizontalLines(true);
		tblJobCart.setShowVerticalLines(false);

		tblJobCart.getColumnModel().getColumn(0)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblJobCart.getColumnModel().getColumn(1)
				.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));

		tblJobCart.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		setColumnWidth(tblJobCart, 0, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblJobCart, 1, 100, JLabel.CENTER, 100, 100);

		tblJobCart.setRowHeight(30);
	}

	/** RETRIEVE ALL JOB CART ITEMS **/
	private List<TblJobCart> getAllJobCartRecords() {
		List<TblJobCart> orderItems = null;
		double totalProducedQty = 0.0;
		try {
			orderItems = daoJobCartObject.getJobCartRecordsForDisplay();
			if (orderItems.size() != 0) {
				for (int item = 0; item < orderItems.size(); item++) {
					jobCartTableModel.addRow(
							new Object[] { orderItems.get(item).getOrderNo(), orderItems.get(item).getOrderQty() });
					textFieldQuantity.setText(Double.toString(orderItems.get(item).getOrderQty()));
					totalProducedQty = totalProducedQty + orderItems.get(item).getOrderQty();
				}
				DecimalFormat decimalFormat = new DecimalFormat("0.0");
				String formattedValue = decimalFormat.format(totalProducedQty);
				lblshowtotalQty.setText(formattedValue);
			} else {
				lblshowtotalQty.setText("0.0");
			}
			return orderItems;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItems;
	}

	/** REMOVE ALL JOB CART ITEMS **/
	private void removeAllJobCartItems() {
		try {
			daoJobCartObject.removeAllJobCartItems();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** REMOVE JOB CART ITEMS BY ORDER ID **/
	private void removeCartItemsByOrderID(int orderID) {
		try {
			daoJobCartObject.removeCartItemsByOrderID(orderID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getSelectedRowsData() {
		for (int i = 0; i < ShowRecordsTableModel.getRowCount(); i++) {
			String endItemCode = (String) ShowRecordsTableModel.getValueAt(i, 3);
			boolean isSelected = (boolean) ShowRecordsTableModel.getValueAt(i, 9);
			if (isSelected) {
				System.out.println("End Item Code: " + endItemCode);
				System.out.println("Select Checkbox: " + isSelected);
			}
		}
	}

	private List<TblCustomerOrder> getAllReceivedOrdersByStockID(int stockID) {
		List<TblCustomerOrder> orderItems = null;
		try {
			orderItems = daoCustomerOrderObject.getAllCustomerOrderByStockID(stockID);
			if (orderItems.size() != 0) {
				for (int item = 0; item < orderItems.size(); item++) {
					ShowRecordsTableModel.addRow(
							new Object[] { orderItems.get(item).getSerialNo(), orderItems.get(item).getOrderNo(),
									orderItems.get(item).getCustomerName(), orderItems.get(item).getStockCode(),
									orderItems.get(item).getOrderQty(), orderItems.get(item).getOnHandQty(),
									orderItems.get(item).getCustomerNotes(), orderItems.get(item).getOrderDate(),
									orderItems.get(item).getExpDlvryDate(), false, orderItems.get(item).getOrderID() });
				}
			} else {
				new MessageWindowType(INFO_ALERT_MESSAGE, 2, 2);
			}
			return orderItems;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItems;
	}

	/** METHOD FOR SET TABLE COLUMNS WIDTH **/
	private void setColumnWidth(JTable table, int columnIndex, int columnWidth, int columnTextPosition,
			int columnMinWidth, int columnMaxWidth) {
		table.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
		table.getColumnModel().getColumn(columnIndex).setMinWidth(columnMinWidth);
		table.getColumnModel().getColumn(columnIndex).setMaxWidth(columnMaxWidth);
		DefaultTableCellRenderer userRenderer = new DefaultTableCellRenderer();
		userRenderer.setHorizontalAlignment(columnTextPosition);
		table.getColumnModel().getColumn(columnIndex).setCellRenderer(userRenderer);
	}

	/** RETRIEVE ALL ROUTES ID & NAMES **/
	private List<TblBomRoute> getAllBomRoutes() {
		List<TblBomRoute> listItems = null;
		try {
			listItems = daoBomRouteObject.getAllListOfRoutes(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listItems;
	}

	/** GET ICON FOR FRAME BANNER **/
	private Image setFrameBannerIcon() {
		Image img = null;
		try {
			img = LoadResource.getImageFromResourceAsURL(AppConstants.IMPORT_TABLES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	/** SET JOB ROUTE JTREE MODEL **/
	private DefaultTreeModel setRouteJTreeModel() {
		DefaultMutableTreeNode rootNode = null;
		String rootNodeName = null;
		routeJTreeRootNode = new DefaultMutableTreeNode(AppConstants.JOB_PATH_TREE_NAME);
		DefaultTreeModel model = new DefaultTreeModel(routeJTreeRootNode);
		try {
			ArrayList<TblBomRoute> routeArray = daoBomRouteObject.fetchAllBomRoutes(sandboxGroupID, 0, 1);
			int routeTreeDepth = routeArray.size();
			ArrayList<String> customItems = new ArrayList<>(routeArray.size());
			for (int item = 0; item < routeArray.size(); item++) {
				if (item != routeTreeDepth - 1) {
					customItems.add(routeArray.get(item).getInFeedStockCode() + FIRST_CONCAT_PART
							+ routeArray.get(item).getMachineName() + SECOND_CONCAT_PART
							+ routeArray.get(item + 1).getInFeedQuantityInHand() + "("
							+ routeArray.get(item + 1).getRouteID() + ")");
				} else {
					customItems.add(routeArray.get(item).getInFeedStockCode() + SECOND_CONCAT_PART
							+ routeArray.get(item).getInFeedQuantityInHand());
				}
			}
			if (routeTreeDepth != 0) {
				rootNodeName = routeArray.get(0).getRouteName() + SECOND_CONCAT_PART
						+ routeArray.get(0).getInFeedQuantityInHand() + "(" + routeArray.get(0).getRouteID() + ")";
				rootNode = new DefaultMutableTreeNode(rootNodeName);
				routeJTreeRootNode.add(rootNode);
				populateNodes(rootNode, customItems, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/** SET RECURSIVE JTREE POPULATE RECORDS **/
	protected void populateNodes(DefaultMutableTreeNode parent, ArrayList<String> items, int index) {
		if (index >= items.size()) {
			return;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(items.get(index));
		parent.add(node);
		populateNodes(node, items, ++index);
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

	/** BIND COMBO BOX WITH ROUTE ID, GROUP ID & ROUTE NAME **/
	private void bindComboBox(JComboBox<TblBomRoute> comboBox, List<TblBomRoute> items) {
		DefaultComboBoxModel<TblBomRoute> model = new DefaultComboBoxModel<>(items.toArray(new TblBomRoute[0]));
		comboBox.setModel(model);
	}

	/** FETCH & SETUP JTREE **/
	private void getBomRouteDetails() {
		TblBomRoute selectedItem = (TblBomRoute) cmboBoxShowBomroute.getSelectedItem();
		if (selectedItem != null) {
			selectedRouteID = selectedItem.getRouteID();
			sandboxGroupID = selectedItem.getRouteGroupID();
		}
		JTreeConfig.clearAllTreeItems(treeBomRoute);
		treeBomRoute.setModel(setRouteJTreeModel());
		JTreeConfig.expandAllNodes(treeBomRoute);
	}

	/** RESET & RELOAD ALL COMPONENTS **/
	private void drawTable() {
		DefaultTableModel model = (DefaultTableModel) tblShowRecords.getModel();
		model.setRowCount(0);
	}

	/** RETRIEVE SELECTED ITEM STOCK ID **/
	private void setupTableOutput() {
		TblBomRoute selectedItem = (TblBomRoute) cmboBoxShowBomroute.getSelectedItem();
		if (selectedItem != null) {
			stockID = selectedItem.getStockID();
			bomRouteID = selectedItem.getRouteID();
			drawTable();
			this.getAllReceivedOrdersByStockID(selectedItem.getStockID());
		}
	}

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class AllUserActionListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == UserActions.BTN_VIEW_DETAILS.name()) {
				getBomRouteDetails();
				drawTable();
				setupTableOutput();
			} else if (e.getActionCommand() == UserActions.CREATE_NEW_JOB.name()) {
				getSelectedRowsData();
				// createNewJob();
			} else if (e.getActionCommand() == UserActions.BTN_VIEW_UNATTENDED_JOBS.name()) {
				callViewUnattendedJobsForm();
			}
		}
	}

	/** CALL OF CHILD FORM **/
	private void callViewUnattendedJobsForm() {
		if (viewUnattendedJobsObject != null && viewUnattendedJobsObject.isVisible()) {
			viewUnattendedJobsObject.setExtendedState(JFrame.NORMAL);
			viewUnattendedJobsObject.toFront();
			viewUnattendedJobsObject.requestFocus();
		} else {
			viewUnattendedJobsObject = new UnattendedJobs();
			viewUnattendedJobsObject.setVisible(true);
		}
	}

	/** CLASS TO RESTRICT USER FOR 100 CHARACTERS IN JTEXTPANE **/
	private class CharacterLimitTextPane extends JTextPane {

		private static final long serialVersionUID = 1L;

		public CharacterLimitTextPane(int maxLength) {
			((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
				@Override
				public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
						throws BadLocationException {
					int currentLength = fb.getDocument().getLength();
					if (currentLength + text.length() - length <= maxLength) {
						super.replace(fb, offset, length, text, attrs);
					} else {
						Toolkit.getDefaultToolkit().beep();
					}
				}
			});
		}
	}

	/** CLASS TO SET TABLE HEADER ALIGNMENT **/
	private class HorizontalAlignmentHeaderRenderer extends JCheckBox implements TableCellRenderer {

		private static final long serialVersionUID = 1L;
		private int horizontalAlignment;

		public HorizontalAlignmentHeaderRenderer(int horizontalAlignment) {
			setHorizontalAlignment(JCheckBox.CENTER);
			this.horizontalAlignment = horizontalAlignment;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			TableCellRenderer r = table.getTableHeader().getDefaultRenderer();
			JLabel lbl = (JLabel) r.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			lbl.setHorizontalAlignment(horizontalAlignment);
			return lbl;
		}
	}

	/** CLASS FOR CHANGE JTREE DEFAULT ICONS **/
	private class UserRendererJTree extends DefaultTreeCellRenderer {
		private Font boldFont;
		private static final long serialVersionUID = 1L;

		public UserRendererJTree() {
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
			if (leaf) {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_LEAF, 10, 10));
			} else if (expanded) {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_EXPAND, 10, 10));
			} else {
				setIcon(setImageIcon(AppConstants.BOM_ROUTE_NODE_RIGHT, 10, 10));
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
}
