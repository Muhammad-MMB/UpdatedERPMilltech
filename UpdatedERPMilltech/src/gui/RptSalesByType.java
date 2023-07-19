package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import dao.DAO_HistoricalSales;
import entities.tbl_stock_list;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RptSalesByType extends JFrame {

	private static final long serialVersionUID = 1L;
	DAO_HistoricalSales daoHSObject;
	JPanel rptOutputPnl;
	JCheckBox chckbxViewAll;
	JComboBox<String> drpdwnType;
	ArrayList<tbl_stock_list> stockAllData;

	public RptSalesByType() throws SQLException {

		daoHSObject = new DAO_HistoricalSales();
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(RptSalesByType.class.getResource("/resources/images/historySales.png")));
		setTitle("View Historical Sales");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		JPanel rptInputPnl = new JPanel();
		rptInputPnl.setBorder(
				new TitledBorder(null, "Generate Sales Report", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rptInputPnl.setBackground(UIManager.getColor("Button.background"));
		rptInputPnl.setBounds(10, 11, 964, 94);
		getContentPane().add(rptInputPnl);
		rptInputPnl.setLayout(null);

		JLabel lblType = new JLabel("Select Type");
		lblType.setBounds(94, 35, 74, 14);
		rptInputPnl.add(lblType);

		drpdwnType = new JComboBox<String>();
		drpdwnType.setBounds(190, 31, 378, 22);
		drpdwnType.setModel(
				new DefaultComboBoxModel<String>(daoHSObject.getAllListOfStockTypes().toArray(new String[0])));
		rptInputPnl.add(drpdwnType);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				generateReport();
			}
		});
		btnSubmit.setHorizontalTextPosition(SwingConstants.LEFT);
		btnSubmit.setBounds(754, 31, 129, 22);
		rptInputPnl.add(btnSubmit);

		chckbxViewAll = new JCheckBox("View all");
		chckbxViewAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxViewAll.isSelected()) {
					drpdwnType.setEnabled(false);
				} else {
					drpdwnType.setEnabled(true);
				}
			}
		});
		chckbxViewAll.setBounds(617, 31, 107, 23);
		rptInputPnl.add(chckbxViewAll);
		rptOutputPnl = new JPanel();
		rptOutputPnl.setBounds(10, 116, 964, 534);
		getContentPane().add(rptOutputPnl);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void generateReport() {
		rptOutputPnl.removeAll();
		rptOutputPnl.repaint();
		rptOutputPnl.revalidate();

		try {
			JasperDesign jasperDesign = JRXmlLoader
					.load(new File("").getAbsolutePath() + "/src/reports/RptSalesByType.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			HashMap<String, Object> rptParameters = new HashMap<>();

			// Stock_List oneStock = new Stock_List("asdas", "ddd", "sds", "dddd", "ere",
			// "dedfg", "defe", "dere");
			// Stock_List oneStock2 = new Stock_List("22asdas", "22ddd", "22sds", "d22ddd",
			// "e222re", "d22edfg", "d22efe", "d22ere");

			// rptParameters.put("sales_type", "ARDWN");

			if (!chckbxViewAll.isSelected()) {
				String selectedValue = drpdwnType.getSelectedItem().toString();
				stockAllData = new ArrayList<>();
				stockAllData = daoHSObject.getStockListByType(selectedValue, 1);
			} else {
				stockAllData = new ArrayList<>();
				stockAllData = daoHSObject.getStockListByType("", 2);
			}
			// stockAllData.add(oneStock);
			// stockAllData.add(oneStock2);

			JRBeanCollectionDataSource tableDtaSource = new JRBeanCollectionDataSource(stockAllData);
			rptParameters.put("stock_type_param", tableDtaSource);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, rptParameters,
					new JREmptyDataSource());

			JRViewer rptViewer = new JRViewer(jasperPrint);
			rptOutputPnl.setLayout(new BorderLayout());
			rptOutputPnl.add(rptViewer);

		} catch (Exception ex) {
			ex.printStackTrace();
			// new MessageWindow(this, "Error","Error in Reporting");
		}
	}

}
