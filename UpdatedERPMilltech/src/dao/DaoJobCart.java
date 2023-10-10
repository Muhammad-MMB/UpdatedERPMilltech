/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.TblJobCart;

/**
 * 
 */
public class DaoJobCart {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** CREATE NEW JOB CART **/
	public boolean createNewJobCart(int orderID, int stockID, int bomRouteID, boolean isActive) throws SQLException {
		boolean isInserted = false;
		final String createNewJobCartQuery = """
				INSERT INTO [dbo].[tbl_Job_Cart]
				       ([OrderID]
				       ,[StockID]
				       ,[BOMRouteID]
				       ,[IsActive]
				       ,[DateCreated])
				 VALUES
				       (?, ?, ?, ?, GETDATE())
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(createNewJobCartQuery);
			stmnt.setInt(1, orderID);
			stmnt.setInt(2, stockID);
			stmnt.setInt(3, bomRouteID);
			stmnt.setBoolean(4, isActive);
			isInserted = stmnt.executeUpdate() > 0;
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
		return isInserted;
	}

	/** RETRIEVE LIST OF ALL JOB CART RECORDS **/
	public List<TblJobCart> getJobCartRecordsForDisplay() throws SQLException {
		ArrayList<TblJobCart> getJobCartRecordsForDisplayArray = new ArrayList<>();
		final String getAllJobCartRecordsQuery = """
					SELECT JobCart.JobCartID AS CartID, JobCart.OrderID AS OrderID, CustOrder.OrderNo AS OrderNo, CustOrder.OrderQty AS OrderQty, CustOrder.CustomerOrderNotes AS CustomerOrderNotes
					FROM tbl_Job_Cart AS JobCart
					INNER JOIN tbl_Customer_Order CustOrder ON CustOrder.OrderID = JobCart.OrderID
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllJobCartRecordsQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getJobCartRecordsForDisplayArray.add(
							new TblJobCart(rs.getInt("OrderID"), rs.getString("OrderNo"), rs.getString("CustomerOrderNotes"), rs.getDouble("OrderQty")));
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
		return getJobCartRecordsForDisplayArray;
	}

	/** REMOVE ALL JOB CART ITEMS **/
	public void removeAllJobCartItems() throws SQLException {
		final String removeAllCartItemsQuery = """
				DELETE FROM [dbo].[tbl_Job_Cart]
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(removeAllCartItemsQuery);
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

	/** REMOVE CART ITEMS BY ORDER ID **/
	public void removeCartItemsByOrderID(int orderID) throws SQLException {
		final String removeCartItemsByOrderIDQuery = """
				DELETE FROM [dbo].[tbl_Job_Cart]
				WHERE tbl_Job_Cart.OrderID = ?
				      	""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(removeCartItemsByOrderIDQuery);
			stmnt.setInt(1, orderID);
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
