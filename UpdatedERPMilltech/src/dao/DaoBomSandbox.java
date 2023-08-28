package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblBomSandbox;

public class DaoBomSandbox {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE SANDBOXPARENT STOCKCODE INFO **/
	public ArrayList<TblBomSandbox> getSandboxParentStockCode() throws SQLException {
		ArrayList<TblBomSandbox> getSandboxParentStockCode = new ArrayList<>();
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
							.add(new TblBomSandbox(rs.getInt("EndItemID"), rs.getString("EndItemName")));
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
	public ArrayList<TblBomSandbox> fetchAllSandboxRoutes() throws SQLException {
		ArrayList<TblBomSandbox> fetchAllSandboxRoutes = new ArrayList<>();
		final String fetchAllSandboxRoutesQuery = """
				SELECT sandBoxRoute.SandboxID AS SandboxGroupID, stock1.Stock_ID AS EndItemStockID, stock1.Stock_Code AS EndItemName, stock2.Stock_ID AS InFeedStockID, stock2.Stock_Code AS InItemName, mac.MachineID AS MachineID,
				mac.MachineName AS MachineName, sandBoxRoute.IsMoreChildExists AS moreChildExist,  sandBoxRoute.SandBoxParentID AS ParentID, sandBoxRoute.SandBoxRouteName AS RouteName, sandBoxRoute.SandBoxTonsPerHour AS TonsPerHour
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
					fetchAllSandboxRoutes.add(new TblBomSandbox(rs.getInt("SandboxGroupID"),
							rs.getInt("EndItemStockID"), rs.getString("EndItemName"), rs.getInt("InFeedStockID"),
							rs.getString("InItemName"), rs.getInt("MachineID"), rs.getString("MachineName"),
							rs.getBoolean("moreChildExist"), rs.getInt("ParentID"), rs.getString("RouteName"),
							rs.getDouble("TonsPerHour")));
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
			int parentID, String routeName, Double tonsPerHour) throws SQLException {
		final String setSandboxRouteQuery = """
				INSERT INTO [tbl_Bom_Sandbox]
				        ([SandBoxEndItemID]
				        ,[SandBoxInFeedItemID]
				        ,[SandBoxMachineID]
				        ,[IsMoreChildExists]
				        ,[SandBoxParentID]
				        ,[SandBoxRouteName],
				        [SandBoxTonsPerHour])
				  VALUES(?, ?, ?, ?, ?, ?, ?)
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
			stmnt.setDouble(7, tonsPerHour);
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
