package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.TitledBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import dao.DAO_RptSales;
import extras.MessageWindow;
import extras.MessageWindow.MessageType;
import extras.UserCustomProgressBar;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.border.MatteBorder;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class RptSales extends JFrame {

	/** VARIABLE DECLARATION **/
	ArrayList<Object> arrListData;;
	DAO_RptSales daoRptSales_Object;
	JasperPrint jasperPrint;
	public static int noOfMonths;
	private ArrayList<JCheckBox> allCheckBoxes;
	private static final String lastYearChckBoxName = "ChckBoxLastYear";
	private static final String reportYears[] = { "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
			"2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035" };
	private static final String reportMonths[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
	"12" };

	/**  COMPONENTS DEFINITION  **/
	JPanel PnlRptInput, Pnl_RptViewer, PnlFreeSpace, PnlSubmit;
	JButton Btnsubmit;
	JXBusyLabel lblBusyProgress;
	JLabel LblSizeFrom, LblSizeTo, LblShape, Lblgrade, Lbl_IS, LblSF, LblDateFrom, LblDateTo;
	JCheckBox ChckboxSizeFrom, ChckboxSizeTo, Chckboxshape, Chkboxgrade, ChkboxIS, ChkboxSF, ChkboxDateFrom,
	ChkboxDateTo, Chckboxoverruleall, ChckBoxUntickAll, ChckBoxLastYear;
	JComboBox<String> DrpdwnSizeFrom, DrpdwnSizeTo, DrpDwnshape, Drpdwngrade, DrpdwnIS, DrpdwnSF, DrpDwnMonthFrom,
	DrpDwnYearFrom, DrpDwnMonthTo, DrpDwnYearTo;
	
	private static Logger logger = LogManager.getLogger(RptSales.class);

	/** DEFAULT CONSTRUCTOR **/
	public RptSales() throws SQLException {

		/** FRAME PROPERTIES **/
		this.setTitle("Report on Sales");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		
		/** DEFINITION **/
		daoRptSales_Object = new DAO_RptSales();
		allCheckBoxes = new ArrayList<>();

		/** METHODS CALL **/
		createAndShowGUI();

		/** LOGGER TEST CASES **/
		logger.debug("This is debug");
		logger.info("This is error : ");
		logger.error("This is error");
	}

	/** CREATE & SETUP GUI */
	private void createAndShowGUI() throws SQLException {

		/** PARENT // MAIN PANEL **/
		JPanel PnlParent = new JPanel();
		PnlParent.setBackground(new Color(255, 255, 255));
		PnlParent.setBounds(10, 11, 1564, 939);
		getContentPane().add(PnlParent);
		PnlParent.setLayout(null);

		PnlRptInput = new JPanel();
		PnlRptInput.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Report Input", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		PnlRptInput.setBounds(10, 11, 362, 413);
		PnlParent.add(PnlRptInput);
		PnlRptInput.setLayout(null);

		LblSizeFrom = new JLabel("Size From");
		LblSizeFrom.setHorizontalAlignment(SwingConstants.LEFT);
		LblSizeFrom.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LblSizeFrom.setBounds(25, 25, 70, 16);
		PnlRptInput.add(LblSizeFrom);

		DrpdwnSizeFrom = createComboBox(daoRptSales_Object.getStockSize());
		DrpdwnSizeFrom.setBounds(136, 22, 150, 22);
		PnlRptInput.add(DrpdwnSizeFrom);

		ChckboxSizeFrom = new JCheckBox("Enable");
		ChckboxSizeFrom.setBounds(288, 22, 68, 23);
		allCheckBoxes.add(ChckboxSizeFrom);
		PnlRptInput.add(ChckboxSizeFrom);

		LblSizeTo = new JLabel("Size To");
		LblSizeTo.setHorizontalAlignment(SwingConstants.LEFT);
		LblSizeTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LblSizeTo.setBounds(25, 65, 70, 16);
		PnlRptInput.add(LblSizeTo);

		DrpdwnSizeTo = createComboBox(daoRptSales_Object.getStockSize());
		DrpdwnSizeTo.setBounds(136, 62, 150, 22);
		PnlRptInput.add(DrpdwnSizeTo);

		ChckboxSizeTo = new JCheckBox("Enable");
		ChckboxSizeTo.setBounds(288, 62, 68, 23);
		allCheckBoxes.add(ChckboxSizeTo);
		PnlRptInput.add(ChckboxSizeTo);

		LblShape = new JLabel("Shape");
		LblShape.setHorizontalAlignment(SwingConstants.LEFT);
		LblShape.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LblShape.setBounds(25, 105, 70, 16);
		PnlRptInput.add(LblShape);

		DrpDwnshape = createComboBox(daoRptSales_Object.getStockShape());
		AutoCompleteDecorator.decorate(DrpDwnshape);
		DrpDwnshape.setBounds(136, 102, 150, 22);
		PnlRptInput.add(DrpDwnshape);

		Chckboxshape = new JCheckBox("Enable");
		Chckboxshape.setBounds(288, 102, 68, 23);
		allCheckBoxes.add(Chckboxshape);
		PnlRptInput.add(Chckboxshape);

		Lblgrade = new JLabel("Grade");
		Lblgrade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Lblgrade.setBounds(25, 145, 70, 16);
		PnlRptInput.add(Lblgrade);

		Drpdwngrade = createComboBox(daoRptSales_Object.getStockGrade());
		Drpdwngrade.setBounds(136, 142, 150, 23);
		PnlRptInput.add(Drpdwngrade);

		Chkboxgrade = new JCheckBox("Enable");
		Chkboxgrade.setBounds(288, 142, 68, 23);
		allCheckBoxes.add(Chkboxgrade);
		PnlRptInput.add(Chkboxgrade);

		Lbl_IS = new JLabel("Internal Structure");
		Lbl_IS.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Lbl_IS.setBounds(25, 185, 100, 16);
		PnlRptInput.add(Lbl_IS);

		DrpdwnIS = createComboBox(daoRptSales_Object.getStockIS());
		DrpdwnIS.setBounds(136, 182, 150, 23);
		PnlRptInput.add(DrpdwnIS);

		ChkboxIS = new JCheckBox("Enable");
		ChkboxIS.setBounds(288, 182, 68, 23);
		allCheckBoxes.add(ChkboxIS);
		PnlRptInput.add(ChkboxIS);

		LblSF = new JLabel("Surface Finish");
		LblSF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LblSF.setBounds(25, 225, 100, 16);
		PnlRptInput.add(LblSF);

		DrpdwnSF = createComboBox(daoRptSales_Object.getStockSF());
		DrpdwnSF.setBounds(136, 222, 150, 23);
		PnlRptInput.add(DrpdwnSF);

		ChkboxSF = new JCheckBox("Enable");
		ChkboxSF.setBounds(288, 222, 68, 23);
		allCheckBoxes.add(ChkboxSF);
		PnlRptInput.add(ChkboxSF);

		LblDateFrom = new JLabel("Date From");
		LblDateFrom.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LblDateFrom.setBounds(25, 265, 70, 16);
		PnlRptInput.add(LblDateFrom);

		DrpDwnMonthFrom = new JComboBox<>();
		DrpDwnMonthFrom.setModel(new DefaultComboBoxModel<String>(reportMonths));
		DrpDwnMonthFrom.setBounds(134, 262, 70, 23);
		PnlRptInput.add(DrpDwnMonthFrom);

		DrpDwnYearFrom = new JComboBox<>();
		DrpDwnYearFrom.setModel(new DefaultComboBoxModel<String>(reportYears));
		DrpDwnYearFrom.setBounds(213, 262, 70, 23);
		PnlRptInput.add(DrpDwnYearFrom);

		ChkboxDateFrom = new JCheckBox("Enable");
		ChkboxDateFrom.setBounds(288, 262, 68, 23);
		allCheckBoxes.add(ChkboxDateFrom);
		PnlRptInput.add(ChkboxDateFrom);

		LblDateTo = new JLabel("Date To");
		LblDateTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LblDateTo.setBounds(25, 305, 70, 16);
		PnlRptInput.add(LblDateTo);

		DrpDwnMonthTo = new JComboBox<>();
		DrpDwnMonthTo.setModel(new DefaultComboBoxModel<String>(reportMonths));
		DrpDwnMonthTo.setBounds(134, 302, 70, 23);
		PnlRptInput.add(DrpDwnMonthTo);

		DrpDwnYearTo = new JComboBox<>();
		DrpDwnYearTo.setModel(new DefaultComboBoxModel<String>(reportYears));
		DrpDwnYearTo.setBounds(213, 302, 70, 23);
		PnlRptInput.add(DrpDwnYearTo);

		ChkboxDateTo = new JCheckBox("Enable");
		ChkboxDateTo.setBounds(288, 302, 68, 23);
		allCheckBoxes.add(ChkboxDateTo);
		PnlRptInput.add(ChkboxDateTo);

		ChckBoxUntickAll = new JCheckBox("Untick All");
		ChckBoxUntickAll.setBounds(263, 365, 79, 23);
		ChckBoxUntickAll.setFont(new Font("Tahoma", Font.BOLD, 11));
		PnlRptInput.add(ChckBoxUntickAll);
		ChckBoxUntickAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChckBoxStatus();
			}
		});

		JSeparator separator = new JSeparator();
		separator.setBounds(25, 349, 317, 9);
		PnlRptInput.add(separator);

		ChckBoxLastYear = new JCheckBox("Last 01 Year");
		ChckBoxLastYear.setFont(new Font("Tahoma", Font.BOLD, 11));
		ChckBoxLastYear.setBounds(25, 365, 97, 23);
		ChckBoxLastYear.setName(lastYearChckBoxName);
		ChckBoxLastYear.setToolTipText("Display last 12 months records");
		ChckBoxLastYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ChckBoxLastYear.isSelected()) {
					setPanelStatusForLastYear(PnlRptInput, false);
				} else {
					setPanelStatusForLastYear(PnlRptInput, true);
				}
			}
		});
		PnlRptInput.add(ChckBoxLastYear);

		Pnl_RptViewer = new JPanel(new GridBagLayout());
		Pnl_RptViewer
		.setBorder(new TitledBorder(null, "Report Viewer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Pnl_RptViewer.setBounds(382, 11, 1172, 917);

		lblBusyProgress = UserCustomProgressBar.createProgressLabel();
		Pnl_RptViewer.add(lblBusyProgress);
		PnlParent.add(Pnl_RptViewer);

		PnlFreeSpace = new JPanel();
		PnlFreeSpace.setLayout(null);
		PnlFreeSpace.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		PnlFreeSpace.setBounds(10, 433, 362, 259);
		PnlParent.add(PnlFreeSpace);

		PnlSubmit = new JPanel();
		PnlSubmit.setLayout(null);
		PnlSubmit.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		PnlSubmit.setBounds(10, 703, 362, 225);
		PnlParent.add(PnlSubmit);

		Chckboxoverruleall = new JCheckBox("OVERRULE ALL");
		Chckboxoverruleall.setFont(new Font("Tahoma", Font.BOLD, 11));
		Chckboxoverruleall.setBounds(10, 138, 121, 23);
		Chckboxoverruleall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Chckboxoverruleall.isSelected()) {
					setPanelStatusForOverrule(PnlRptInput, false);
				} else {
					setPanelStatusForOverrule(PnlRptInput, true);
				}
			}
		});
		PnlSubmit.add(Chckboxoverruleall);

		Btnsubmit = new JButton("Submit");
		Btnsubmit.setBounds(10, 168, 342, 46);
		PnlSubmit.add(Btnsubmit);
		Btnsubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						startBackgroundProcess();
					}
				});
			}
		});
	}

	/** CREATE DYNAMIC COMBOBOX MODEL **/
	private static JComboBox<String> createComboBox(ArrayList<String> items) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for (String item : items) {
			comboBoxModel.addElement(item);
		}
		JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setEditable(true);
		return comboBox;
	}

	/** CREATE & SET REPORT FILTERES **/
	private void createReportFilters() {
		try {
			if (Chckboxoverruleall.isSelected()) {
				arrListData = daoRptSales_Object.getAllItmHstryRcrd();
				createSaleReport(arrListData);
			} else if (ChckBoxLastYear.isSelected()) {
				arrListData = daoRptSales_Object.getItmHstryRcrdLastYear();
				createSaleReport(arrListData);
			} else {
				/** ONLY SELECT SIZE FROM **/
				if (ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object
							.getItmHstryRcrdSizeFrom(DrpdwnSizeFrom.getSelectedItem().toString());
					createSaleReport(arrListData);
				}
				/** ONLY SELECT SIZE TO **/
				else if (!ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdSizeTo(DrpdwnSizeTo.getSelectedItem().toString());
					createSaleReport(arrListData);
				}
				/** ONLY SELECT SHAPE **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerShape(DrpDwnshape.getSelectedItem().toString());
					createSaleReport(arrListData);
				}
				/** ONLY SELECT GRADE **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerGrade(Drpdwngrade.getSelectedItem().toString());
					createSaleReport(arrListData);
				}
				/** ONLY SELECT INTERNAL STRUCTURE **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerIS(DrpdwnIS.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** ONLY SELECT SURFACE FINISH **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSF(DrpdwnSF.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** ONLY SELECT DATE FROM **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerDateFrom(
							DrpDwnMonthFrom.getSelectedItem().toString(), DrpDwnYearFrom.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** ONLY SELECT DATE TO **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerDateTo(
							DrpDwnMonthTo.getSelectedItem().toString(), DrpDwnYearTo.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** 02 PARAMETERS VALUES  **/
				/** SELECT SIZE FROM & SIZE TO  **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromAndSizeTo(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString());
					createSaleReport(arrListData);
				}
				
				/** SELECT INTERNAL STRUCTURE & SURFACE FINISH  **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && ChkboxIS.isSelected() && ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerISAndSF(
							DrpdwnIS.getSelectedItem().toString(), DrpdwnSF.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** SELECT DATE FROM & DATE TO  **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					noOfMonths = calculateNoOfMonths();
					arrListData = daoRptSales_Object.getItmHstryRcrdPerDateFromAndDateTo(
							Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString()));
					createSaleReport(arrListData);
				}

				/** 03 PARAMETERS VALUES **/
				/** SELECT SIZE FROM & SIZE TO & SHAPE **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToShape(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							DrpDwnshape.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** SELECT SIZE FROM & SIZE TO & GRADE **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToGrade(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							Drpdwngrade.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** SELECT SURFACE FINISH & DATE FROM & DATE TO **/
				else if (!ChckboxSizeFrom.isSelected() && !ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					noOfMonths = calculateNoOfMonths();
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSFAndDteFromAndDteTo(
							DrpdwnSF.getSelectedItem().toString(),
							Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString()));
					createSaleReport(arrListData);
				}

				/** 04 PARAMETERS VALUES **/
				/** SELECT SIZE FROM & SIZE TO & SHAPE & GRADE **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && Chckboxshape.isSelected()
						&& Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& !ChkboxDateFrom.isSelected() && !ChkboxDateTo.isSelected()) {
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							DrpDwnshape.getSelectedItem().toString(), Drpdwngrade.getSelectedItem().toString());
					createSaleReport(arrListData);
				}

				/** SELECT SIZE FROM & SIZE TO & DATE FROM & DATE TO **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					noOfMonths = calculateNoOfMonths();
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToAndDteFromDteTo(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString()));
					createSaleReport(arrListData);
				}

				/** 05 PARAMETERS VALUES **/
				/** SELECT SIZE FROM & SIZE TO & SHAPE & DATE FROM & DATE TO **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && Chckboxshape.isSelected()
						&& !Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					noOfMonths = calculateNoOfMonths();
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							DrpDwnshape.getSelectedItem().toString(),
							Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString()));
					createSaleReport(arrListData);
				}

				/** SELECT SIZE FROM & SIZE TO & GRADE & DATE FROM & DATE TO **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && !Chckboxshape.isSelected()
						&& Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					noOfMonths = calculateNoOfMonths();
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							Drpdwngrade.getSelectedItem().toString(),
							Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString()));
					createSaleReport(arrListData);
				}

				/** 06 PARAMETERS VALUES **/
				/** SELECT SIZE FROM & SIZE TO & SHAPE & GRADE & DATE FROM & DATE TO **/
				else if (ChckboxSizeFrom.isSelected() && ChckboxSizeTo.isSelected() && Chckboxshape.isSelected()
						&& Chkboxgrade.isSelected() && !ChkboxIS.isSelected() && !ChkboxSF.isSelected()
						&& ChkboxDateFrom.isSelected() && ChkboxDateTo.isSelected()) {
					noOfMonths = calculateNoOfMonths();
					arrListData = daoRptSales_Object.getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo(
							DrpdwnSizeFrom.getSelectedItem().toString(), DrpdwnSizeTo.getSelectedItem().toString(),
							DrpDwnshape.getSelectedItem().toString(), Drpdwngrade.getSelectedItem().toString(),
							Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString()),
							Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString()));
					createSaleReport(arrListData);
				} else {
					MessageWindow.showMessage("Please select a valid parameters to proceed!", MessageType.ERROR);
				}
			}
		} catch (Exception SQLException) {
			SQLException.printStackTrace();
		}
	}

	/** CREATE REPORT DATA **/
	private void createSaleReport(ArrayList<Object> dtaCntnt) {
		Component[] componentList = Pnl_RptViewer.getComponents();
		for (Component c : componentList) {
			if (c instanceof JRViewer) {
				Pnl_RptViewer.remove(c);
			}
		}
		JRBeanCollectionDataSource tblDtaSrc;
		try {
			JasperDesign jasperDesign = JRXmlLoader
					.load(new File("").getAbsolutePath() + "/src/reports/RptSales.jrxml");
			JasperReport jsprRpt = JasperCompileManager.compileReport(jasperDesign);
			HashMap<String, Object> rptPramtrs = new HashMap<>();
			tblDtaSrc = new JRBeanCollectionDataSource(dtaCntnt);
			rptPramtrs.put("rptDtaSrc", tblDtaSrc);
			jasperPrint = JasperFillManager.fillReport(jsprRpt, rptPramtrs, new JREmptyDataSource());
			JRViewer rptViewer = new JRViewer(jasperPrint);
			Pnl_RptViewer.setLayout(new BorderLayout());
			Pnl_RptViewer.add(rptViewer);
			Pnl_RptViewer.repaint();
			Pnl_RptViewer.revalidate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** SET PANEL STATUS FOR OVERRULE CHECK BOX **/
	private void setPanelStatusForOverrule(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelStatusForOverrule((JPanel) components[i], isEnabled);
			}
			if (components[i] instanceof JCheckBox) {
				JCheckBox chckbox = (JCheckBox) components[i];
				chckbox.setSelected(false);
			}
			components[i].setEnabled(isEnabled);
		}
	}

	/** SET PANEL STATUS FOR LAST 01 YEAR CHECK BOX **/
	private void setPanelStatusForLastYear(JPanel panel, Boolean isEnabled) {
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JCheckBox"
					&& components[i].getName() != lastYearChckBoxName) {
				components[i].setEnabled(isEnabled);
			}
			if (components[i].getClass().getName() == "javax.swing.JLabel") {
				components[i].setEnabled(isEnabled);
			}
			if (components[i].getClass().getName() == "javax.swing.JComboBox") {
				components[i].setEnabled(isEnabled);
			}
		}
	}

	/** SET ALL CHECK BOXES STATUS **/
	private void setChckBoxStatus() {
		for (JCheckBox checkBox : allCheckBoxes) {
			if (checkBox.isSelected()) {
				checkBox.setSelected(false);
			}
		}
		ChckBoxUntickAll.setSelected(false);
	}

	/** CHECK USER INPUT STATUS **/
	private boolean checkUserInputStatus() {
		boolean isTicked = false;
		for (JCheckBox checkBox : allCheckBoxes) {
			if (checkBox.isSelected() || Chckboxoverruleall.isSelected() || ChckBoxLastYear.isSelected()) {
				isTicked = true;
				return isTicked;
			}
		}
		return isTicked;
	}

	/** CALCULATE NUMBER OF MONTHS **/
	private int calculateNoOfMonths() {
		int startMonth, endMonth, startYear, endYear, noOfMonths = 0;
		try {
			startMonth = Integer.parseInt(DrpDwnMonthFrom.getSelectedItem().toString());
			endMonth = Integer.parseInt(DrpDwnMonthTo.getSelectedItem().toString());
			startYear = Integer.parseInt(DrpDwnYearFrom.getSelectedItem().toString());
			endYear = Integer.parseInt(DrpDwnYearTo.getSelectedItem().toString());

			YearMonth date1 = YearMonth.of(startYear, startMonth);
			YearMonth date2 = YearMonth.of(endYear, endMonth);

			Period period = Period.between(date1.atDay(1), date2.atDay(1));
			noOfMonths = period.getMonths() + (period.getYears() * 12);
			if (noOfMonths >= 12) {
				return noOfMonths;
			} else {
				noOfMonths++;
			}
			System.out.println("Number of months between " + date1 + " and " + date2 + ": " + noOfMonths);
		} catch (Exception excelpt) {
		}
		return noOfMonths;
	}

	/** PROCESS EXECUTION IN BACKGROUND **/
	private void startBackgroundProcess() {
		SwingWorker<Void, String> swingWorker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				if (checkUserInputStatus()) {
					lblBusyProgress = UserCustomProgressBar.setStatus(true, true);
				}
				createReportFilters();
				return null;
			}

			@Override
			protected void done() {
				try {
					lblBusyProgress = UserCustomProgressBar.setStatus(false, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		swingWorker.execute();
	}
}
