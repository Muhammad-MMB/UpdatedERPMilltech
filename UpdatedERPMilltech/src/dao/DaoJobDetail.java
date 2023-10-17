/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 */
public class DaoJobDetail {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** CREATE NEW JOB DETAIL **/
	public boolean createNewJobDetail(int jobID, int orderID, int stockID, int bomRouteID, boolean isActive) throws SQLException {
		boolean isInserted = false;
		final String createNewJobDetailQuery = """
				INSERT INTO [dbo].[tbl_Job_Details]
				       ([JobID]
				       ,[OrderID]
				       ,[StockID]
				       ,[BomRouteID]
				       ,[IsActive]
				       ,[DateCreated])
				 VALUES
				       (?, ?, ?, ?, ?, GETDATE())
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(createNewJobDetailQuery);
			stmnt.setInt(1, jobID);
			stmnt.setInt(2, orderID);
			stmnt.setInt(3, stockID);
			stmnt.setInt(4, bomRouteID);
			stmnt.setBoolean(5, isActive);
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
