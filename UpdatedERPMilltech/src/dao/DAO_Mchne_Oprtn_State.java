package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.tbl_machine_operation_states;

/**
 * @author Muhammad
 *
 */
public class DAO_Mchne_Oprtn_State {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE ALL POSSIBLE MACHINE STATES */
	public ArrayList<tbl_machine_operation_states> getAllMachineStates() throws SQLException {
		ArrayList<tbl_machine_operation_states> getAllMachineStatesArray = new ArrayList<>();
		final String getAllMachineStatesQuery = "SELECT * FROM tbl_Machine_Operation_States";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllMachineStatesQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllMachineStatesArray.add(new tbl_machine_operation_states(rs.getInt("MachineOperationStateID"),
							rs.getString("MachineOperationStateName"),
							rs.getString("MachineOperationStateDescription")));
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
		return getAllMachineStatesArray;
	}
}
