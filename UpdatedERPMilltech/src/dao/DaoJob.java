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
public class DaoJob {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** CREATE NEW JOB **/
	public boolean createNewJob(int bomRouteID, double quantity, String notes, TblJobState jobState, boolean isActive, boolean jobPriority)
			throws SQLException {
		boolean isInserted = false;
		final String createNewJob = """
				INSERT INTO [dbo].[tbl_Job]
				      ([BomRouteID]
				      ,[JobQuantity]
				      ,[JobNotes]
				      ,[JobStateID]
				      ,[IsActive]
				      ,[Date]
				      ,[JobPriority])
				VALUES
				(?, ?, ?, ?, ?, GETDATE(), ?)
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(createNewJob);
			stmnt.setInt(1, bomRouteID);
			stmnt.setDouble(2, quantity);
			stmnt.setString(3, notes);
			stmnt.setInt(4, jobState.getJobStateId());
			stmnt.setBoolean(5, isActive);
			stmnt.setBoolean(6, jobPriority);
			isInserted = stmnt.executeUpdate() > 0;

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
		return isInserted;
	}
}
