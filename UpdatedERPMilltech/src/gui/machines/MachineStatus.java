package gui.machines;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import dao.DAO_MachineStatus;
import entities.tbl_machines;
import extras.AppConstants;
import extras.ReadResources;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Font;
import javax.swing.border.EtchedBorder;

public class MachineStatus extends JFrame {

	private JTable TblMain;
	JPanel PnlMain;
	private DAO_MachineStatus machineStatusObject = null;
	private static final long serialVersionUID = 1L;
	private String colNames[] = { "S. No", "Factory Name", "Machine Code", "Machine Name", "Machine Description",
			"Std Hours/Month", "Machine Current State", "Machine Symbol", "Action" };

	public MachineStatus() {

		machineStatusObject = new DAO_MachineStatus();

		/** FRAME PROPERTIES **/
		this.setTitle("Machines Status");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);
		this.setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		PnlMain = new JPanel();
		PnlMain.setBounds(10, 11, 1564, 939);
		getContentPane().add(PnlMain);
		PnlMain.setLayout(null);

		/** METHODS **/
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				createUserTable();
			}
		});
	}

	private void createUserTable() {
		ArrayList<tbl_machines> machineArray;
		String factoryName, machineName, machineCode, machineDescription, machineStatus;
		int machineStdHours, machineStatusID;
		ImageIcon machineStatusIcon = null;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 568, 1544, 360);
		PnlMain.add(scrollPane);

		TblMain = new JTable() {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				if (column == 7) {
					return ImageIcon.class;
				} else {
					return Object.class;
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 8)
					return true;
				else
					return false;
			}

		};

		TblMain.setFont(new Font("Calibri", Font.PLAIN, 12));
		scrollPane.setViewportView(TblMain);
		TblMain.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		TblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableModel tableModel = (DefaultTableModel) TblMain.getModel();
		tableModel.setColumnIdentifiers(colNames);

		try {
			machineArray = machineStatusObject.getAllMachineStatus();
			for (int item = 0; item < machineArray.size(); item++) {
				factoryName = machineArray.get(item).getFactoryName();
				machineCode = machineArray.get(item).getMachineCode();
				machineName = machineArray.get(item).getMachineName();
				machineDescription = machineArray.get(item).getMachineedescription();
				machineStdHours = machineArray.get(item).getMachineStdHrsPerMonth();
				machineStatus = machineArray.get(item).getMachineOperatingStatusName();
				machineStatusID = machineArray.get(item).getMachineOperatingStatusID();
				machineStatusIcon = getMachineIcon(machineStatusID);
				tableModel.addRow(new Object[] { item + 1, factoryName, machineCode, machineName, machineDescription,
						machineStdHours, machineStatus, machineStatusIcon });
			}
			
			TblMain.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 13));
			
			TblMain.getColumnModel().getColumn(0)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(1)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(2)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(3)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(4)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(5)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(6)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.LEFT));
			TblMain.getColumnModel().getColumn(7)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));

			setColumnWidth(TblMain, 0, 60, JLabel.CENTER, 60, 70);
			setColumnWidth(TblMain, 1, 160, JLabel.LEFT, 160, 170);
			setColumnWidth(TblMain, 2, 140, JLabel.LEFT, 140, 150);
			setColumnWidth(TblMain, 3, 180, JLabel.LEFT, 180, 190);
			setColumnWidth(TblMain, 4, 280, JLabel.LEFT, 280, 290);
			setColumnWidth(TblMain, 5, 140, JLabel.LEFT, 140, 150);
			setColumnWidth(TblMain, 6, 170, JLabel.LEFT, 170, 180);

			Action editButtonClickAction = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					 int modelRow = Integer.valueOf( e.getActionCommand() );
					//((DefaultTableModel)TblMain.getModel()).removeRow(modelRow);
					System.out.println("clicked" + modelRow);
				}
			};

			new ButtonColumn(TblMain, editButtonClickAction, 8);

		} catch (

				SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel PnlMchneInfo = new JPanel();
		PnlMchneInfo.setBackground(new Color(255, 255, 255));
		PnlMchneInfo
		.setBorder(new TitledBorder(
				new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
								new Color(160, 160, 160)),
						"Machine Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)),
				"Machine Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		PnlMchneInfo.setBounds(10, 11, 1544, 546);
		PnlMain.add(PnlMchneInfo);
	}

	private void setColumnWidth(JTable table, int columnIndex, int columnWidth, int columnTextPosition,
			int columnMinWidth, int columnMaxWidth) {

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
		table.getColumnModel().getColumn(columnIndex).setMinWidth(columnMinWidth);
		table.getColumnModel().getColumn(columnIndex).setMaxWidth(columnMaxWidth);
		DefaultTableCellRenderer userRenderer = (DefaultTableCellRenderer) table.getDefaultRenderer(JLabel.class);
		userRenderer.setHorizontalAlignment(columnTextPosition);
		table.getColumnModel().getColumn(columnIndex).setCellRenderer(userRenderer);
		TblMain.setRowHeight(28);
	}

	private ImageIcon getMachineIcon(int machineID) {
		Image image = null;
		ImageIcon machineIcon = null;
		try {
			if (machineID == AppConstants.READY) {
				image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_GREEN);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				machineIcon = new ImageIcon(image);
			} else if (machineID == AppConstants.BUSY) {
				image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_YELLOW);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				machineIcon = new ImageIcon(image);
			} else {
				image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_RED);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				machineIcon = new ImageIcon(image);
			}
		} catch (Exception excpt) {

		}
		return machineIcon;
	}

	class HorizontalAlignmentHeaderRenderer implements TableCellRenderer {
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
}
