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
                "SELECT DISTINCT Stock_ID, Stock_Code FROM tbl_stock_list ORDER BY Stock_Code ASC";
        final String getAllStockCodesQueryByStockCode =
                "SELECT DISTINCT Stock_ID, Stock_Code FROM tbl_stock_list WHERE Stock_Code = ? ORDER BY Stock_Code ASC";
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
            boolean isActive) throws SQLException {
        final String setBomRouteQuery = """
                INSERT INTO tbl_Bom_Route(
                EndItemStockID,
                InFeedItemStockID,
                MachineID,
                RouteName,
                SandboxGroupID,
                IsActive,
                Date)
                VALUES(?, ?, ?, ?, ?, ?, GETDATE())
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
        final String getBomRouteCheckQuery =
                "SELECT BOMRouteID FROM tbl_Bom_Route WHERE EndItemStockID = ? AND InFeedStockID = ? AND MachineID = ?";
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
    public ArrayList<TblBomRoute> fetchAllBomRoutes() throws SQLException {
        ArrayList<TblBomRoute> fetchAllBomRoutes = new ArrayList<>();
        final String fetchAllBomRoutesQuery =
                """
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
                    fetchAllBomRoutes.add(new TblBomRoute(rs.getInt("EndItemStockID"), rs.getString("EndItemName"), rs
                            .getInt("InFeedStockID"), rs.getString("InItemName"), rs.getString("MachineName"), rs
                                    .getString("RouteName")));
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
    public ArrayList<TblBomRoute> getAllListOfRoutes() throws SQLException {
        ArrayList<TblBomRoute> getAllListOfRoutesArray = new ArrayList<>();
        final String getAllListOfRoutesQuery =
                """
                        SELECT BOMRouteID AS RouteID, RouteName AS RouteName, SandboxGroupID AS RouteGroupID, EndItemStockID AS EndItemID
                        FROM tbl_Bom_Route
                        WHERE RouteName IS NOT NULL AND TRIM(RouteName) <> ''
                        ORDER BY EndItemID ASC

                        """;
        try {
            con = DataSource.getConnection();
            stmnt = con.prepareStatement(getAllListOfRoutesQuery);
            rs = stmnt.executeQuery();
            if (rs.next()) {
                do {
                    getAllListOfRoutesArray.add(new TblBomRoute(rs.getInt("RouteID"), rs.getString("RouteName"), rs
                            .getInt("RouteGroupID")));
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
        System.out.println(getAllListOfRoutesArray);
        return getAllListOfRoutesArray;
    }
}