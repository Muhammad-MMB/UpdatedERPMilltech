/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entities.TblJobState;

/**
 * @author Muhammad
 *
 */
public class DaoJobState {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE DEFAULT JOB STATE **/
	public void setDefaultJobState(TblJobState jobState) throws SQLException {
		final String getDefaultJobState = """
				SELECT TOP 1 jobState.JobStateID AS JobStateID, jobState.JobStateName AS JobStateName
				FROM tbl_Job_State jobState
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getDefaultJobState);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					jobState.setJobStateId(rs.getInt("JobStateID"));
					jobState.setJobStateName(rs.getString("JobStateName"));
					
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
	}
}
