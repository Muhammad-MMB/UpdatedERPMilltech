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

	/** RETRIEVE LIST OF ALL CUSTOMER ORDERS BY END ITEM ID **/
	public ArrayList<TblCustomerOrder> getListOfAllCustomerOrder(int endItemID) throws SQLException {
		ArrayList<TblCustomerOrder> getListOfAllCustomerOrderArray = new ArrayList<>();
		final String getListOfAllCustomerOrderQuery = """
				SELECT ROW_NUMBER() OVER (ORDER BY custOrder.OrderID ASC) AS "SerialNo", custOrder.OrderID AS OrderID, custOrder.OrderNo AS OrderNo, 
				cust.CustomerName AS CustomerName, sl.Stock_Code AS StockCode,
				custOrder.OrderQty AS OrderQty, custOrder.OrderDate AS OrderDate, custOrder.ExpectedDlvryDte AS ExpDeliveryDate
				FROM tbl_Customer_Order custOrder
				INNER JOIN tbl_Stock_List sl ON custOrder.StockID = sl.Stock_ID
				INNER JOIN tbl_Customer cust ON custOrder.CustomerID = cust.CustomerID
				WHERE custOrder.OrderStateID = 2
				AND sl.Stock_ID = ?
				ORDER BY custOrder.ExpectedDlvryDte ASC

				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getListOfAllCustomerOrderQuery);
			stmnt.setInt(1, endItemID);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getListOfAllCustomerOrderArray.add(new TblCustomerOrder(rs.getInt("SerialNo"), rs.getInt("OrderID"), rs.getInt("OrderNo"),
							rs.getString("CustomerName"), rs.getString("StockCode"), rs.getDouble("OrderQty"),
							rs.getDate("OrderDate"), rs.getDate("ExpDeliveryDate")));
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
}
