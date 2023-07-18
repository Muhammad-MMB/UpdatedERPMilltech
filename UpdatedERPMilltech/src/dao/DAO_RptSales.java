package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.tbl_item_history;
import entities.tbl_stock_list;
import extras.AppConstants;
import reports.RptSales;

public class DAO_RptSales {

	/** VARIABLES & OBJECTS DECLARATION  **/
	tbl_stock_list stockListObject;
	tbl_item_history itemHistoryObject;
	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	public DAO_RptSales() {
		stockListObject = new tbl_stock_list();
	}

	/** RETRIEVE STOCK SIZE */
	public ArrayList<String> getStockSize() throws SQLException {
		ArrayList<String> getStockSize = new ArrayList<>();
		final String getStockSizeQuery = "SELECT DISTINCT STOCK_SIZE FROM TBL_STOCK_LIST ORDER BY STOCK_SIZE ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockSizeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					stockListObject.setStock_size(rs.getString("Stock_Size"));
					getStockSize.add(stockListObject.getStock_size());
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getStockSize;
	}

	/** RETRIEVE STOCK SHAPE */
	public ArrayList<String> getStockShape() throws SQLException {
		ArrayList<String> getStockShape = new ArrayList<>();
		final String getStockShapeQuery = "SELECT DISTINCT STOCK_SHAPE FROM TBL_STOCK_LIST ORDER BY STOCK_SHAPE ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockShapeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					stockListObject.setStock_shape(rs.getString("Stock_Shape"));
					getStockShape.add(stockListObject.getStock_shape());
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getStockShape;
	}

	/** RETRIEVE STOCK GRADE */
	public ArrayList<String> getStockGrade() throws SQLException {
		ArrayList<String> getStockGrade = new ArrayList<>();
		final String getStockGradeQuery = "SELECT DISTINCT STOCK_GRADE FROM TBL_STOCK_LIST ORDER BY STOCK_GRADE ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockGradeQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					stockListObject.setStock_grade(rs.getString("Stock_Grade"));
					getStockGrade.add(stockListObject.getStock_grade());
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getStockGrade;
	}

	/** RETRIEVE STOCK INTERNAL STRUCTURE */
	public ArrayList<String> getStockIS() throws SQLException {
		ArrayList<String> getStockIS = new ArrayList<>();
		final String getStockISQuery = "SELECT DISTINCT STOCK_INTERNAL_STRUCTURE FROM TBL_STOCK_LIST ORDER BY STOCK_INTERNAL_STRUCTURE ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockISQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					stockListObject.setStock_internal_structure(rs.getString("STOCK_INTERNAL_STRUCTURE"));
					getStockIS.add(stockListObject.getStock_internal_structure());
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getStockIS;
	}

	/** RETRIEVE STOCK SURFACE FINISH */
	public ArrayList<String> getStockSF() throws SQLException {
		ArrayList<String> getStockSF = new ArrayList<>();
		final String getStockSFQuery = "SELECT DISTINCT STOCK_SURFACE_FINISH FROM TBL_STOCK_LIST ORDER BY STOCK_SURFACE_FINISH ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockSFQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					stockListObject.setStock_surface_finish(rs.getString("STOCK_SURFACE_FINISH"));
					getStockSF.add(stockListObject.getStock_surface_finish());
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getStockSF;
	}

	/** RETRIEVE ITEM HISTORY RECORDS LAST 01 YEAR */
	public ArrayList<Object> getItmHstryRcrdLastYear() throws SQLException {
		ArrayList<Object> getItmHstryRcrdLastYear = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdLastYearQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdLastYear.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdLastYear;
	}

