package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.TblMachineOperationStates;

/**
 * @author Muhammad
 *
 */
public class DaoMachineOprState {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE ALL POSSIBLE MACHINE STATES */
	public ArrayList<TblMachineOperationStates> getAllMachineStates() throws SQLException {
		ArrayList<TblMachineOperationStates> getAllMachineStatesArray = new ArrayList<>();
		final String getAllMachineStatesQuery = "SELECT * FROM tbl_Machine_Operation_States";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllMachineStatesQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllMachineStatesArray.add(new TblMachineOperationStates(rs.getInt("MachineOperationStateID"),
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
