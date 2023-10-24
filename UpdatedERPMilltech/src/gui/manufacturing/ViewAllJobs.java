package gui.manufacturing;

/**
 * @author Muhammad
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import dao.DaoJob;
import dao.DaoJobState;
import entities.TblJob.JobCreated;
import extras.AppConstants;
import extras.LoadResource;
import org.jdesktop.swingx.JXTreeTable;

public class ViewAllJobs extends JFrame {

	private static final long serialVersionUID = 1L;

	private JCheckBox chckbxUnplanned, chckbxAwaitingProduction, chckbxInprogress, chckboxASAP;
	private JButton loadData;
	private JSeparator separator, separator_1, separator_2, separator_3;
	private JTable tblShowRecords;
	private ActionListener refreshActionListener;
	private JScrollPane scrollPaneShowRecords;
	private DefaultTableModel ShowRecordsTableModel;
	private TreeTableModel allJobsTreeTableModel;
	private JPanel panelTop;
	private JXTreeTable treeTable;
	private String showRecordsTblColNames[] = { "S. No", "End Item", "Infeed Item", "Machine Name", "Qty Instock" };

	/** CLASSES OBJECTS **/
	DaoJob daoJobObject;
	DaoJobState daoJobStateObject;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_LOAD_DATA
	}

	public ViewAllJobs() {

		/** SETUP JFRAME PROPERTIES **/
		this.setTitle("View Jobs Catalog");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 1078, 772);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoJobObject = new DaoJob();
		daoJobStateObject = new DaoJobState();

		/** SETUP GUI **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});

		/** SETUP TABLE **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// createJobsTable();
			}
		});
	}

	private TreeTableModel createTreeTableModel() {

		DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode("Root");
		String[] columnNames = { "Node", "Description" };

		DefaultMutableTreeTableNode node1 = new DefaultMutableTreeTableNode(
				new AllJobsDataModel("Node 1", "Description 1"));
		node1.add(new DefaultMutableTreeTableNode(new AllJobsDataModel("Subnode 1.1", "Description 1.1")));
		node1.add(new DefaultMutableTreeTableNode(new AllJobsDataModel("Subnode 1.2", "Description 1.2")));

		DefaultMutableTreeTableNode node2 = new DefaultMutableTreeTableNode(
				new AllJobsDataModel("Node 2", "Description 2"));
		node2.add(new DefaultMutableTreeTableNode(new AllJobsDataModel("Subnode 2.1", "Description 2.1")));

		root.add(node1);
		root.add(node2);

		allJobsTreeTableModel = new AllJobsTreeTableModel(root, columnNames);
		return allJobsTreeTableModel;
	}

	/** CREATE AND SHOW GUI **/
	private void createAndShowGUI() {

		scrollPaneShowRecords = new JScrollPane();
		scrollPaneShowRecords.setBounds(10, 513, 1042, 209);
		getContentPane().add(scrollPaneShowRecords);

		scrollPaneShowRecords.setViewportView(tblShowRecords);

		panelTop = new JPanel();
		panelTop.setBorder(new TitledBorder(
				new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "", TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(0, 0, 0)),
				"Job States Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelTop.setBounds(10, 11, 1042, 101);
		getContentPane().add(panelTop);
		panelTop.setLayout(null);

		chckbxUnplanned = new JCheckBox("Unplanned");
		chckbxUnplanned.setBounds(103, 41, 95, 23);
		panelTop.add(chckbxUnplanned);

		chckbxAwaitingProduction = new JCheckBox("Awaiting Production");
		chckbxAwaitingProduction.setBounds(230, 41, 134, 23);
		panelTop.add(chckbxAwaitingProduction);

		chckbxInprogress = new JCheckBox("InProgress");
		chckbxInprogress.setBounds(440, 41, 104, 23);
		panelTop.add(chckbxInprogress);

		chckboxASAP = new JCheckBox("ASAP");
		chckboxASAP.setBounds(595, 41, 84, 23);
		panelTop.add(chckboxASAP);

		loadData = new JButton("Load Data");
		loadData.setIconTextGap(10);
		loadData.setIcon(LoadResource.getImageIconFromImage(AppConstants.VIEW, 15, 15));
		loadData.setHorizontalAlignment(SwingConstants.CENTER);
		loadData.setActionCommand(UserActions.BTN_LOAD_DATA.name());
		loadData.setBounds(751, 32, 175, 40);
		panelTop.add(loadData);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(203, 22, 10, 65);
		panelTop.add(separator);

		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(392, 22, 10, 65);
		panelTop.add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(550, 22, 10, 65);
		panelTop.add(separator_2);

		separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(691, 22, 10, 65);
		panelTop.add(separator_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 123, 1042, 379);
		getContentPane().add(scrollPane);

		treeTable = new JXTreeTable(createTreeTableModel());
		scrollPane.setViewportView(treeTable);

	}

	/** SETUP TABLE FOR SHOW RECORDS **/
	private void createJobsTable() {

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

		this.getAllUnattendedJobs();

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

	/** RETRIEVE ALL UNATTENDED JOBS **/
	private ArrayList<JobCreated> getAllUnattendedJobs() {
		ArrayList<JobCreated> jobItems = null;
		try {
			jobItems = daoJobObject.fetchUnplannedJobs();
			for (int item = 0; item < jobItems.size(); item++) {
				ShowRecordsTableModel.addRow(new Object[] { jobItems.get(item).getSerialNo(),
						jobItems.get(item).getEndItemName(), jobItems.get(item).getInFeedItemName(),
						jobItems.get(item).getMachineName(), jobItems.get(item).getJobQuantity() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobItems;
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

	/** RESET & RELOAD ALL COMPONENTS **/
	private void refreshTableRecords() {
		DefaultTableModel model = (DefaultTableModel) tblShowRecords.getModel();
		model.setRowCount(0);
		this.getAllUnattendedJobs();
	}
}

class CustomTreeTableModel extends DefaultTreeTableModel {
	public CustomTreeTableModel(DefaultMutableTreeTableNode root, java.util.List<String> columnNames) {
		super(root, columnNames);
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		// Optionally, you can make cells editable as needed
		return false;
	}
}

class AllJobsDataModel {
	private String name;
	private String description;

	public AllJobsDataModel(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
