package gui.manufacturing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import dao.DAO_MachineStatus;
import dao.DAO_Mchne_Oprtn_State;
import dao.DAO_Mchne_Ops_Sts_Dtls;
import entities.tbl_mchne_ops_sts_dtls;
import entities.tbl_machine_operation_states;
import entities.tbl_machines;
import extras.AppConstants;
import extras.Generics;
import extras.MessageWindow;
import extras.MessageWindow.MessageType;
import extras.LoadResource;
import java.awt.SystemColor;

public class SetupMachine extends JFrame {

	/** COMPONENTS DECLARATION & INITIALIZATION **/
	private JTable TblMain;
	JPanel PnlMain, PnlChngeStatus, PnlMchneInfo;
	JLabel lblFctryName, lblshowFctryName, lblMchneName, lblShowMchneName, lblMchneDscptn, lblshowMchneDscrptn,
	lblMchneStatus, lblShowMchneStatus, lblShowMchneStatusSymbol, lblMchneCode, lblShowMchneCode,
	lblMchneChangeStatus, lblClock, lblReturnDate, lblShowReturnDate, lblNotes;
	JComboBox<String> CmboBoxLoadStatus;
	JDateChooser dateReturnChooser;
	JTextPane textPaneUserNotes;
	DefaultTableModel tableModel, logTableModel;
	ImageIcon machineStatusIcon = null;
	private DAO_MachineStatus machineStatusObject = null;
	private DAO_Mchne_Ops_Sts_Dtls machineOpsStsDtlsObject = null;
	private DAO_Mchne_Oprtn_State mchmeOprnStatesObject = null;
	private static final long serialVersionUID = 1L;
	private String mainTblColNames[] = { "S. No", "Factory Name", "Machine Code", "Machine Name", "Machine Description",
			"Std Hours/Month", "Machine Current State", "State Symbol", "Action" };
	private String logTblColNames[] = { "Code", "Old", "New", "Date", "Time" };
	private JLabel lblMchneNewStatus;
	JButton BtnSetStatus;
	ArrayList<tbl_machines> machineArray;
	ArrayList<tbl_mchne_ops_sts_dtls> logsRecordsArray;
	String factoryName, machineName, machineCodeName, machineDescription, machineStatusName;
	private int machineStdHours, machineStatusID, machineID, selectedRow, currentOperatingStatusID;
	private final int maxNumberOfCharacters = 100;
	private JLabel lblMax;
	private JTable tblLogs;
	private JScrollPane logTableScrollPane;

