/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblStockList;

/**
 * 
 */
public class DaoStockList {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE LIST OF ALL STOCK CODES NAME & ID's */
	public ArrayList<TblStockList> getListOfAllStockCode() throws SQLException {
		ArrayList<TblStockList> getListOfAllStockCodeArray = new ArrayList<>();
		final String getListOfAllStockCodeQuery = """
				SELECT SL.Stock_ID AS StockID, SL.Stock_Code AS StockCode
				FROM tbl_Stock_List SL
				ORDER BY StockID ASC
				                """;
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getListOfAllStockCodeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getListOfAllStockCodeArray.add(new TblStockList(rs.getInt("StockID"), rs.getString("StockCode")));
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
		return getListOfAllStockCodeArray;
	}
}
