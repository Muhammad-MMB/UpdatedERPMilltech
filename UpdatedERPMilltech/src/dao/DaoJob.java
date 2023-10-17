/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.TblJob;
import entities.TblJob.JobCreated;
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
	public boolean createNewJob(double jobQuantity, boolean jobPriority, TblJobState jobState, boolean isActive, String jobNotes) throws SQLException {
		boolean isInserted = false;
		final String createNewJob = """
				INSERT INTO [dbo].[tbl_Job]
				       ([JobQty]
				       ,[JobPriority]
				       ,[JobStateID]
				       ,[Datetime]
				       ,[IsActive]
				       ,[JobNotes])
				 VALUES
				       (?, ?, ?, GETDATE(), ?, ?)
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(createNewJob);
			stmnt.setDouble(1, jobQuantity);
			stmnt.setBoolean(2, jobPriority);
			stmnt.setInt(3, jobState.getJobStateId());
			stmnt.setBoolean(4, isActive);
			stmnt.setString(5, jobNotes);
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

	/** RETRIEVE LAST 300 JOBS **/
	public ArrayList<JobCreated> fetchLastFewJobs() throws SQLException {
		ArrayList<JobCreated> fetchLastFewJobs = new ArrayList<>();
		final String fetchLastFewJobsQuery = """
				SELECT TOP 300 ROW_NUMBER() OVER (ORDER BY bomRoute.EndItemStockID ASC) AS "SerialNo", job.JOBID AS JobID, bomRoute.BOMRouteID AS BomRouteID, stock1.Stock_Code AS EndItemName, stock2.Stock_Code AS InfeedItemName, mac.MachineName AS MachineName,
				job.JobQuantity AS JobQuantity, job.JobNotes AS JobNotes, jobState.JobStateName AS JobStateName, job.JobPriority AS JobPriority, CAST(job.Date AS date) as DateOnly,
				CONVERT(VARCHAR(8), job.Date, 108) + ' ' + RIGHT(CONVERT(VARCHAR(30), job.Date, 9), 2) as TimeOnly
				FROM tbl_Job AS job
				INNER JOIN tbl_Job_State jobState ON job.JobStateID = jobState.JobStateID
				INNER JOIN tbl_Bom_Route bomRoute ON job.BomRouteID = bomRoute.BOMRouteID
				INNER JOIN tbl_Stock_List stock1 ON bomRoute.EndItemStockID = stock1.Stock_ID
				INNER JOIN tbl_Stock_List stock2 ON bomRoute.InFeedItemStockID = stock2.Stock_ID
				INNER JOIN tbl_Machines mac ON bomRoute.MachineID = mac.MachineID
				WHERE job.IsActive = 1
				AND jobState.JobStateID <> 3 AND jobState.JobStateID <> 4
				ORDER BY bomRoute.EndItemStockID ASC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(fetchLastFewJobsQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					fetchLastFewJobs.add(new JobCreated(rs.getInt("SerialNo"), rs.getInt("JobID"),
							rs.getInt("BomRouteID"), rs.getString("EndItemName"), rs.getString("InfeedItemName"),
							rs.getString("MachineName"), rs.getDouble("JobQuantity"), rs.getString("JobNotes"),
							rs.getString("JobStateName"), rs.getBoolean("JobPriority"), rs.getString("DateOnly"),
							rs.getString("TimeOnly")));
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
		return fetchLastFewJobs;
	}

	/** RETRIEVE ALL UNPLANNED JOBS **/
	public ArrayList<JobCreated> fetchUnplannedJobs() throws SQLException {
		ArrayList<JobCreated> fetchUnplannedJobs = new ArrayList<>();
		final String fetchUnplannedJobsQuery = """
				SELECT ROW_NUMBER() OVER (ORDER BY bomRoute.EndItemStockID ASC) AS "SerialNo", bomRoute.BOMRouteID AS BomRouteID,  stock1.Stock_Code AS EndItemName, stock2.Stock_Code AS InfeedItemName, mac.MachineName AS MachineName,
				stock2.Stock_QuantityInHand AS QuantityStock
				FROM tbl_Job job
				RIGHT JOIN tbl_Bom_Route bomRoute ON job.BomRouteID = bomRoute.BOMRouteID
				INNER JOIN tbl_Stock_List stock1 ON bomRoute.EndItemStockID = stock1.Stock_ID
				INNER JOIN tbl_Stock_List stock2 ON bomRoute.InFeedItemStockID = stock2.Stock_ID
				INNER JOIN tbl_Machines mac ON bomRoute.MachineID = mac.MachineID
				WHERE job.BomRouteID IS NULL
				ORDER BY bomRoute.EndItemStockID ASC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(fetchUnplannedJobsQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					fetchUnplannedJobs.add(new JobCreated(rs.getInt("SerialNo"), rs.getInt("BomRouteID"),
							rs.getString("EndItemName"), rs.getString("InfeedItemName"), rs.getString("MachineName"),
							rs.getDouble("QuantityStock")));
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
		return fetchUnplannedJobs;
	}

	/** RETRIEVE LAST JOB FOR JOB DETAIL **/
	public TblJob getLastJob() throws SQLException {
		TblJob jobObject = null;
		final String getLastJobQuery = """
				SELECT TOP (1) Job.JobID AS JobID
				FROM tbl_Job AS Job
				ORDER BY Job.JobID DESC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getLastJobQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				jobObject = new TblJob(rs.getInt("JobID"));
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
		return jobObject;
	}
}
