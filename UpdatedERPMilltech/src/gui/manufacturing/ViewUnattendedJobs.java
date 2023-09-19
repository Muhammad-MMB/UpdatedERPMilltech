package gui.manufacturing;

/**
 * @author Muhammad
 *
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import dao.DaoJob;
import entities.TblJob.JobCreated;

public class ViewUnattendedJobs extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTable tblShowRecords;
	private JButton btnRefresh;
	private ActionListener refreshActionListener;
	private JScrollPane scrollPaneShowRecords;
	private DefaultTableModel ShowRecordsTableModel;
	private String showRecordsTblColNames[] = { "S. No", "End Item", "Infeed Item", "Machine Name", "Qty Instock" };

	/** CLASSES OBJECTS **/
	DaoJob daoJobObject;

	/** ENUM FOR USER BUTTON ACTIONS **/
	private enum UserActions {
		BTN_REFRESH
	}

	public ViewUnattendedJobs() {

		/** JFRAME PROPERTIES **/
		this.setTitle("View All Unattended Jobs");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 1078, 670);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);

		/** CLASSES OBJECTS INITIALIZATION **/
		daoJobObject = new DaoJob();

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
				createJobsTable();
			}
		});
	}

	private void createAndShowGUI() {

		btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(963, 15, 89, 23);
		refreshActionListener = new allUserActionListener();
		btnRefresh.addActionListener(refreshActionListener);
		btnRefresh.setActionCommand(UserActions.BTN_REFRESH.name());
		getContentPane().add(btnRefresh);

		scrollPaneShowRecords = new JScrollPane();
		scrollPaneShowRecords.setBounds(10, 57, 1042, 563);
		getContentPane().add(scrollPaneShowRecords);

		scrollPaneShowRecords.setViewportView(tblShowRecords);
	}

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

		getAllUnattendedJobs();

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

	/** SET TABLE COLUMNS WIDTH **/
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

	/** RESET ALL COMPONENTS TO DEFAULT STATE **/
	private void refreshRecords() {
		DefaultTableModel model = (DefaultTableModel) tblShowRecords.getModel();
		model.setRowCount(0);
		getAllUnattendedJobs();
	}

	/** CLASS TO SET TABLE HEADER ALIGNMENT **/
	private class allUserActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == UserActions.BTN_REFRESH.name()) {
				refreshRecords();
			}

		}

	}
}
