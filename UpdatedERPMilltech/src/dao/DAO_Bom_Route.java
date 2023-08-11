/**
 * 
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import entities.tbl_bom_route;
import entities.tbl_item_history;
import entities.tbl_machine_operation_states;

/**
 * @author Muhammad
 *
 */
public class DAO_Bom_Route {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE LIST OF ALL STOCK CODES **/
	public ArrayList<tbl_bom_route> getAllStockInfo(int queryType, String stockCode) throws SQLException {
		ArrayList<tbl_bom_route> getAllStockCodesArray = new ArrayList<>();
		final String getAllStockCodesQuery = "SELECT DISTINCT Stock_ID, Stock_Code FROM tbl_stock_list ORDER BY Stock_Code ASC";
		final String getAllStockCodesQueryByStockCode = "SELECT DISTINCT Stock_ID, Stock_Code FROM tbl_stock_list WHERE Stock_Code = ? ORDER BY Stock_Code ASC";
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
					getAllStockCodesArray.add(new tbl_bom_route(rs.getString("Stock_Code"), rs.getInt("Stock_ID")));
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
	public ArrayList<tbl_bom_route> getAllMachineInfo(int queryType, String machineName) throws SQLException {
		ArrayList<tbl_bom_route> getAllMachineInfoArray = new ArrayList<>();
		final String getAllMachineInfoQuery = "SELECT MachineID, MachineName FROM tbl_Machines ORDER BY MachineID ASC";
		final String getAllMachineInfoQueryByMachineName = "SELECT MachineID, MachineName FROM tbl_Machines WHERE MachineName = ? ORDER BY MachineID ASC";
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
					getAllMachineInfoArray.add(new tbl_bom_route(rs.getInt("MachineID"), rs.getString("MachineName")));
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
	public void setBomRoute(int endItemStockID, int inFeedStockID, int machineID, boolean isActive)
			throws SQLException {
		final String setBomRouteQuery = """
				INSERT INTO tbl_Bom_Route(
				EndItemStockID,
				InFeedItemStockID,
				MachineID,
				IsActive,
				Date)
				VALUES(?,?,?,?,GETDATE())
					""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setBomRouteQuery);
			stmnt.setInt(1, endItemStockID);
			stmnt.setInt(2, inFeedStockID);
			stmnt.setInt(3, machineID);
			stmnt.setBoolean(4, isActive);
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
	public boolean isBomRouteExist(int endItem, int feedItem, int machineID) throws SQLException {
		final String getBomRouteCheckQuery = "SELECT BOMRouteID FROM tbl_Bom_Route WHERE EndItemStockID = ? AND InFeedStockID = ? AND MachineID = ?";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getBomRouteCheckQuery);
			stmnt.setInt(1, endItem);
			stmnt.setInt(2, feedItem);
			stmnt.setInt(3, machineID);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
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
		return true;
	}

	/** RETRIEVE ALL EXISTING BOM ROUTE **/
	public ArrayList<tbl_bom_route> fetchAllBomRoutes() throws SQLException {
		ArrayList<tbl_bom_route> fetchAllBomRoutes = new ArrayList<>();
		final String fetchAllBomRoutesQuery = """
				SELECT stock1.Stock_ID AS EndItemStockID, stock1.Stock_Code AS EndItemName, stock2.Stock_ID AS InFeedStockID, stock2.Stock_Code AS InItemName,
				mac.MachineName AS MachineName, routeM.RouteName AS RouteName
				FROM tbl_Bom_Route route
				INNER JOIN tbl_Stock_List stock1 ON route.EndItemStockID = stock1.Stock_ID
				INNER JOIN tbl_Stock_List stock2 ON route.InFeedItemStockID = stock2.Stock_ID
				INNER JOIN tbl_Machines mac ON route.MachineID = mac.MachineID
				INNER JOIN tbl_Bom_Route_Metadata routeM ON routeM.BomRouteID = route.BOMRouteID
				ORDER BY route.EndItemStockID ASC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(fetchAllBomRoutesQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					fetchAllBomRoutes.add(new tbl_bom_route(rs.getInt("EndItemStockID"), rs.getString("EndItemName"), rs.getInt("InFeedStockID"), rs.getString("InItemName"),
							rs.getString("MachineName"), rs.getString("RouteName")));
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
}
