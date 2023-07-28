package gui.machines;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;


public class MachineStatus extends JFrame {

	private JTable TblMain;
	JPanel PnlMain;
	private DAO_MachineStatus machineStatusObject = null;
	private static final long serialVersionUID = 1L;
	private String colNames[] = { "S. No", "Factory Name", "Machine Code", "Machine Name", "Machine Description",
			" Machine Std Hours / Month", "Machine Current State", "Machine Symbol" };

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
		Image image;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 568, 1544, 360);
		PnlMain.add(scrollPane);

		TblMain = new JTable() {
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
				return (column == 7) ? ImageIcon.class : Object.class;
			}
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
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

			setColumnWidths(TblMain, 50);
			((DefaultTableCellRenderer) TblMain.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.LEFT);
			
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)TblMain.getDefaultRenderer(ImageIcon.class);
			renderer.setHorizontalAlignment( JLabel.LEFT );
			TblMain.getColumnModel().getColumn(7).setCellRenderer(renderer);
			TblMain.setRowHeight(28);

		} catch (

		SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TblMain.setModel(tableModel);
		
		JPanel PnlMchneInfo = new JPanel();
		PnlMchneInfo.setBackground(new Color(255, 255, 255));
		PnlMchneInfo.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Machine Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Machine Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		PnlMchneInfo.setBounds(10, 11, 1544, 546);
		PnlMain.add(PnlMchneInfo);
	}

	private void setColumnWidths(JTable table, int... widths) {
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < widths.length; i++) {
			if (i < columnModel.getColumnCount()) {
				columnModel.getColumn(i).setMaxWidth(widths[i]);
			} else
				break;
		}
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
		}
		catch (Exception excpt){
			
		}
		return machineIcon;
	}
	
}
