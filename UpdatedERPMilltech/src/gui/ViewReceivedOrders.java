package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import dao.DaoCustomerOrder;
import dao.DaoStockList;
import entities.TblCustomerOrder;
import entities.TblStockList;
import extras.AppConstants;
import extras.LoadResource;
import extras.MessageWindowType;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

public class ViewReceivedOrders extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelTop, panelMiddle;
	private JLabel lblEndItem;
	private JButton btnViewOrders;
	private JTable tblShowRecords;
	private JScrollPane scrollPaneShowRecords;
	private DefaultTableModel ShowRecordsTableModel;
	private JComboBox<TblStockList> comboBoxEndItem;

	private DaoStockList daoStockListObject;
	private DaoCustomerOrder daoCustomerOrderObject;

	private String showRecordsTblColNames[] = { "S. No", "Order No", "Customer Name", "End Item No", "Order Qty",
			"On Hand Qty", "Allocated Qty", "Customer Notes", "Order Date", "Exp. Delivery Date" };
	private String INFO_ALERT_MESSAGE = "No records found against this product!";

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_VIEW_ORDERS
	}

	public ViewReceivedOrders() {
		this.mainFrameProperties();
	}

	/** SETUP MAIN FRAME PROPERTIES **/
	private void mainFrameProperties() {

		this.setTitle("View Received Orders ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(setFrameBannerIcon());
		setBounds(100, 100, 1332, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		daoStockListObject = new DaoStockList();
		daoCustomerOrderObject = new DaoCustomerOrder();

		/** CREATE & SETUP GUI **/
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createAndShowGUI();
			}
		});

		/** SETUP & DISPLAY TABLE **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createReceivedOrdersTable();
			}
		});
	}

	private void createAndShowGUI() {

		panelTop = new JPanel();
		panelTop.setBounds(10, 11, 1296, 97);
		getContentPane().add(panelTop);
		panelTop.setLayout(null);

		lblEndItem = new JLabel("Select End Item:");
		lblEndItem.setBounds(22, 43, 84, 14);
		panelTop.add(lblEndItem);

		comboBoxEndItem = new JComboBox<>();
		comboBoxEndItem.setBounds(110, 38, 192, 24);
		AutoCompleteDecorator.decorate(comboBoxEndItem);
		bindComboBox(comboBoxEndItem, getAllEndItems());
		panelTop.add(comboBoxEndItem);

		btnViewOrders = new JButton("View Orders");
		btnViewOrders.setBounds(1127, 11, 147, 75);
		btnViewOrders.setIcon(LoadResource.getImageIconFromImage(AppConstants.VIEW, 15, 15));
		btnViewOrders.setIconTextGap(10);
		ActionListener viewOrderListener = new AllUserActionListeners();
		btnViewOrders.addActionListener(viewOrderListener);
		btnViewOrders.setActionCommand(UserActions.BTN_VIEW_ORDERS.name());
		panelTop.add(btnViewOrders);

		JCheckBox chckbxEnditem = new JCheckBox("Enable");
		chckbxEnditem.setBounds(308, 39, 69, 23);
		panelTop.add(chckbxEnditem);

		JLabel lblSelectSize = new JLabel("Select Size:");
		lblSelectSize.setBounds(404, 43, 61, 14);
		panelTop.add(lblSelectSize);

		JComboBox<TblStockList> comboBoxSize = new JComboBox<TblStockList>();
		comboBoxSize.setBounds(463, 38, 192, 24);
		panelTop.add(comboBoxSize);

		JCheckBox chckbxSize = new JCheckBox("Enable");
		chckbxSize.setBounds(661, 39, 69, 23);
		panelTop.add(chckbxSize);

		JLabel lblSelectGrade = new JLabel("Select Grade:");
		lblSelectGrade.setBounds(762, 43, 73, 14);
		panelTop.add(lblSelectGrade);

		JComboBox<TblStockList> comboBoxGrade = new JComboBox<TblStockList>();
		comboBoxGrade.setBounds(834, 38, 192, 24);
		panelTop.add(comboBoxGrade);

		JCheckBox chckbxGrade = new JCheckBox("Enable");
		chckbxGrade.setBounds(1032, 39, 69, 23);
		panelTop.add(chckbxGrade);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(383, 11, 11, 75);
		panelTop.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(736, 11, 11, 75);
		panelTop.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(1107, 11, 11, 75);
		panelTop.add(separator_2);

		panelMiddle = new JPanel();
		panelMiddle.setBounds(10, 119, 1296, 831);
		getContentPane().add(panelMiddle);
		panelMiddle.setLayout(null);

		scrollPaneShowRecords = new JScrollPane();
		scrollPaneShowRecords.setBounds(10, 11, 1276, 809);
		panelMiddle.add(scrollPaneShowRecords);

		tblShowRecords = new JTable();
		scrollPaneShowRecords.setViewportView(tblShowRecords);
	}

	/** RETRIEVE ALL END ITEM ID & NAMES **/
	private List<TblStockList> getAllEndItems() {
		List<TblStockList> listItems = null;
		try {
			listItems = daoStockListObject.getListOfAllStockCode();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listItems;
	}

	/** GET ICON FOR FRAME BANNER **/
	private Image setFrameBannerIcon() {
		Image img = null;
		try {
			img = LoadResource.getImageFromResourceAsURL(AppConstants.ENTITIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	/** BIND COMBO BOX WITH ROUTE ID, GROUP ID & ROUTE NAME **/
	private void bindComboBox(JComboBox<TblStockList> comboBox, List<TblStockList> items) {
		DefaultComboBoxModel<TblStockList> model = new DefaultComboBoxModel<>(items.toArray(new TblStockList[0]));
		comboBox.setModel(model);
	}

	/** SETUP TABLE FOR SHOW RECORDS **/
	private void createReceivedOrdersTable() {

		tblShowRecords = new JTable() {
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

		setColumnWidth(tblShowRecords, 0, 80, JLabel.CENTER, 80, 80);
		setColumnWidth(tblShowRecords, 1, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 2, 150, JLabel.LEFT, 150, 150);
		setColumnWidth(tblShowRecords, 3, 150, JLabel.LEFT, 150, 200);
		setColumnWidth(tblShowRecords, 4, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 5, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 6, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 7, 150, JLabel.CENTER, 150, 200);
		setColumnWidth(tblShowRecords, 8, 100, JLabel.CENTER, 100, 100);
		setColumnWidth(tblShowRecords, 9, 150, JLabel.CENTER, 150, 150);

		tblShowRecords.setRowHeight(30);
	}

	private List<TblCustomerOrder> getAllReceivedOrdersByEndItem() {
		List<TblCustomerOrder> orderItems = null;
		try {
			orderItems = daoCustomerOrderObject.getListOfAllCustomerOrder(getSelectedItemStockID());
			if (orderItems.size() != 0) {
				for (int item = 0; item < orderItems.size(); item++) {
					ShowRecordsTableModel.addRow(
							new Object[] { orderItems.get(item).getSerialNo(), orderItems.get(item).getOrderNo(),
									orderItems.get(item).getCustomerName(), orderItems.get(item).getStockCode(),
									orderItems.get(item).getOrderQty(), orderItems.get(item).getOnHandQty(),
									orderItems.get(item).getAllocatedQty(), orderItems.get(item).getCustomerNotes(),
									orderItems.get(item).getOrderDate(), orderItems.get(item).getExpDlvryDate() });
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

	/** RESET & RELOAD ALL COMPONENTS **/
	private void refreshTableRecords() {
		getSelectedItemStockID();
		DefaultTableModel model = (DefaultTableModel) tblShowRecords.getModel();
		model.setRowCount(0);
		this.getAllReceivedOrdersByEndItem();
	}

	/** RETRIEVE SELECTED ITEM STOCK ID **/
	private int getSelectedItemStockID() {
		TblStockList selectedItem = (TblStockList) comboBoxEndItem.getSelectedItem();
		if (selectedItem != null) {
			return selectedItem.getStock_ID();
		}
		return 0;
	}

	/** CLASS TO SET TABLE HEADER ALIGNMENT **/
	private class HorizontalAlignmentHeaderRenderer implements TableCellRenderer {
		private int horizontalAlignment;

		public HorizontalAlignmentHeaderRenderer(int horizontalAlignment) {
			this.horizontalAlignment = horizontalAlignment;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			TableCellRenderer r = table.getTableHeader().getDefaultRenderer();
			JLabel l = (JLabel) r.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			l.setHorizontalAlignment(horizontalAlignment);
			return l;
		}
	}

	/** ALL ACTION LISTENERS OF COMPONENTS **/
	private class AllUserActionListeners implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == UserActions.BTN_VIEW_ORDERS.name()) {
				refreshTableRecords();
			}
		}
	}
}
