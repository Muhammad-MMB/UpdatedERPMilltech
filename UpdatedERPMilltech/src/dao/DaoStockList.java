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
import entities.TblStockList.StockGradeSetup;
import entities.TblStockList.StockSizeSetup;

/**
 * 
 */
public class DaoStockList {

	private Connection con = null;
	private PreparedStatement stmnt = null;
	private ResultSet rs = null;

	/** RETRIEVE LIST OF ALL STOCK CODES NAME & ID's **/
	public ArrayList<TblStockList> getAllStockCode() throws SQLException {
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

	/** RETRIEVE ALL STOCK SIZE **/
	public ArrayList<StockSizeSetup> getAllStockSize() throws SQLException {
		ArrayList<StockSizeSetup> getStockSizeArray = new ArrayList<>();
		final String getStockSizeQuery = """
				WITH CTE AS (
				  SELECT
				    Stock_Size,
				    Stock_ID,
				    ROW_NUMBER() OVER (PARTITION BY Stock_Size ORDER BY (SELECT 0)) AS rn
				  FROM
				    tbl_Stock_List
				)
				SELECT Stock_Size, Stock_ID
				FROM CTE
				WHERE rn = 1
				ORDER BY Stock_Size ASC
								""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockSizeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getStockSizeArray.add(new StockSizeSetup(rs.getInt("Stock_ID"), rs.getString("Stock_Size")));
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
		return getStockSizeArray;
	}

	/** RETRIEVE ALL STOCK GRADE **/
	public ArrayList<StockGradeSetup> getAllStockGrade() throws SQLException {
		ArrayList<StockGradeSetup> getAllStockGradeArray = new ArrayList<>();
		final String getStockGradeQuery = """
									WITH CTE AS (
				  SELECT
				    Stock_Grade,
				    Stock_ID,
				    ROW_NUMBER() OVER (PARTITION BY Stock_Grade ORDER BY (SELECT 0)) AS rn
				  FROM
				    tbl_Stock_List
				)
				SELECT Stock_Grade, Stock_ID
				FROM CTE
				WHERE rn = 1
				ORDER BY Stock_Grade ASC
												""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockGradeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllStockGradeArray.add(new StockGradeSetup(rs.getInt("Stock_ID"), rs.getString("Stock_Grade")));
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
		return getAllStockGradeArray;
	}
}
