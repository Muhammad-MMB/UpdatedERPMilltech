/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	/** GET LIST OF ALL JOB STATES **/
	public ArrayList<TblJobState> getAllJobState() throws SQLException {
		 ArrayList<TblJobState> getAllJobStateArray = new ArrayList<>();
		final String getAllJobStateQuery = """
				SELECT JobState.JobStateID AS JobStateID, JobState.JobStateName AS JobStateName
				FROM tbl_Job_State JobState
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllJobStateQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllJobStateArray.add(new TblJobState(rs.getInt("JobStateID"), rs.getString("JobStateName")));
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
		return getAllJobStateArray;
	}
}
