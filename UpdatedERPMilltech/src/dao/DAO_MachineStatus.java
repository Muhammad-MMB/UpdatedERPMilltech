package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.tbl_machines;

public class DAO_MachineStatus {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE ALL MACHINES STATUS ALONG WITH FACTORIES NAME */
	public ArrayList<tbl_machines> getAllMachineStatus() throws SQLException {
		ArrayList<tbl_machines> getAllMachineStatusArray = new ArrayList<>();
		final String getAllMachineStatusQuery = """
				SELECT fct.FactoryName, mac.MachineCode, mac.MachineName, mac.MachineDescription, mac.MachineStdHrsPerMonth, mos.MachineOperationStateName, mos.MachineOperationStateID
				FROM tbl_Machines mac, tbl_Machine_Operation_States mos, tbl_Factories fct
				WHERE mac.FactoryID = fct.FactoryID
				AND mac.MachineOperationStateID = mos.MachineOperationStateID
				ORDER BY fct.FactoryName ASC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllMachineStatusQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllMachineStatusArray.add(new tbl_machines(rs.getString("FactoryName"), rs.getString("MachineCode"), rs.getString("MachineName"),
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
}