	public SetupMachine() {

		machineStatusObject = new DAO_MachineStatus();
		machineOpsStsDtlsObject = new DAO_Mchne_Ops_Sts_Dtls();
		mchmeOprnStatesObject = new DAO_Mchne_Oprtn_State();

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

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createLogsTable();
			}
		});
	}

	private void createLogsTable() {
		logTableScrollPane = new JScrollPane();
		logTableScrollPane.setBounds(22, 231, 347, 245);
		PnlMchneInfo.add(logTableScrollPane);

		tblLogs = new JTable() {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				if (column == 1 || column == 2) {
					return ImageIcon.class;
				} else {
					return Object.class;
				}
			}

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

		logTableScrollPane.setViewportView(tblLogs);
		tblLogs.setBackground(SystemColor.inactiveCaptionBorder);
		tblLogs.setFont(new Font("Calibri", Font.PLAIN, 12));
		tblLogs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		logTableModel = (DefaultTableModel) tblLogs.getModel();
		tblLogs.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
		tblLogs.getTableHeader().setReorderingAllowed(false);
		logTableModel.setColumnIdentifiers(logTblColNames);
		tblLogs.setGridColor(Color.BLACK);
		tblLogs.setShowHorizontalLines(true);
		tblLogs.setShowVerticalLines(false);

		showLogTableData();

		tblLogs.getColumnModel().getColumn(0)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblLogs.getColumnModel().getColumn(1)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblLogs.getColumnModel().getColumn(2)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblLogs.getColumnModel().getColumn(3)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));
		tblLogs.getColumnModel().getColumn(4)
		.setHeaderRenderer(new HorizontalAlignmentHeaderRenderer(SwingConstants.CENTER));

		tblLogs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		setColumnWidth(tblLogs, 0, 70, JLabel.CENTER, 70, 70);
		setColumnWidth(tblLogs, 3, 75, JLabel.CENTER, 75, 75);
		setColumnWidth(tblLogs, 4, 75, JLabel.CENTER, 75, 75);
		tblLogs.setRowHeight(30);

	}

	/** RETRIEVE LOG TABLE DATA   **/
	private void showLogTableData() {
		ImageIcon oldStatusIcon, newStatusIcon = null;
		try {
			logsRecordsArray = machineOpsStsDtlsObject.getAllMachineChangeStatusLogs();
			for (int item = 0; item < logsRecordsArray.size(); item++) {
				oldStatusIcon = getMachineIcon(logsRecordsArray.get(item).getOldMachineOperationStatusID());
				newStatusIcon = getMachineIcon(logsRecordsArray.get(item).getNewMachineOperationStatusID());
				logTableModel.addRow(
						new Object[] { logsRecordsArray.get(item).getMachineCodeName(), oldStatusIcon, newStatusIcon,
								logsRecordsArray.get(item).getDateOnly(), logsRecordsArray.get(item).getTimeOnly() });
			}
		} catch (Exception excpt) {
			excpt.printStackTrace();
		}
	}

	/** CREATE AND SET A MAIN TABLE MODEL **/
	private void createUserTable() {

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
		TblMain.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		TblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TblMain.setGridColor(Color.BLACK);
		TblMain.setShowHorizontalLines(true);
		TblMain.setShowVerticalLines(false);
		tableModel = (DefaultTableModel) TblMain.getModel();
		tableModel.setColumnIdentifiers(mainTblColNames);

		try {
			machineArray = machineStatusObject.getAllMachineStatus();
			for (int item = 0; item < machineArray.size(); item++) {
				factoryName = machineArray.get(item).getFactoryName();
				machineCodeName = machineArray.get(item).getMachineCodeName();
				machineName = machineArray.get(item).getMachineName();
				machineDescription = machineArray.get(item).getMachineDescription();
				machineStdHours = machineArray.get(item).getMachineStdHrsPerMonth();
				machineStatusName = machineArray.get(item).getMachineOperatingStatusName();
				machineStatusID = machineArray.get(item).getMachineOperatingStatusID();
				machineStatusIcon = getMachineIcon(machineStatusID);
				tableModel.addRow(new Object[] { item + 1, factoryName, machineCodeName, machineName,
						machineDescription, machineStdHours, machineStatusName, machineStatusIcon });
			}
			TblMain.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			TblMain.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 15));
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

			setColumnWidth(TblMain, 0, 60, JLabel.CENTER, 60, 70);
			setColumnWidth(TblMain, 1, 160, JLabel.LEFT, 160, 170);
			setColumnWidth(TblMain, 2, 140, JLabel.LEFT, 140, 150);
			setColumnWidth(TblMain, 3, 200, JLabel.LEFT, 200, 210);
			setColumnWidth(TblMain, 4, 280, JLabel.LEFT, 280, 290);
			setColumnWidth(TblMain, 5, 140, JLabel.LEFT, 140, 150);
			setColumnWidth(TblMain, 6, 190, JLabel.LEFT, 190, 200);

			Action changeStatusButtonClickAction = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					selectedRow = Integer.valueOf(e.getActionCommand());
					String selectedData = (String) TblMain.getValueAt(selectedRow, 2);
					for (int item = 0; item < machineArray.size(); item++) {
						if (selectedData.equals(machineArray.get(item).getMachineCodeName())) {
							machineID = machineArray.get(item).getMachineID();
							lblshowFctryName.setText(machineArray.get(item).getFactoryName());
							lblShowMchneName.setText(machineArray.get(item).getMachineName());
							lblshowMchneDscrptn.setText(machineArray.get(item).getMachineDescription());
							lblShowMchneStatus.setText(machineArray.get(item).getMachineOperatingStatusName());
							lblShowMchneCode.setText(machineArray.get(item).getMachineCodeName());
							currentOperatingStatusID = machineArray.get(item).getMachineOperatingStatusID();
							if (machineArray.get(item).getMachineOperatingStatusID() == AppConstants.READY) {
								lblShowMchneStatusSymbol.setIcon(new ImageIcon(
										SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_YELLOW)));
							} else if (machineArray.get(item).getMachineOperatingStatusID() == AppConstants.BUSY) {
								lblShowMchneStatusSymbol.setIcon(new ImageIcon(
										SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_GREEN)));
							} else {
								lblShowMchneStatusSymbol.setIcon(new ImageIcon(
										SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_RED)));
							}
						}
					}
				}
			};
			new ButtonColumn(TblMain, changeStatusButtonClickAction, 8);
		} catch (

				SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		PnlMchneInfo = new JPanel();
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

		CmboBoxLoadStatus = Generics.createComboBox(getAllMachineStates());
		CmboBoxLoadStatus.setBounds(145, 59, 353, 28);
		AutoCompleteDecorator.decorate(CmboBoxLoadStatus);
		CmboBoxLoadStatus.setEditable(true);
		PnlChngeStatus.add(CmboBoxLoadStatus);

		BtnSetStatus = new JButton("Set Status");
		BtnSetStatus.setBounds(873, 174, 151, 37);
		ActionListener setStatusButtonListener = new userActionListener();
		BtnSetStatus.addActionListener(setStatusButtonListener);
		PnlChngeStatus.add(BtnSetStatus);

		lblNotes = new JLabel("User Notes:");
		lblNotes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNotes.setBounds(59, 101, 80, 14);
		PnlChngeStatus.add(lblNotes);

		JScrollPane scrollPaneUserNotes = new JScrollPane();
		scrollPaneUserNotes.setBounds(149, 101, 349, 98);
		PnlChngeStatus.add(scrollPaneUserNotes);

		textPaneUserNotes = new JTextPane(new DefaultStyledDocument() {
			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if ((getLength() + str.length()) <= maxNumberOfCharacters) {
					super.insertString(offs, str, a);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		scrollPaneUserNotes.setViewportView(textPaneUserNotes);

		lblReturnDate = new JLabel("Expected Fix Date:");
		lblReturnDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReturnDate.setBounds(555, 63, 110, 14);
		PnlChngeStatus.add(lblReturnDate);

		JCalendar calendar = new JCalendar(GregorianCalendar.getInstance());
		dateReturnChooser = new JDateChooser(calendar, new Date(), "dd MMMM yyyy", null);
		dateReturnChooser.setBounds(555, 88, 229, 27);
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		cal.set(2050, 10, 10);
		dateReturnChooser.setSelectableDateRange(new Date(), cal.getTime());
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
		lblShowReturnDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblShowReturnDate.setBounds(555, 126, 229, 20);
		PnlChngeStatus.add(lblShowReturnDate);

		lblMax = new JLabel("(max 100)");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblMax.setBounds(69, 114, 44, 14);
		PnlChngeStatus.add(lblMax);

		lblClock = new JLabel("Clock");
		lblClock.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblClock.setBounds(908, 102, 182, 53);
		PnlMchneInfo.add(lblClock);
	}

	/** SET TABLE COLUMNS WIDTH  **/
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

	/** SET IMAGE ICONS  **/
	private ImageIcon getMachineIcon(int machineID) {
		Image image = null;
		ImageIcon machineIcon = null;
		try {
			if (machineID == AppConstants.READY) {
				image = LoadResource.getImageFromResourceAsURL(AppConstants.SHORT_YELLOW);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			} else if (machineID == AppConstants.BUSY) {
				image = LoadResource.getImageFromResourceAsURL(AppConstants.SHORT_GREEN);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			} else {
				image = LoadResource.getImageFromResourceAsURL(AppConstants.SHORT_RED);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			}
			machineIcon = new ImageIcon(image);
		} catch (Exception excpt) {
		}
		return machineIcon;
	}

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

	/** CREATE CONFIRM DIALOGUE WINDOW  **/
	private int createConfirmDialogueWindow() {
		Image image = null;
		ImageIcon imageIcon = null;
		try {
			image = LoadResource.getImageFromResourceAsURL(AppConstants.QUESTION_MARK);
			image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		} catch (Exception excpt) {
			excpt.printStackTrace();
		}
		int input = JOptionPane.showConfirmDialog(null, "Are you sure you wnat to change this machine status ?",
				"Confirm Machine Status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, imageIcon);
		return input;
	}

	private void updateStatusAndGUI() {
		int userResponse = createConfirmDialogueWindow();
		try {
			if (userResponse == 0) {
				if (CmboBoxLoadStatus.getSelectedItem().toString().equals(AppConstants.READY_NAME)) {
					machineStatusObject.setMachineStatus(AppConstants.READY, machineID);
					machineOpsStsDtlsObject.setAllMachineStatusDetails(machineID, currentOperatingStatusID,
							AppConstants.READY, 1, textPaneUserNotes.getText(), true, Generics.getUserSystemName());
					lblShowMchneStatusSymbol.setIcon(
							new ImageIcon(SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_YELLOW)));
				} else if (CmboBoxLoadStatus.getSelectedItem().toString().equalsIgnoreCase(AppConstants.BUSY_NAME)) {
					machineStatusObject.setMachineStatus(AppConstants.BUSY, machineID);
					machineOpsStsDtlsObject.setAllMachineStatusDetails(machineID, currentOperatingStatusID,
							AppConstants.BUSY, 1, textPaneUserNotes.getText(), true, Generics.getUserSystemName());
					lblShowMchneStatusSymbol.setIcon(
							new ImageIcon(SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_GREEN)));
				} else {
					machineStatusObject.setMachineStatus(AppConstants.MAINTENANCE, machineID);
					machineOpsStsDtlsObject.setAllMachineStatusDetails(machineID, currentOperatingStatusID,
							AppConstants.MAINTENANCE, 1, textPaneUserNotes.getText(), true,
							Generics.getUserSystemName());
					lblShowMchneStatusSymbol.setIcon(
							new ImageIcon(SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_RED)));
				}
				textPaneUserNotes.setText("");
				
				/**  UPDATE MAIN TABLE GUI  **/
				machineArray = machineStatusObject.getAllMachineStatus();
				for (int item = 0; item < machineArray.size(); item++) {
					if (machineID == machineArray.get(item).getMachineID()) {
						machineStatusID = machineArray.get(item).getMachineOperatingStatusID();
						machineStatusIcon = getMachineIcon(machineStatusID);
						lblShowMchneStatus.setText(machineArray.get(item).getMachineOperatingStatusName());
						TblMain.setValueAt(machineArray.get(item).getMachineOperatingStatusName(), selectedRow, 6);
						TblMain.setValueAt(machineStatusIcon, selectedRow, 7);
					}
				}
				/** UPDATE LOG TABLE GUI **/
				DefaultTableModel model = (DefaultTableModel) tblLogs.getModel();
				model.setRowCount(0);
				showLogTableData();
				resetAllComponents();
			}
		} catch (Exception excpt) {
			excpt.printStackTrace();
		}
	}
	
	private ArrayList<String> getAllMachineStates() {
		ArrayList<String> itemsList = new ArrayList<>();
		try {
			ArrayList<tbl_machine_operation_states> statesArray = mchmeOprnStatesObject.getAllMachineStates();
			for (int item = 0; item < statesArray.size(); item++) {
				itemsList.add(statesArray.get(item).getMachineOperationStateName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}
	
	private void resetAllComponents() {
		lblShowMchneCode.setText("-");
	}

	class userActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (lblShowMchneCode.getText().equalsIgnoreCase("-")) {
						MessageWindow.showMessage("Please select machine to perform the action!", MessageType.ERROR);
					} else if (textPaneUserNotes.getText().equals("") || lblShowReturnDate.getText().equals("-")) {
						MessageWindow.showMessage(
								"User must provide notes & expected date of return to proceed further!",
								MessageType.ERROR);
					}
					else if(CmboBoxLoadStatus.getSelectedItem().toString().equals(lblShowMchneStatus.getText())) {
						MessageWindow.showMessage(
								"You must choose different machine state!",
								MessageType.ERROR);
					}
					else {
						updateStatusAndGUI();
					}
				}
			});
		}
	}
}
