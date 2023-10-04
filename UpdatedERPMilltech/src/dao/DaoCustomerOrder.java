package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblCustomerOrder;

public class DaoCustomerOrder {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE LIST OF ALL CUSTOMER ORDERS BY STOCK ID's **/
	public ArrayList<TblCustomerOrder> getAllCustomerOrderByStockID(int stockID) throws SQLException {
		ArrayList<TblCustomerOrder> getListOfAllCustomerOrderArray = new ArrayList<>();
		final String getListOfAllCustomerOrderQuery = """
				SELECT ROW_NUMBER() OVER (ORDER BY custOrder.OrderID ASC) AS "SerialNo", custOrder.OrderID AS OrderID, custOrder.OrderNo AS OrderNo, 
				cust.CustomerName AS CustomerName, sl.Stock_Code AS StockCode,
				custOrder.OrderQty AS OrderQty, custOrder.OnhandQty AS OnHandQty, custOrder.AllocatedQty AS AllocatedQty, custOrder.CustomerNotes AS CustNotes,
				custOrder.OrderDate AS OrderDate, custOrder.ExpectedDlvryDte AS ExpDeliveryDate
				FROM tbl_Customer_Order custOrder
				INNER JOIN tbl_Stock_List sl ON custOrder.StockID = sl.Stock_ID
				INNER JOIN tbl_Customer cust ON custOrder.CustomerID = cust.CustomerID
				WHERE custOrder.OrderStateID = 2
				AND sl.Stock_ID = ?

				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getListOfAllCustomerOrderQuery);
			stmnt.setInt(1, stockID);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getListOfAllCustomerOrderArray.add(new TblCustomerOrder(rs.getInt("SerialNo"), rs.getInt("OrderID"), rs.getInt("OrderNo"),
							rs.getString("CustomerName"), rs.getString("StockCode"), rs.getDouble("OrderQty"), rs.getDouble("OnHandQty"), rs.getDouble("AllocatedQty"),
							rs.getString("CustNotes"), rs.getDate("OrderDate"), rs.getDate("ExpDeliveryDate")));
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
		return getListOfAllCustomerOrderArray;
	}
	
	/** RETRIEVE LIST OF ALL CUSTOMER ORDERS BY STOCK SIZE **/
	public ArrayList<TblCustomerOrder> getAllCustomerOrderByStockSize(String sizeFrom) throws SQLException {
		ArrayList<TblCustomerOrder> getListOfAllCustomerOrderArray = new ArrayList<>();
		final String getListOfAllCustomerOrderQuery = """
				SELECT ROW_NUMBER() OVER (ORDER BY custOrder.OrderID ASC) AS "SerialNo", custOrder.OrderID AS OrderID, custOrder.OrderNo AS OrderNo, 
				cust.CustomerName AS CustomerName, sl.Stock_Code AS StockCode,
				custOrder.OrderQty AS OrderQty, custOrder.OnhandQty AS OnHandQty, custOrder.AllocatedQty AS AllocatedQty, custOrder.CustomerNotes AS CustNotes,
				custOrder.OrderDate AS OrderDate, custOrder.ExpectedDlvryDte AS ExpDeliveryDate
				FROM tbl_Customer_Order custOrder
				INNER JOIN tbl_Stock_List sl ON custOrder.StockID = sl.Stock_ID
				INNER JOIN tbl_Customer cust ON custOrder.CustomerID = cust.CustomerID
				WHERE custOrder.OrderStateID = 2
				AND sl.Stock_Size >= ?
				ORDER BY custOrder.ExpectedDlvryDte ASC

				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getListOfAllCustomerOrderQuery);
			stmnt.setString(1, sizeFrom);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getListOfAllCustomerOrderArray.add(new TblCustomerOrder(rs.getInt("SerialNo"), rs.getInt("OrderID"), rs.getInt("OrderNo"),
							rs.getString("CustomerName"), rs.getString("StockCode"), rs.getDouble("OrderQty"), rs.getDouble("OnHandQty"), rs.getDouble("AllocatedQty"),
							rs.getString("CustNotes"), rs.getDate("OrderDate"), rs.getDate("ExpDeliveryDate")));
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
		return getListOfAllCustomerOrderArray;
	}
	
	/** RETRIEVE LIST OF ALL CUSTOMER ORDERS BY STOCK SIZE & STOCK ID **/
	public ArrayList<TblCustomerOrder> getAllCustomerOrderBySizeGrade(String sizeFrom, int stockID) throws SQLException {
		ArrayList<TblCustomerOrder> getAllCustomerOrderBySizeGradeArray = new ArrayList<>();
		final String getAllCustomerOrderBySizeGradeQuery = """
				SELECT ROW_NUMBER() OVER (ORDER BY custOrder.OrderID ASC) AS "SerialNo", custOrder.OrderID AS OrderID, custOrder.OrderNo AS OrderNo, 
				cust.CustomerName AS CustomerName, sl.Stock_Code AS StockCode,
				custOrder.OrderQty AS OrderQty, custOrder.OnhandQty AS OnHandQty, custOrder.AllocatedQty AS AllocatedQty, custOrder.CustomerNotes AS CustNotes,
				custOrder.OrderDate AS OrderDate, custOrder.ExpectedDlvryDte AS ExpDeliveryDate
				FROM tbl_Customer_Order custOrder
				INNER JOIN tbl_Stock_List sl ON custOrder.StockID = sl.Stock_ID
				INNER JOIN tbl_Customer cust ON custOrder.CustomerID = cust.CustomerID
				WHERE custOrder.OrderStateID = 2
				AND sl.Stock_Size >= ?
				AND sl.Stock_ID = ?
				ORDER BY custOrder.ExpectedDlvryDte ASC

				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllCustomerOrderBySizeGradeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setInt(2, stockID);
			rs = stmnt.executeQuery();
			
			if (rs.next()) {
				do {
					getAllCustomerOrderBySizeGradeArray.add(new TblCustomerOrder(rs.getInt("SerialNo"), rs.getInt("OrderID"), rs.getInt("OrderNo"),
							rs.getString("CustomerName"), rs.getString("StockCode"), rs.getDouble("OrderQty"), rs.getDouble("OnHandQty"), rs.getDouble("AllocatedQty"),
							rs.getString("CustNotes"), rs.getDate("OrderDate"), rs.getDate("ExpDeliveryDate")));
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
		return getAllCustomerOrderBySizeGradeArray;
	}
}
