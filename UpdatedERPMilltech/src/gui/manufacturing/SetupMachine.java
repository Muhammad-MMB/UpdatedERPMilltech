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
import dao.DaoMachines;
import dao.DaoMachineOprState;
import dao.DaoMachineOprStatesDtls;
import entities.TblMachineOprStatesDtls;
import entities.TblMachineOperationStates;
import entities.TblMachines;
import extras.AppConstants;
import extras.AppGenerics;
import extras.MessageWindowType;
import extras.MessageWindowType.MessageType;
import extras.LoadResource;
import java.awt.SystemColor;

public class SetupMachine extends JFrame {

	private static final long serialVersionUID = 1L;

	/** COMPONENTS & VARIABLES DECLARATION / INITIALIZATION **/
	private JPanel PnlMain, PnlChngeStatus, PnlMchneInfo;
	private JLabel lblFctryName, lblshowFctryName, lblMchneName, lblShowMchneName, lblMchneDscptn, lblshowMchneDscrptn,
			lblMchneStatus, lblShowMchneStatus, lblShowMchneStatusSymbol, lblMchneCode, lblShowMchneCode,
			lblMchneChangeStatus, lblClock, lblReturnDate, lblShowReturnDate, lblNotes, lblMchneNewStatus, lblMax;
	private JComboBox<String> CmboBoxLoadStatus;
	private JDateChooser dateReturnChooser;
	private JTextPane textPaneUserNotes;
	private JButton BtnSetStatus;
	private JTable tblLogs, TblMain;
	private JScrollPane logTableScrollPane, scrollPaneUserNotes;
	private JSeparator separator;
	private JCalendar calendar;
	private DefaultTableModel tableModel, logTableModel;
	private ImageIcon machineStatusIcon = null;
	private DaoMachines machineStatusObject = null;
	private DaoMachineOprStatesDtls machineOpsStsDtlsObject = null;
	private DaoMachineOprState mchmeOprnStatesObject = null;
	private ArrayList<TblMachines> machineArray;
	private ArrayList<TblMachineOprStatesDtls> logsRecordsArray;
	private String factoryName, machineName, machineCodeName, machineDescription, machineStatusName;
	private int machineStdHours, machineStatusID, machineID, selectedRow, currentOperatingStatusID;
	private final int maxNumberOfCharacters = 100;
	private String mainTblColNames[] = { "S. No", "Factory Name", "Machine Code", "Machine Name", "Machine Description",
			"Std Hours/Month", "Machine Current State", "State Symbol", "Action" };
	private String logTblColNames[] = { "Code", "Old", "New", "Date", "Time", "User" };

