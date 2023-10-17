/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Muhammad
 */
public class DaoCustomerOrderLogs {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** CREATE NEW CUSTOMER ORDER LOG ENTRY **/
	public boolean createNewCustomerOrderLog(int custOrderID, int custOrderStateIDFrom, int custOrderStateIDTo, int userID, String userSystemName)
			throws SQLException {
		boolean isInserted = false;
		final String createNewCustomerOrderLogQuery = """
				INSERT INTO [dbo].[tbl_Customer_Order_Logs]
				       ([CustomerOrderID]
				       ,[CustomerOrderStateIDFrom]
				       ,[CustomerOrderStateIDTo]
				       ,[CustomerOrderDateOccurance]
				       ,[UserID]
				       ,[UserSystemName])
				 VALUES
				       (?, ?, ?, GETDATE(), ?, ?)
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(createNewCustomerOrderLogQuery);
			stmnt.setInt(1, custOrderID);
			stmnt.setInt(2, custOrderStateIDFrom);
			stmnt.setInt(3, custOrderStateIDTo);
			stmnt.setInt(4, userID);
			stmnt.setString(5, userSystemName);
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
