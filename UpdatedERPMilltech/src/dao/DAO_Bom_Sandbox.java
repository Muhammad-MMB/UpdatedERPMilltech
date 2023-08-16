package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.tbl_bom_sandbox;

public class DAO_Bom_Sandbox {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE SANDBOXPARENT STOCKCODE INFO **/
	public ArrayList<tbl_bom_sandbox> getSandboxParentStockCode() throws SQLException {
		ArrayList<tbl_bom_sandbox> getSandboxParentStockCode = new ArrayList<>();
		final String getSandboxParentStockCodeQuery = """
				SELECT TOP 1 sandBoxRoute.SandBoxInFeedItemID AS EndItemID, stock1.Stock_Code AS EndItemName
				FROM tbl_Bom_Sandbox sandBoxRoute
				INNER JOIN tbl_Stock_List stock1 ON sandBoxRoute.SandBoxEndItemID = stock1.Stock_ID
				ORDER BY stock1.Stock_ID desc

				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getSandboxParentStockCodeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getSandboxParentStockCode
					.add(new tbl_bom_sandbox(rs.getInt("EndItemID"), rs.getString("EndItemName")));
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
		return getSandboxParentStockCode;
	}

	/** RETRIEVE ALL EXISTING SANDBOX ROUTES **/
	public ArrayList<tbl_bom_sandbox> fetchAllSandboxRoutes() throws SQLException {
		ArrayList<tbl_bom_sandbox> fetchAllSandboxRoutes = new ArrayList<>();
		final String fetchAllSandboxRoutesQuery = """
				SELECT stock1.Stock_ID AS EndItemStockID, stock1.Stock_Code AS EndItemName, stock2.Stock_ID AS InFeedStockID, stock2.Stock_Code AS InItemName, mac.MachineID AS MachineID,
				mac.MachineName AS MachineName, sandBoxRoute.IsMoreChildExists AS moreChildExist,  sandBoxRoute.SandBoxParentID AS ParentID, sandBoxRoute.SandBoxRouteName AS RouteName
				FROM tbl_Bom_Sandbox sandBoxRoute
				INNER JOIN tbl_Stock_List stock1 ON sandBoxRoute.SandBoxEndItemID = stock1.Stock_ID
				INNER JOIN tbl_Stock_List stock2 ON sandBoxRoute.SandBoxInFeedItemID = stock2.Stock_ID
				INNER JOIN tbl_Machines mac ON sandBoxRoute.SandBoxMachineID = mac.MachineID

				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(fetchAllSandboxRoutesQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					fetchAllSandboxRoutes.add(new tbl_bom_sandbox(rs.getInt("EndItemStockID"),
							rs.getString("EndItemName"), rs.getInt("InFeedStockID"), rs.getString("InItemName"),
							rs.getInt("MachineID"), rs.getString("MachineName"), rs.getBoolean("moreChildExist"),
							rs.getInt("ParentID"), rs.getString("RouteName")));
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
		return fetchAllSandboxRoutes;
	}

	/** SEND SANDBOX ROUTE ENTRY INTO DATABASE **/
	public void setSandboxRoute(int endItemStockID, int inFeedStockID, int machineID, boolean moreChildExists,
			int parentID, String routeName) throws SQLException {
		final String setSandboxRouteQuery = """
				INSERT INTO [dbo].[tbl_Bom_Sandbox]
				        ([SandBoxEndItemID]
				        ,[SandBoxInFeedItemID]
				        ,[SandBoxMachineID]
				        ,[IsMoreChildExists]
				        ,[SandBoxParentID]
				        ,[SandBoxRouteName])
				  VALUES(?, ?, ?, ?, ?, ?)
						""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setSandboxRouteQuery);
			stmnt.setInt(1, endItemStockID);
			stmnt.setInt(2, inFeedStockID);
			stmnt.setInt(3, machineID);
			stmnt.setBoolean(4, moreChildExists);
			stmnt.setInt(5, parentID);
			stmnt.setString(6, routeName);
			stmnt.executeUpdate();

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
	}

	/** DELETE ALL SANDBOX TABLE ENTRIES **/
	public void deleteAllTblSandboxRecords() throws SQLException {
		final String setSandboxRouteQuery = """
				DELETE from tbl_Bom_Sandbox
						""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setSandboxRouteQuery);
			stmnt.executeUpdate();

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
	}

}