	public SetupMachine() {

		/** CLASSES OBJECT INITIALIZATION **/
		machineStatusObject = new DaoMachines();
		machineOpsStsDtlsObject = new DaoMachineOprStatesDtls();
		mchmeOprnStatesObject = new DaoMachineOprState();

		/** MAIN FRAME CALL **/
		mainFrameProperties();

		/** CREATE AND SHOW GUI **/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/** MAIN FRAME PROPERTIES **/
	private void mainFrameProperties() {
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
	}

	/** CREATE AND SHOW GUI **/
	private void createAndShowGUI() {
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
		lblMchneStatus.setBounds(605, 138, 98, 14);
		PnlMchneInfo.add(lblMchneStatus);

		lblShowMchneStatus = new JLabel("-");
		lblShowMchneStatus.setBounds(709, 138, 190, 14);
		PnlMchneInfo.add(lblShowMchneStatus);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(486, 77, 12, 412);
		PnlMchneInfo.add(separator);

		lblShowMchneStatusSymbol = new JLabel("-");
		lblShowMchneStatusSymbol.setBounds(1450, 102, 40, 65);
		PnlMchneInfo.add(lblShowMchneStatusSymbol);

		lblMchneCode = new JLabel("Machine Code:");
		lblMchneCode.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneCode.setBounds(605, 90, 93, 14);
		PnlMchneInfo.add(lblMchneCode);

		lblShowMchneCode = new JLabel("-");
		lblShowMchneCode.setBounds(709, 90, 190, 14);
		PnlMchneInfo.add(lblShowMchneCode);

		PnlChngeStatus = new JPanel();
		PnlChngeStatus.setBorder(new LineBorder(new Color(0, 0, 0)));
		PnlChngeStatus.setBounds(515, 200, 992, 247);
		PnlMchneInfo.add(PnlChngeStatus);

		PnlChngeStatus.setLayout(null);
		lblMchneChangeStatus = new JLabel("Update Machine Status");
		lblMchneChangeStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMchneChangeStatus.setBounds(380, 11, 190, 25);
		PnlChngeStatus.add(lblMchneChangeStatus);

		lblMchneNewStatus = new JLabel("New Status:");
		lblMchneNewStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMchneNewStatus.setBounds(52, 70, 80, 14);
		PnlChngeStatus.add(lblMchneNewStatus);

		CmboBoxLoadStatus = AppGenerics.createComboBox(getAllMachineStates());
		CmboBoxLoadStatus.setBounds(145, 59, 353, 28);
		AutoCompleteDecorator.decorate(CmboBoxLoadStatus);
		CmboBoxLoadStatus.setEditable(true);
		PnlChngeStatus.add(CmboBoxLoadStatus);

		BtnSetStatus = new JButton("Set Status");
		BtnSetStatus.setBounds(810, 174, 151, 37);
		ActionListener setStatusButtonListener = new userActionListener();
		BtnSetStatus.addActionListener(setStatusButtonListener);
		PnlChngeStatus.add(BtnSetStatus);

		lblNotes = new JLabel("User Notes:");
		lblNotes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNotes.setBounds(52, 113, 80, 14);
		PnlChngeStatus.add(lblNotes);

		scrollPaneUserNotes = new JScrollPane();
		scrollPaneUserNotes.setBounds(142, 113, 349, 98);
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

		lblReturnDate = new JLabel("Expected Receive / Return  Date:");
		lblReturnDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReturnDate.setBounds(627, 60, 199, 14);
		PnlChngeStatus.add(lblReturnDate);

		calendar = new JCalendar(GregorianCalendar.getInstance());
		dateReturnChooser = new JDateChooser(calendar, new Date(), "dd MMMM yyyy", null);
		dateReturnChooser.setBounds(627, 85, 229, 27);
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
		lblShowReturnDate.setBounds(627, 123, 229, 20);
		PnlChngeStatus.add(lblShowReturnDate);

		lblMax = new JLabel("(max 100)");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblMax.setBounds(62, 126, 44, 14);
		PnlChngeStatus.add(lblMax);

		lblClock = new JLabel("Clock");
		lblClock.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblClock.setBounds(1014, 99, 182, 53);
		PnlMchneInfo.add(lblClock);

		/** INVOKE PRIVATE METHODS **/
		this.createUserTable();
		this.createLogsTable();
		this.displayDigitalClock();
	}

	/** SETUP LOGS TABLE **/
	private void createLogsTable() {
		logTableScrollPane = new JScrollPane();
		logTableScrollPane.setBounds(22, 231, 439, 245);
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
		setColumnWidth(tblLogs, 5, 85, JLabel.CENTER, 85, 85);

		tblLogs.setRowHeight(30);
	}

	/** RETRIEVE LOG TABLE DATA **/
	private void showLogTableData() {
		ImageIcon oldStatusIcon, newStatusIcon = null;
		try {
			logsRecordsArray = machineOpsStsDtlsObject.getAllMachineChangeStatusLogs();
			for (int item = 0; item < logsRecordsArray.size(); item++) {
				oldStatusIcon = getMachineIcon(logsRecordsArray.get(item).getOldMachineOperationStatusID());
				newStatusIcon = getMachineIcon(logsRecordsArray.get(item).getNewMachineOperationStatusID());
				logTableModel.addRow(new Object[] { logsRecordsArray.get(item).getMachineCodeName(), oldStatusIcon,
						newStatusIcon, logsRecordsArray.get(item).getDateOnly(),
						logsRecordsArray.get(item).getTimeOnly(), logsRecordsArray.get(item).getUserName() });
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
			
			TblMain.setRowHeight(28);

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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	/** SET IMAGE ICONS **/
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

	/** CLASS FOR TABLE HEADER ALIGNMENT **/
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

	/** SETUP DIGITAL CLOCK **/
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

	/** UPDATE GUI ON ACTION **/
	private void updateStatusAndGUI() {
		int userResponse = MessageWindowType.createConfirmDialogueWindow(
				"Are you sure you want to change this machine status ?", "Confirm Machine Status");
		try {
			if (userResponse == 0) {
				if (CmboBoxLoadStatus.getSelectedItem().toString().equals(AppConstants.READY_NAME)) {
					machineStatusObject.setMachineStatus(AppConstants.READY, machineID);
					machineOpsStsDtlsObject.setAllMachineStatusDetails(machineID, currentOperatingStatusID,
							AppConstants.READY, 1, textPaneUserNotes.getText(), true, AppGenerics.getUserSystemName());
					lblShowMchneStatusSymbol.setIcon(
							new ImageIcon(SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_YELLOW)));
				} else if (CmboBoxLoadStatus.getSelectedItem().toString().equalsIgnoreCase(AppConstants.BUSY_NAME)) {
					machineStatusObject.setMachineStatus(AppConstants.BUSY, machineID);
					machineOpsStsDtlsObject.setAllMachineStatusDetails(machineID, currentOperatingStatusID,
							AppConstants.BUSY, 1, textPaneUserNotes.getText(), true, AppGenerics.getUserSystemName());
					lblShowMchneStatusSymbol.setIcon(
							new ImageIcon(SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_GREEN)));
				} else {
					machineStatusObject.setMachineStatus(AppConstants.MAINTENANCE, machineID);
					machineOpsStsDtlsObject.setAllMachineStatusDetails(machineID, currentOperatingStatusID,
							AppConstants.MAINTENANCE, 1, textPaneUserNotes.getText(), true,
							AppGenerics.getUserSystemName());
					lblShowMchneStatusSymbol.setIcon(
							new ImageIcon(SetupMachine.class.getClassLoader().getResource(AppConstants.LONG_RED)));
				}
				textPaneUserNotes.setText("");

				/** UPDATE MAIN TABLE GUI **/
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

	/** RETRIEVE ALL MACHINES STATES **/
	private ArrayList<String> getAllMachineStates() {
		ArrayList<String> itemsList = new ArrayList<>();
		try {
			ArrayList<TblMachineOperationStates> statesArray = mchmeOprnStatesObject.getAllMachineStates();
			for (int item = 0; item < statesArray.size(); item++) {
				itemsList.add(statesArray.get(item).getMachineOperationStateName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	/** RESET STATES OF COMPONENTS **/
	private void resetAllComponents() {
		lblShowMchneCode.setText("-");
	}

	/** CLASS FOR ACTION LISTENERS **/
	class userActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (lblShowMchneCode.getText().equalsIgnoreCase("-")) {
						MessageWindowType.showMessage("Please select machine to perform the action!", MessageType.ERROR);
					} else if (textPaneUserNotes.getText().equals("") || lblShowReturnDate.getText().equals("-")) {
						MessageWindowType.showMessage(
								"User must provide notes & expected date of return to proceed further!",
								MessageType.ERROR);
					} else if (CmboBoxLoadStatus.getSelectedItem().toString().equals(lblShowMchneStatus.getText())) {
						MessageWindowType.showMessage("User must choose a different machine state than active one!",
								MessageType.ERROR);
					} else {
						updateStatusAndGUI();
					}
				}
			});
		}
	}
}
