package gui.manufacturing;

/**
 * @author Muhammad
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableModel;
import dao.DaoJob;
import dao.DaoJobDetail;
import dao.DaoJobState;
import entities.TblJob;
import entities.TblJob.JobCreated;
import entities.TblJobDetails;
import extras.AppConstants;
import extras.LoadResource;

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
	private String[] treeTableColumnNames = { "Job No", "Job Qty", "Priority", "State", "job Notes", "order No",
			"Stock Code", "Job Date Created", "Job Time" };

	/** CLASSES OBJECTS **/
	private DaoJob daoJobObject;
	private DaoJobState daoJobStateObject;
	private DaoJobDetail daoJobDetailObject;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_LOAD_DATA
	}

	public ViewAllJobs() {

		/** SETUP JFRAME PROPERTIES **/
		this.setTitle("View Jobs Catalog");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1565, 1000);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		// setIconImage(setFrameBannerIcon());
		this.getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoJobObject = new DaoJob();
		daoJobStateObject = new DaoJobState();
		daoJobDetailObject = new DaoJobDetail();

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

	/** SETUP JXTREETABLE MODEL **/
	private TreeTableModel createTreeTableModel(int jobStateID) {
		int jobIDPointer = 0;
		ArrayList<TblJob> jobItems = getAllParentJobsByStateID(jobStateID);
		ArrayList<TblJobDetails> jobDetailItems = getJobDetailsByStateID(jobStateID);
		DefaultMutableTreeTableNode parentNode = null, childNode = null, rootNode = null;

		rootNode = new DefaultMutableTreeTableNode("Root");

		for (int parentJobItem = 0; parentJobItem < jobItems.size(); parentJobItem++) {
			jobIDPointer = jobItems.get(parentJobItem).getJobID();
			parentNode = new DefaultMutableTreeTableNode(new TblJob(jobItems.get(parentJobItem).getJobID(),
					jobItems.get(parentJobItem).getJobQty(), jobItems.get(parentJobItem).isJobPriority(),
					jobItems.get(parentJobItem).getJobStateName(), jobItems.get(parentJobItem).getJobNotes(),
					jobItems.get(parentJobItem).getJobDateOnly(), jobItems.get(parentJobItem).getJobTimeOnly()));
			for (int item = 0; item < jobDetailItems.size(); item++) {
				if (jobIDPointer == jobDetailItems.get(item).getJobID()) {
					childNode = new DefaultMutableTreeTableNode(new TblJobDetails(jobDetailItems.get(item).getJobID(),
							jobDetailItems.get(item).getJobQty(), jobDetailItems.get(item).isJobPriority(),
							jobDetailItems.get(item).getJobStateName(), jobDetailItems.get(item).getJobNotes(),
							jobDetailItems.get(item).getJobDateOnly(), jobDetailItems.get(item).getJobTimeOnly(),
							jobDetailItems.get(item).getCustomerOrderNo(), jobDetailItems.get(item).getStockCode(),
							jobDetailItems.get(item).getCustomerOrderQty()));
					parentNode.add(childNode);
				}
			}
			rootNode.add(parentNode);
		}
		allJobsTreeTableModel = new AllJobsTreeTableModel(rootNode, treeTableColumnNames);
		return allJobsTreeTableModel;

		/*
		 * DefaultMutableTreeTableNode node1 = new DefaultMutableTreeTableNode( new
		 * AllJobsDataModel("Node 1", "Description 1")); node1.add(new
		 * DefaultMutableTreeTableNode(new AllJobsDataModel("Subnode 1.1",
		 * "Description 1.1"))); node1.add(new DefaultMutableTreeTableNode(new
		 * AllJobsDataModel("Subnode 1.2", "Description 1.2")));
		 * 
		 * DefaultMutableTreeTableNode node2 = new DefaultMutableTreeTableNode( new
		 * AllJobsDataModel("Node 2", "Description 2")); node2.add(new
		 * DefaultMutableTreeTableNode(new AllJobsDataModel("Subnode 2.1",
		 * "Description 2.1")));
		 * 
		 * root.add(node1); root.add(node2);
		 */

	}

	/** RETRIEVE ALL PARENT JOBS JOB STATE ID **/
	private ArrayList<TblJob> getAllParentJobsByStateID(int jobStateID) {
		ArrayList<TblJob> jobItems = null;
		try {
			jobItems = daoJobObject.getAllParentJobsByStateID(jobStateID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobItems;
	}

	/** RETRIEVE ALL JOBS & DETAILS BY JOB STATE ID **/
	private ArrayList<TblJobDetails> getJobDetailsByStateID(int jobStateID) {
		ArrayList<TblJobDetails> jobDetailItems = null;
		try {
			jobDetailItems = daoJobDetailObject.getJobDetailsByJobStateID(jobStateID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobDetailItems;
	}

	/** CREATE AND SHOW GUI **/
	private void createAndShowGUI() {

		scrollPaneShowRecords = new JScrollPane();
		scrollPaneShowRecords.setBounds(10, 741, 1529, 209);
		getContentPane().add(scrollPaneShowRecords);
		scrollPaneShowRecords.setViewportView(tblShowRecords);

		panelTop = new JPanel();
		panelTop.setBorder(new TitledBorder(
				new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "", TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(0, 0, 0)),
				"Job States Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelTop.setBounds(10, 11, 1529, 101);
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
		scrollPane.setBounds(10, 123, 1529, 607);
		getContentPane().add(scrollPane);

		treeTable = new JXTreeTable(createTreeTableModel(AppConstants.JOB_STATE_UNPLANNED));
		MouseListener clickListener = new TreeTableMouseLisener();
		treeTable.addMouseListener(clickListener);
		treeTable.setTreeCellRenderer(new CustomJXTreeTableRenderer());
		scrollPane.setViewportView(treeTable);
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

	/** CUSTOM JXTREETABLE RENDERER **/
	private class CustomJXTreeTableRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 1L;

		private Icon customLeafIcon;
		private Icon customOpenIcon;
		private Icon customClosedIcon;

		public CustomJXTreeTableRenderer() {
			customLeafIcon = setImageIcon(AppConstants.MINUS_ICON, 12, 12);
			customOpenIcon = setImageIcon(AppConstants.MINUS_ICON, 12, 12);
			customClosedIcon = setImageIcon(AppConstants.ADD_ICON, 12, 12);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			if (value instanceof DefaultMutableTreeTableNode) {
				DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) value;
				Object userObject = node.getUserObject();
				if (userObject instanceof TblJob) {
					TblJob job = (TblJob) userObject;
					setText(job.getJobID() + "");
				} 
			}
			if (leaf) {
				setIcon(customLeafIcon);
			} else if (expanded) {
				setIcon(customOpenIcon);
			} else {
				setIcon(customClosedIcon);
			}
			// setText(nodeValue);
			return component;
		}
	}

	private class AllJobsTreeTableModel extends AbstractTreeTableModel implements TreeTableModel {
		private String[] columnNames;

		public AllJobsTreeTableModel(DefaultMutableTreeTableNode root, String[] columnNames) {
			super(root);
			this.columnNames = columnNames;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Object getValueAt(Object node, int column) {
			if (node instanceof DefaultMutableTreeTableNode) {
				DefaultMutableTreeTableNode treeNode = (DefaultMutableTreeTableNode) node;
				Object userObject = treeNode.getUserObject();
				if (userObject instanceof TblJob) {
					TblJob job = (TblJob) userObject;
					if (column == 5) {
						return job.getJobID();
					} else if (column == 1) {
						return job.getJobQty();
					} else if (column == 2) {
						return job.getJobStateName();
					} 
				}
			}
			return null;
		}
		
		@Override
		public Object getChild(Object parent, int index) {
			return ((DefaultMutableTreeTableNode) parent).getChildAt(index);	
		}

		@Override
		public int getChildCount(Object parent) {
			return ((DefaultMutableTreeTableNode) parent).getChildCount();
		}

		@Override
		public boolean isLeaf(Object node) {
			return ((DefaultMutableTreeTableNode) node).isLeaf();
		}

		@Override
		public int getIndexOfChild(Object parent, Object child) {
			DefaultMutableTreeTableNode parentNode = (DefaultMutableTreeTableNode) parent;
			DefaultMutableTreeTableNode childNode = (DefaultMutableTreeTableNode) child;
			return parentNode.getIndex(childNode);
		}

		@Override
		public boolean isCellEditable(Object node, int column) {
			return false;
		}
	}

	private class TreeTableMouseLisener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = treeTable.rowAtPoint(e.getPoint());
			int column = treeTable.columnAtPoint(e.getPoint());

			if (row >= 0 && column == 0) {
				TreePath path = treeTable.getPathForRow(row);
				if (path != null) {
					if (treeTable.isExpanded(path)) {
						treeTable.collapsePath(path);
					} else {
						treeTable.expandPath(path);
					}
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