	/** RETRIEVE ALL ITEMS HISTORY RECORDS */
	public ArrayList<Object> getAllItmHstryRcrd() throws SQLException {
		ArrayList<Object> getAllItmHstryRcrd = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getAllItmHstryRcrdQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getAllItmHstryRcrd.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getAllItmHstryRcrd;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM */
	public ArrayList<Object> getItmHstryRcrdSizeFrom(String sizeFrom) throws SQLException {
		ArrayList<Object> getItmHstryRcrdSizeFrom = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromQuery);
			stmnt.setString(1, sizeFrom);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdSizeFrom.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdSizeFrom;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE TO */
	public ArrayList<Object> getItmHstryRcrdSizeTo(String sizeTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdSizeTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeToQuery);
			stmnt.setString(1, sizeTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdSizeTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdSizeTo;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SHAPE */
	public ArrayList<Object> getItmHstryRcrdPerShape(String shape) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerShape = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdShapeQuery);
			stmnt.setString(1, shape);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerShape.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerShape;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER GRADE */
	public ArrayList<Object> getItmHstryRcrdPerGrade(String grade) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerGrade = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdGradeQuery);
			stmnt.setString(1, grade);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerGrade.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerGrade;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER INTERNAL STRUCTURE */
	public ArrayList<Object> getItmHstryRcrdPerIS(String IS) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerIS = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdISQuery);
			stmnt.setString(1, IS);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerIS.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerIS;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SURFACE FINISH */
	public ArrayList<Object> getItmHstryRcrdPerSF(String SF) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSF = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSFQuery);
			stmnt.setString(1, SF);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerSF.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSF;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER DATE FROM */
	public ArrayList<Object> getItmHstryRcrdPerDateFrom(String DateMonth, String DateYear) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerDateFrom = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdDateFromQuery);
			stmnt.setString(1, DateMonth);
			stmnt.setString(2, DateYear);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerDateFrom.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerDateFrom;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER DATE TO */
	public ArrayList<Object> getItmHstryRcrdPerDateTo(String DateMonth, String DateYear) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerDateTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdDateToQuery);
			stmnt.setString(1, DateMonth);
			stmnt.setString(2, DateYear);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerDateTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerDateTo;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromAndSizeTo(String sizeFrom, String sizeTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromAndSizeTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromAndSizeToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerSizeFromAndSizeTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromAndSizeTo;
	}
	
	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO */
	public ArrayList<Object> getItmHstryRcrdPerISAndSF(String IS, String SF) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerISAndSF = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdPerISAndSFQuery);
			stmnt.setString(1, IS);
			stmnt.setString(2, SF);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerISAndSF.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerISAndSF;
	}
	

	/** RETRIEVE ITEMS HISTORY RECORDS PER DATE FROM & DATE TO */
	public ArrayList<Object> getItmHstryRcrdPerDateFromAndDateTo(int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerDateFromAndDateTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdDateFromAndDateToQuery);
			stmnt.setInt(1, yearFrom);
			stmnt.setInt(2, monthFrom);
			stmnt.setInt(3, yearTo);
			stmnt.setInt(4, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths);
					getItmHstryRcrdPerDateFromAndDateTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerDateFromAndDateTo;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToShape(String sizeFrom, String sizeTo, String shape)
			throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToShape = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToShapeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, shape);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerSizeFromSizeToShape.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToShape;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & GRADE */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToGrade(String sizeFrom, String sizeTo, String grade)
			throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToGrade = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToShapeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, grade);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerSizeFromSizeToGrade.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToGrade;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SURFACE FINISH & DATE FROM & DATE TO */
	public ArrayList<Object> getItmHstryRcrdPerSFAndDteFromAndDteTo(String surfaceFinish, int yearFrom, int monthFrom,
			int yearTo, int monthTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSFAndDteFromAndDteTo = new ArrayList<>();
		try {
			PreparedStatement stmnt;
			ResultSet rs;
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSFDateFromAndDateToQuery);
			stmnt.setString(1, surfaceFinish);
			stmnt.setInt(2, yearFrom);
			stmnt.setInt(3, monthFrom);
			stmnt.setInt(4, yearTo);
			stmnt.setInt(5, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total") / RptSales.noOfMonths);
					getItmHstryRcrdPerSFAndDteFromAndDteTo.add(itemHistoryObject);
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
		return getItmHstryRcrdPerSFAndDteFromAndDteTo;
	}

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE & GRADE */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade(String sizeFrom, String sizeTo,
			String shape, String grade) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToShapeAndGradeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, shape);
			stmnt.setString(4, grade);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT);
					getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade;
	}
	
	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & DATE FROM & DATE TO */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndDteFromDteTo(String sizeFrom, String sizeTo,
			int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndDteFrmDteTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToAndDteFrmDteToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setInt(3, yearFrom);
			stmnt.setInt(4, monthFrom);
			stmnt.setInt(5, yearTo);
			stmnt.setInt(6, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths);
					getItmHstryRcrdPerSizeFromSizeToAndDteFrmDteTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToAndDteFrmDteTo;
	}
	
	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE & DATE FROM & DATE TO */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo(String sizeFrom, String sizeTo, String shape,
			int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToAndShapeAndDteFrmDteToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, shape);
			stmnt.setInt(4, yearFrom);
			stmnt.setInt(5, monthFrom);
			stmnt.setInt(6, yearTo);
			stmnt.setInt(7, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths);
					getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo;
	}
	
	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & GRADE & DATE FROM & DATE TO */
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo(String sizeFrom, String sizeTo, String grade,
			int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToAndGradeAndDteFrmDteToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, grade);
			stmnt.setInt(4, yearFrom);
			stmnt.setInt(5, monthFrom);
			stmnt.setInt(6, yearTo);
			stmnt.setInt(7, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths);
					getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo;
	}
	

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE & DATE FROM & DATE TO*/
	public ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo(String sizeFrom, String sizeTo,
			String shape, String grade, int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<Object> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(AppQueries.getItmHstryRcrdSizeFromSizeToShapeAndGradeAndDateFromToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, shape);
			stmnt.setString(4, grade);
			stmnt.setInt(5, yearFrom);
			stmnt.setInt(6, monthFrom);
			stmnt.setInt(7, yearTo);
			stmnt.setInt(8, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					itemHistoryObject = new tbl_item_history(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths);
					getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo.add(itemHistoryObject);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		return getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo;
	}
}
