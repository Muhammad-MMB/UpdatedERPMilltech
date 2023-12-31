/**
 * 
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import entities.TblBomRoute;

/**
 * @author Muhammad
 */
public class DaoBomRoute {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE LIST OF ALL STOCK CODES **/
	public ArrayList<TblBomRoute> getAllStockInfo(int queryType, String stockCode) throws SQLException {
		ArrayList<TblBomRoute> getAllStockCodesArray = new ArrayList<>();
		final String getAllStockCodesQuery =
				"SELECT DISTINCT Stock_ID, Stock_Code FROM tbl_Stock_List ORDER BY Stock_Code ASC";
		final String getAllStockCodesQueryByStockCode =
				"SELECT DISTINCT Stock_ID, Stock_Code FROM tbl_Stock_List WHERE Stock_Code = ? ORDER BY Stock_Code ASC";
		try {
			con = DataSource.getConnection();
			if (queryType == 1) {
				stmnt = con.prepareStatement(getAllStockCodesQuery);
				rs = stmnt.executeQuery();
			} else {
				stmnt = con.prepareStatement(getAllStockCodesQueryByStockCode);
				stmnt.setString(1, stockCode);
				rs = stmnt.executeQuery();
			}
			if (rs.next()) {
				do {
					getAllStockCodesArray.add(new TblBomRoute(rs.getString("Stock_Code"), rs.getInt("Stock_ID")));
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
		return getAllStockCodesArray;
	}

	/** RETRIEVE LIST OF ALL MACHINES NAMES **/
	public ArrayList<TblBomRoute> getAllMachineInfo(int queryType, String machineName) throws SQLException {
		ArrayList<TblBomRoute> getAllMachineInfoArray = new ArrayList<>();
		final String getAllMachineInfoQuery = "SELECT MachineID, MachineName FROM tbl_Machines ORDER BY MachineID ASC";
		final String getAllMachineInfoQueryByMachineName =
				"SELECT MachineID, MachineName FROM tbl_Machines WHERE MachineName = ? ORDER BY MachineID ASC";
		try {
			con = DataSource.getConnection();
			if (queryType == 1) {
				stmnt = con.prepareStatement(getAllMachineInfoQuery);
				rs = stmnt.executeQuery();
			} else {
				stmnt = con.prepareStatement(getAllMachineInfoQueryByMachineName);
				stmnt.setString(1, machineName);
				rs = stmnt.executeQuery();
			}
			if (rs.next()) {
				do {
					getAllMachineInfoArray.add(new TblBomRoute(rs.getInt("MachineID"), rs.getString("MachineName")));
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
		return getAllMachineInfoArray;
	}

	/** SEND BOM ROUTE ENTRY INTO DATABASE **/
	public void setBomRoute(int endItemStockID, int inFeedStockID, int machineID, String routeName, int sandboxGroupID,
			boolean isActive, boolean isUnique,  Double tonsPerHour) throws SQLException {
		final String setBomRouteQuery = """
				INSERT INTO tbl_Bom_Route(
				EndItemStockID,
				InFeedItemStockID,
				MachineID,
				RouteName,
				SandboxGroupID,
				IsActive,
				IsUnique,
				Date,
				TonsPerHour)
				VALUES(?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)
					""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setBomRouteQuery);
			stmnt.setInt(1, endItemStockID);
			stmnt.setInt(2, inFeedStockID);
			stmnt.setInt(3, machineID);
			stmnt.setString(4, routeName);
			stmnt.setInt(5, sandboxGroupID);
			stmnt.setBoolean(6, isActive);
			stmnt.setBoolean(7, isUnique);
			stmnt.setDouble(8, tonsPerHour);
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

	/** RETRIEVE EXISTING BOM ROUTE WITH INPUT PARAMETERS **/
	public boolean isBomRouteAlreadyExist(int endItem, int feedItem, int machineID) throws SQLException {
		boolean isFoundRecord = false;
		final String getBomRouteCheckQuery =
				"""   
				    SELECT BOMRouteID FROM tbl_Bom_Route bomRoute
				    WHERE bomRoute.EndItemStockID = ?
				    AND bomRoute.InFeedItemStockID = ?
				    AND bomRoute.MachineID = ?
				    AND bomRoute.IsActive = 1
				    AND bomRoute.IsUnique = 1
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getBomRouteCheckQuery);
			stmnt.setInt(1, endItem);
			stmnt.setInt(2, feedItem);
			stmnt.setInt(3, machineID);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				isFoundRecord = true;
			} else {
				isFoundRecord = false;
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
		return isFoundRecord;
	}

	/** RETRIEVE ALL EXISTING BOM ROUTE **/
	public ArrayList<TblBomRoute> fetchAllBomRoutes(int sandboxGroupID, int bomRouteID, int queryType)
			throws SQLException {
		ArrayList<TblBomRoute> fetchAllBomRoutes = new ArrayList<>();
		final String fetchAllBomRoutesQueryBySandboxID = """
				SELECT bomRoute.BOMRouteID AS BomRouteID, stock1.Stock_ID AS EndItemStockID, stock1.Stock_Code AS EndItemName, stock2.Stock_ID AS InFeedStockID, stock2.Stock_Code AS InItemName,
				mac.MachineID AS MachineID, mac.MachineName AS MachineName, bomRoute.RouteName, bomRoute.TonsPerHour AS TonsPerHour, stock2.Stock_QuantityInHand AS InFeedQuantityInHand
				FROM tbl_Bom_Route bomRoute
				INNER JOIN tbl_Stock_List stock1 ON bomRoute.EndItemStockID = stock1.Stock_ID
				INNER JOIN tbl_Stock_List stock2 ON bomRoute.InFeedItemStockID = stock2.Stock_ID
				INNER JOIN tbl_Machines mac ON bomRoute.MachineID = mac.MachineID
				AND bomRoute.SandboxGroupID = ?
				""";

		final String fetchAllBomRoutesQueryByBomRouteID = """
					SELECT bomRoute.BOMRouteID AS BomRouteID, stock1.Stock_ID AS EndItemStockID, stock1.Stock_Code AS EndItemName, stock2.Stock_ID AS InFeedStockID, stock2.Stock_Code AS InItemName,
					mac.MachineID AS MachineID, mac.MachineName AS MachineName, bomRoute.RouteName, bomRoute.TonsPerHour AS TonsPerHour, stock2.Stock_QuantityInHand AS InFeedQuantityInHand
					FROM tbl_Bom_Route bomRoute
					INNER JOIN tbl_Stock_List stock1 ON bomRoute.EndItemStockID = stock1.Stock_ID
					INNER JOIN tbl_Stock_List stock2 ON bomRoute.InFeedItemStockID = stock2.Stock_ID
					INNER JOIN tbl_Machines mac ON bomRoute.MachineID = mac.MachineID
					WHERE bomRoute.BOMRouteID = ?
				""";

		try {
			con = DataSource.getConnection();
			if (queryType == 1) {
				stmnt = con.prepareStatement(fetchAllBomRoutesQueryBySandboxID);
				stmnt.setInt(1, sandboxGroupID);
			} else {
				stmnt = con.prepareStatement(fetchAllBomRoutesQueryByBomRouteID);
				stmnt.setInt(1, bomRouteID);
			}
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					fetchAllBomRoutes.add(new TblBomRoute(rs.getInt("BomRouteID"), rs.getInt("EndItemStockID"),
							rs.getString("EndItemName"), rs.getInt("InFeedStockID"), rs.getString("InItemName"),
							rs.getInt("MachineID"), rs.getString("MachineName"), rs.getString("RouteName"),
							rs.getDouble("TonsPerHour"), rs.getDouble("InFeedQuantityInHand")));
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
		return fetchAllBomRoutes;
	}

	/** RETRIEVE LIST OF ALL STORED ROUTES **/
	public ArrayList<TblBomRoute> getAllListOfRoutes(boolean activeRecords) throws SQLException {
		ArrayList<TblBomRoute> getAllListOfRoutesArray = new ArrayList<>();
		final String getAllActiveListOfRoutesQuery =
				"""
				SELECT BOMRouteID AS RouteID, RouteName AS RouteName, SandboxGroupID AS RouteGroupID, EndItemStockID AS EndItemID
				FROM tbl_Bom_Route
				WHERE RouteName IS NOT NULL AND TRIM(RouteName) <> '' AND IsActive = 1
				ORDER BY EndItemID ASC
				""";
		final String getAllInActiveListOfRoutesQuery =
				"""
				SELECT BOMRouteID AS RouteID, RouteName AS RouteName, SandboxGroupID AS RouteGroupID, EndItemStockID AS EndItemID
				FROM tbl_Bom_Route
				WHERE RouteName IS NOT NULL AND TRIM(RouteName) <> '' AND IsActive = 0
				ORDER BY EndItemID ASC
				""";
		try {
			con = DataSource.getConnection();
			if(activeRecords){
				stmnt = con.prepareStatement(getAllActiveListOfRoutesQuery);	
			}
			else {
				stmnt = con.prepareStatement(getAllInActiveListOfRoutesQuery);
			}
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllListOfRoutesArray.add(new TblBomRoute(rs.getInt("RouteID"), rs.getString("RouteName"), rs
							.getInt("RouteGroupID"), rs.getInt("EndItemID")));
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
		return getAllListOfRoutesArray;
	}

	/** UPDATE BOM ROUTE TONE PER HOUR AS PER BOM ROUTE ID **/
	public boolean updateTonePerHour(double tonePerHour, int bomRouteID) throws SQLException {
		boolean isUpdated = false;
		final String updateTonePerHour = """
					UPDATE bomRoute
					SET bomRoute.TonsPerHour = ?
					FROM tbl_Bom_Route bomRoute
					WHERE bomRoute.BOMRouteID = ?
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(updateTonePerHour);
			stmnt.setDouble(1, tonePerHour);
			stmnt.setInt(2, bomRouteID);
			isUpdated = stmnt.executeUpdate() > 0;

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
		return isUpdated;
	}

	/** DEACTIVATE WHOLE BOM ROUTE BY SANDBOX GROUP ID  **/
	public boolean updateBomRouteStatus(boolean routeStatus, int sandboxID) throws SQLException {
		boolean isUpdated = false;
		final String updateBomRouteStatusQuery = """
				UPDATE bomRoute
				SET bomRoute.IsActive = ?
				FROM tbl_Bom_Route bomRoute
				WHERE bomRoute.SandboxGroupID = ?
			""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(updateBomRouteStatusQuery);
			stmnt.setBoolean(1, routeStatus);
			stmnt.setInt(2, sandboxID);
			isUpdated = stmnt.executeUpdate() > 0;

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
		return isUpdated;
	}

}
