package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblStockList;

public class DaoHistorySales {

	TblStockList stockListObject;
	PreparedStatement stmnt;
	ResultSet rs;

	public ArrayList<TblStockList> getStockListByType(String stockType, int reportType) throws SQLException {
		ArrayList<TblStockList> stockAllDataBYType = new ArrayList<>();
		final String getStockListByTypeQuery = "SELECT * from tbl_stock_list where stock_Type = ? order by stock_type asc";
		final String getAllStockListQuery = "SELECT * from tbl_stock_list order by stock_type asc";

		try {
			Connection con = DataSource.getConnection();
			if(reportType == 1) {
				stmnt = con.prepareStatement(getStockListByTypeQuery);
				stmnt.setString(1, stockType);
				rs = stmnt.executeQuery();
			}
			else {
				stmnt = con.prepareStatement(getAllStockListQuery);
				rs = stmnt.executeQuery();
			}
			if (rs.next() == false) {
				System.out.println("No value found");
			} else {
				do {
					stockListObject = new TblStockList(rs.getString("Stock_Code"), rs.getString("Stock_Group"),
							rs.getString("Stock_Category"), rs.getString("Stock_Size"), rs.getString("Stock_Shape"),
							rs.getString("Stock_Grade"), rs.getString("Stock_Type"), rs.getString("Stock_Description"), "", "");
					stockAllDataBYType.add(stockListObject);
				} while (rs.next());
				stmnt.close();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockAllDataBYType;
	}

	public ArrayList<String> getAllListOfStockTypes() throws SQLException {
		ArrayList<String> getAllListOfStockTypes = new ArrayList<>();
		final String getAllListOfStockTypesQuery = "SELECT stock_type from tbl_stock_list order by stock_type asc";
		try {
			stockListObject = new TblStockList();  
			Connection con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllListOfStockTypesQuery);
			rs = stmnt.executeQuery();
			if (rs.next() == false) {
				System.out.println("No value found");
			} else {
				do {
					stockListObject.setStock_type(rs.getString("Stock_Type"));
					getAllListOfStockTypes.add(stockListObject.getStock_type());
				} while (rs.next());
				stmnt.close();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getAllListOfStockTypes;
	}

}
