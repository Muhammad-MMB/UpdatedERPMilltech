package gui.machines;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.SwingUtilities;
import javax.swing.table.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.Font;
import javax.swing.border.*;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JTextPane;
import com.toedter.calendar.JDateChooser;

public class MachineStatus extends JFrame {

	/** COMPONENTS **/
	private JTable TblMain;
	JPanel PnlMain, PnlChngeStatus;
	JLabel lblFctryName, lblshowFctryName, lblMchneName, lblShowMchneName, lblMchneDscptn, lblshowMchneDscrptn,
	lblMchneStatus, lblShowMchneStatus, lblShowMchneStatusSymbol, lblMchneCode, lblShowMchneCode,
	lblMchneChangeStatus, lblClock, lblReturnDate, lblShowReturnDate, lblNotes;
	JComboBox<String> CmboBoxLoadStatus;
	
	private DAO_MachineStatus machineStatusObject = null;
	private static final long serialVersionUID = 1L;
	private String colNames[] = { "S. No", "Factory Name", "Machine Code", "Machine Name", "Machine Description",
			"Std Hours/Month", "Machine Current State", "State Symbol", "Action" };
	private JLabel lblMchneNewStatus;
	private static final String machineStatusArray[] = { "Ready", "Busy", "Maintenance" };

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
		displayDigitalClock();
		SwingUtilities.invokeLater(new Runnable() {
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
		scrollPane.setBounds(10, 538, 1544, 390);
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

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent) c;
				if (isRowSelected(row))
					jc.setBorder(null);
				return c;
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
			TblMain.getTableHeader().setReorderingAllowed(false);

			TblMain.getColumnModel().getColumn(0)
			.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
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

			TblMain.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			setColumnWidth(TblMain, 0, 60, JLabel.CENTER, 60, 70);
			setColumnWidth(TblMain, 1, 160, JLabel.LEFT, 160, 170);
			setColumnWidth(TblMain, 2, 140, JLabel.LEFT, 140, 150);
			setColumnWidth(TblMain, 3, 200, JLabel.LEFT, 200, 210);
			setColumnWidth(TblMain, 4, 280, JLabel.LEFT, 280, 290);
			setColumnWidth(TblMain, 5, 140, JLabel.LEFT, 140, 150);
			setColumnWidth(TblMain, 6, 190, JLabel.LEFT, 190, 200);

			Action editButtonClickAction = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					int selectedRow = Integer.valueOf(e.getActionCommand());
					String selectedData = (String) TblMain.getValueAt(selectedRow, 2);
					for (int item = 0; item < machineArray.size(); item++) {
						if (selectedData.equalsIgnoreCase(machineArray.get(item).getMachineCode())) {
							lblshowFctryName.setText(machineArray.get(item).getFactoryName());
							lblShowMchneName.setText(machineArray.get(item).getMachineName());
							lblshowMchneDscrptn.setText(machineArray.get(item).getMachineedescription());
							lblShowMchneStatus.setText(machineArray.get(item).getMachineOperatingStatusName());
							lblShowMchneCode.setText(machineArray.get(item).getMachineCode());
							if (machineArray.get(item).getMachineOperatingStatusID() == AppConstants.READY) {
								lblShowMchneStatusSymbol.setIcon(new ImageIcon(
										MachineStatus.class.getClassLoader().getResource(AppConstants.LONG_GREEN)));
							} else if (machineArray.get(item).getMachineOperatingStatusID() == AppConstants.BUSY) {
								lblShowMchneStatusSymbol.setIcon(new ImageIcon(
										MachineStatus.class.getClassLoader().getResource(AppConstants.LONG_YELLOW)));
							} else {
								lblShowMchneStatusSymbol.setIcon(new ImageIcon(
										MachineStatus.class.getClassLoader().getResource(AppConstants.LONG_RED)));
							}
						}
					}
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
		PnlMchneInfo.setBounds(10, 11, 1544, 516);
		PnlMain.add(PnlMchneInfo);
		PnlMchneInfo.setLayout(null);

		lblFctryName = new JLabel("Factory Name:");
		lblFctryName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFctryName.setBounds(49, 77, 87, 14);
		PnlMchneInfo.add(lblFctryName);

		lblshowFctryName = new JLabel("-");
		lblshowFctryName.setBounds(179, 77, 122, 14);
		PnlMchneInfo.add(lblshowFctryName);

		lblMchneName = new JLabel("Machine Name:");
		lblMchneName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneName.setBounds(49, 128, 87, 14);
		PnlMchneInfo.add(lblMchneName);

		lblShowMchneName = new JLabel("-");
		lblShowMchneName.setBounds(179, 128, 172, 14);
		PnlMchneInfo.add(lblShowMchneName);

		lblMchneDscptn = new JLabel("Machine Description:");
		lblMchneDscptn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneDscptn.setBounds(49, 182, 131, 14);
		PnlMchneInfo.add(lblMchneDscptn);

		lblshowMchneDscrptn = new JLabel("-");
		lblshowMchneDscrptn.setBounds(179, 182, 208, 14);
		PnlMchneInfo.add(lblshowMchneDscrptn);

		lblMchneStatus = new JLabel("Machine Status:");
		lblMchneStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneStatus.setBounds(487, 141, 108, 14);
		PnlMchneInfo.add(lblMchneStatus);

		lblShowMchneStatus = new JLabel("-");
		lblShowMchneStatus.setBounds(591, 141, 190, 14);
		PnlMchneInfo.add(lblShowMchneStatus);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(397, 77, 12, 412);
		PnlMchneInfo.add(separator);

		lblShowMchneStatusSymbol = new JLabel("-");
		lblShowMchneStatusSymbol.setBounds(1450, 102, 40, 65);
		PnlMchneInfo.add(lblShowMchneStatusSymbol);

		lblMchneCode = new JLabel("Machine Code:");
		lblMchneCode.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneCode.setBounds(487, 93, 108, 14);
		PnlMchneInfo.add(lblMchneCode);

		lblShowMchneCode = new JLabel("-");
		lblShowMchneCode.setBounds(591, 93, 190, 14);
		PnlMchneInfo.add(lblShowMchneCode);
		
		PnlChngeStatus = new JPanel();
		PnlChngeStatus.setBorder(new LineBorder(new Color(0, 0, 0)));
		PnlChngeStatus.setBounds(433, 200, 1074, 247);
		PnlMchneInfo.add(PnlChngeStatus);
		PnlChngeStatus.setLayout(null);

		lblMchneChangeStatus = new JLabel("Update Machine Status");
		lblMchneChangeStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMchneChangeStatus.setBounds(471, 11, 190, 25);
		PnlChngeStatus.add(lblMchneChangeStatus);

		lblMchneNewStatus = new JLabel("New Status:");
		lblMchneNewStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneNewStatus.setBounds(59, 63, 80, 14);
		PnlChngeStatus.add(lblMchneNewStatus);

		CmboBoxLoadStatus = new JComboBox<String>();
		CmboBoxLoadStatus.setModel(new DefaultComboBoxModel<String>(machineStatusArray));
		CmboBoxLoadStatus.setBounds(145, 59, 353, 28);
		AutoCompleteDecorator.decorate(CmboBoxLoadStatus);
		CmboBoxLoadStatus.setEditable(true);
		PnlChngeStatus.add(CmboBoxLoadStatus);

		JButton BtnSetStatus = new JButton("Set Status");
		BtnSetStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		BtnSetStatus.setBounds(873, 174, 151, 37);
		PnlChngeStatus.add(BtnSetStatus);

		lblNotes = new JLabel("User Notes:");
		lblNotes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNotes.setBounds(59, 101, 80, 14);
		PnlChngeStatus.add(lblNotes);

		JScrollPane scrollPaneUserNotes = new JScrollPane();
		scrollPaneUserNotes.setBounds(149, 101, 349, 98);
		PnlChngeStatus.add(scrollPaneUserNotes);

		JTextPane textPaneUserNotes = new JTextPane();
		scrollPaneUserNotes.setViewportView(textPaneUserNotes);

		lblReturnDate = new JLabel("Expected Fix Date:");
		lblReturnDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReturnDate.setBounds(555, 63, 110, 14);
		PnlChngeStatus.add(lblReturnDate);

		JDateChooser dateReturnChooser = new JDateChooser();
		dateReturnChooser.setBounds(555, 88, 229, 27);
		dateReturnChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
					lblShowReturnDate.setText(dateFormat.format(dateReturnChooser.getDate()));
				}
			}
		});
		PnlChngeStatus.add(dateReturnChooser);

		lblShowReturnDate = new JLabel("-");
		lblShowReturnDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowReturnDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblShowReturnDate.setBounds(555, 143, 229, 20);
		PnlChngeStatus.add(lblShowReturnDate);

		lblClock = new JLabel("Clock");
		lblClock.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblClock.setBounds(908, 102, 182, 53);
		PnlMchneInfo.add(lblClock);
	}

	private void setColumnWidth(JTable table, int columnIndex, int columnWidth, int columnTextPosition,
			int columnMinWidth, int columnMaxWidth) {
		table.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnWidth);
		table.getColumnModel().getColumn(columnIndex).setMinWidth(columnMinWidth);
		table.getColumnModel().getColumn(columnIndex).setMaxWidth(columnMaxWidth);
		DefaultTableCellRenderer userRenderer = new DefaultTableCellRenderer();
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

			} else if (machineID == AppConstants.BUSY) {
				image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_YELLOW);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

			} else {
				image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_RED);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			}
			machineIcon = new ImageIcon(image);
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

	private void displayDigitalClock() {
		int delay = 100;
		Timer timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				String formattedDateTime = now.format(formatter);
				lblClock.setText(formattedDateTime);
			}
		});
		timer.start();
	}
}
