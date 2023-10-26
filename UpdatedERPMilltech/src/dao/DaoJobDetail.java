/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.TblJobDetails;
import entities.TblMachines;

/**
 * 
 */
public class DaoJobDetail {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** CREATE NEW JOB DETAIL **/
	public boolean createNewJobDetail(int jobID, int orderID, int stockID, int bomRouteID, boolean isActive)
			throws SQLException {
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

	/** RETRIEVE JOB & ITS DETAILS BY JOB STATE ID **/
	public ArrayList<TblJobDetails> getJobDetailsByJobStateID(int jobStateID) throws SQLException {
		ArrayList<TblJobDetails> getJobDetailsArray = new ArrayList<>();
		final String getJobDetailsQuery = """
				SELECT Job.JobID AS JobNo, Job.JobQty AS JobQty, Job.JobPriority AS JobPriority, JobState.JobStateName AS JobState, Job.JobNotes AS JobNotes,
				CustomerOrder.OrderNo AS CustomerOrderNo, CustomerOrder.OrderQty AS CustomerOrderQty, StockList.Stock_Code AS EndItemCode, CAST(Job.Datetime AS date) as DateOnly,
				CONVERT(VARCHAR(8), Job.Datetime, 108) + ' ' + RIGHT(CONVERT(VARCHAR(30), Job.Datetime, 9), 2) as TimeOnly
				FROM tbl_Job AS Job
				INNER JOIN tbl_Job_Details AS JobDetail ON JobDetail.JobID = Job.JobID
				INNER JOIN tbl_Customer_Order AS CustomerOrder ON CustomerOrder.OrderID = JobDetail.OrderID
				INNER JOIN tbl_Stock_List AS StockList ON StockList.Stock_ID = JobDetail.StockID
				INNER JOIN tbl_Bom_Route AS BomRoute ON BomRoute.BOMRouteID = JobDetail.BomRouteID
				INNER JOIN tbl_Job_State AS JobState ON JobState.JobStateID = Job.JobStateID
				WHERE Job.JobStateID = ?
				ORDER BY Job.JobID DESC
			""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getJobDetailsQuery);
			stmnt.setInt(1, jobStateID);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getJobDetailsArray.add(new TblJobDetails(rs.getInt("JobNo"), rs.getDouble("JobQty"), rs.getBoolean("JobPriority"), rs.getString("JobState"),
							rs.getString("JobNotes"), rs.getString("DateOnly"), rs.getString("TimeOnly"), rs.getString("CustomerOrderNo"), rs.getString("EndItemCode"), rs.getDouble("CustomerOrderQty")));
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
		return getJobDetailsArray;
	}

}
