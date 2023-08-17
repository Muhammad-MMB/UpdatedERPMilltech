package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblMachines;

public class DaoMachineStatus {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE ALL MACHINES STATUS ALONG WITH FACTORIES NAME */
	public ArrayList<TblMachines> getAllMachineStatus() throws SQLException {
		ArrayList<TblMachines> getAllMachineStatusArray = new ArrayList<>();
		final String getAllMachineStatusQuery = """
				SELECT mac.MachineID, fct.FactoryName, mcode.MachineCode, mac.MachineName, mac.MachineDescription, mac.MachineStdHrsPerMonth, mos.MachineOperationStateName, mos.MachineOperationStateID
				FROM tbl_Machines mac, tbl_Machine_Operation_States mos, tbl_Factories fct, tbl_Machine_Codes mcode
				WHERE mac.FactoryID = fct.FactoryID
				AND mac.MachineOperationStateID = mos.MachineOperationStateID
				AND mcode.MachineCodeID = mac.MachineCodeID
				ORDER BY fct.FactoryName ASC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllMachineStatusQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllMachineStatusArray.add(new TblMachines(rs.getInt("MachineID") ,rs.getString("FactoryName"), rs.getString("MachineCode"), rs.getString("MachineName"),
							rs.getString("MachineDescription"), rs.getInt("MachineStdHrsPerMonth"),
							rs.getString("MachineOperationStateName"), rs.getInt("MachineOperationStateID")));
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmnt != null) {
				stmnt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return getAllMachineStatusArray;
	}
	
	/** UPDATE MACHINE OPERATING STATUS BY MACHINE ID */
	public void setMachineStatus(int machineStatusID, int machineID) throws SQLException {
		final String setMachineStatusQuery = """
				UPDATE tbl_Machines
				SET
				MachineOperationStateID = ?
				WHERE MachineID = ?
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setMachineStatusQuery);
			stmnt.setInt(1, machineStatusID);
			stmnt.setInt(2, machineID);
			stmnt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmnt != null) {
				stmnt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
	
}