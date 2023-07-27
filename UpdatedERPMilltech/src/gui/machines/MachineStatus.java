package gui.machines;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;

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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.poi.hpsf.Vector;

public class MachineStatus extends JFrame {

	private JTable TblMain;
	JPanel PnlMain;
	private DAO_MachineStatus machineStatusObject = null;
	private static final long serialVersionUID = 1L;
	private DefaultTableCellRenderer cellRenderer;
	private String colNames[] = { "S. No", "Factory Name", "Machine Name", "Machine Description",
			" Machine Std Hours / Month", "Machine Current State", "Machine Icon" };

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
		String factoryName, machineName, machineDescription, machineStatus;
		int machineStdHours, machineStatusID;
		ImageIcon machineStatusIcon = null;
		Image image;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 526, 1544, 402);
		PnlMain.add(scrollPane);

		TblMain = new JTable() {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				return (column == 6) ? ImageIcon.class : Object.class;
			}
		};
		scrollPane.setViewportView(TblMain);
		TblMain.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		TblMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		DefaultTableModel tableModel = (DefaultTableModel) TblMain.getModel();
		tableModel.setColumnIdentifiers(colNames);

		try {
			machineArray = machineStatusObject.getAllMachineStatus();
			for (int item = 0; item < machineArray.size(); item++) {
				factoryName = machineArray.get(item).getFactoryName();
				machineName = machineArray.get(item).getMachineName();
				machineDescription = machineArray.get(item).getMachineedescription();
				machineStdHours = machineArray.get(item).getMachineStdHrsPerMonth();
				machineStatus = machineArray.get(item).getMachineOperatingStatusName();
				machineStatusID = machineArray.get(item).getMachineOperatingStatusID();

				if (machineStatusID == AppConstants.READY) {
					image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_GREEN);
					image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
					machineStatusIcon = new ImageIcon(image);
				} else if (machineStatusID == AppConstants.BUSY) {
					image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_YELLOW);
					image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
					machineStatusIcon = new ImageIcon(image);
				} else {
					image = ReadResources.getImageFromResourceAsURL(AppConstants.SHORT_RED);
					image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
					machineStatusIcon = new ImageIcon(image);
				}

				tableModel.addRow(new Object[] { item + 1, factoryName, machineName, machineDescription,
						machineStdHours, machineStatus, machineStatusIcon });
			}

			setColumnWidths(TblMain, 50);
			((DefaultTableCellRenderer) TblMain.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.LEFT);
			
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)TblMain.getDefaultRenderer(ImageIcon.class);
			renderer.setHorizontalAlignment( JLabel.LEFT );
			TblMain.getColumnModel().getColumn(6).setCellRenderer(renderer);

			
			TblMain.setRowHeight(28);

		} catch (

		SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TblMain.setModel(tableModel);
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
}
