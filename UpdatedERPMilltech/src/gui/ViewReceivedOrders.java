package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
import dao.DaoStockList;
import entities.TblStockList;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class ViewReceivedOrders extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelTop, panelMiddle;
	private JLabel lblEndItem;
	private JButton btnViewOrders;
	private JTable tblShowRecords;
	private JScrollPane scrollPaneShowRecords;
	private DefaultTableModel ShowRecordsTableModel;

	private JComboBox<TblStockList> comboBoxEndItem;
	private DaoStockList daoStockListObject = null;
	private String showRecordsTblColNames[] = { "S. No", "Order No", "Customer Name", "Item No", "Order Qty", "Order Date", "Exp. Delivery Date" };

	
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
		// setIconImage(setFrameBannerIcon());
		setBounds(100, 100, 1332, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		daoStockListObject = new DaoStockList();

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
		lblEndItem.setBounds(90, 43, 84, 14);
		panelTop.add(lblEndItem);

		comboBoxEndItem = new JComboBox<>();
		comboBoxEndItem.setBounds(194, 36, 307, 29);
		AutoCompleteDecorator.decorate(comboBoxEndItem);
		bindComboBox(comboBoxEndItem, getAllEndItems());
		panelTop.add(comboBoxEndItem);

		btnViewOrders = new JButton("View Orders");
		btnViewOrders.setBounds(581, 36, 147, 29);
		ActionListener viewOrderListener = new AllUserActionListeners();
		btnViewOrders.addActionListener(viewOrderListener);
		btnViewOrders.setActionCommand(UserActions.BTN_VIEW_ORDERS.name());
		panelTop.add(btnViewOrders);

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

		this.getAllReceivedOrdersByEndItem();

		tblShowRecords.getColumnModel().getColumn(0)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblShowRecords.getColumnModel().getColumn(1)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
		tblShowRecords.getColumnModel().getColumn(2)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
		tblShowRecords.getColumnModel().getColumn(3)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
		tblShowRecords.getColumnModel().getColumn(4)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));

		tblShowRecords.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		setColumnWidth(tblShowRecords, 0, 80, JLabel.CENTER, 80, 80);
		setColumnWidth(tblShowRecords, 1, 240, JLabel.LEFT, 240, 240);
		setColumnWidth(tblShowRecords, 2, 240, JLabel.LEFT, 240, 240);
		setColumnWidth(tblShowRecords, 3, 300, JLabel.LEFT, 300, 300);
		setColumnWidth(tblShowRecords, 4, 170, JLabel.CENTER, 170, 200);
		
		tblShowRecords.setRowHeight(30);
	}
	
	
	private void getAllReceivedOrdersByEndItem() {
		
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
