package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.TblItemHistory;
import entities.TblStockList;
import extras.AppConstants;
import gui.RptSales;

public class DaoReportSales {

	/** VARIABLES & OBJECTS DECLARATION  **/
	TblStockList stockListObject;
	TblItemHistory itemHistoryObject;
	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	public DaoReportSales() {
		stockListObject = new TblStockList();
	}

	/** RETRIEVE STOCK SIZE */
	public ArrayList<String> getStockSize() throws SQLException {
		ArrayList<String> getStockSize = new ArrayList<>();
		final String getStockSizeQuery = "SELECT DISTINCT Stock_Size FROM tbl_stock_list ORDER BY Stock_Size ASC";
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
		final String getStockShapeQuery = "SELECT DISTINCT Stock_Shape FROM tbl_stock_list ORDER BY Stock_Shape ASC";
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
		final String getStockGradeQuery = "SELECT DISTINCT Stock_Grade FROM tbl_stock_list ORDER BY Stock_Grade ASC";
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
		final String getStockISQuery = "SELECT DISTINCT Stock_Internal_Structure FROM tbl_stock_list ORDER BY Stock_Internal_Structure ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockISQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					stockListObject.setStock_internal_structure(rs.getString("Stock_Internal_Structure"));
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
		final String getStockSFQuery = "SELECT DISTINCT Stock_Surface_Finish FROM tbl_stock_list ORDER BY Stock_Surface_Finish ASC";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getStockSFQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					stockListObject.setStock_surface_finish(rs.getString("Stock_Surface_Finish"));
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
	public ArrayList<TblItemHistory> getItmHstryRcrdLastYear() throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdLastYear = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdLastYearQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdLastYear.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getAllItmHstryRcrd() throws SQLException {
		ArrayList<TblItemHistory> getAllItmHstryRcrd = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getAllItmHstryRcrdQuery);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getAllItmHstryRcrd.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdSizeFrom(String sizeFrom) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdSizeFrom = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromQuery);
			stmnt.setString(1, sizeFrom);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getItmHstryRcrdSizeFrom.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdSizeTo(String sizeTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdSizeTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeToQuery);
			stmnt.setString(1, sizeTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdSizeTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerShape(String shape) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerShape = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdShapeQuery);
			stmnt.setString(1, shape);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerShape.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerGrade(String grade) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerGrade = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdGradeQuery);
			stmnt.setString(1, grade);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerGrade.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerIS(String IS) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerIS = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdISQuery);
			stmnt.setString(1, IS);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerIS.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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

	/** RETRIEVE ITEMS HISTORY RECORDS PER SURFACE FINISH **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSF(String SF) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSF = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSFQuery);
			stmnt.setString(1, SF);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSF.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
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

	/**  RETRIEVE ITEMS HISTORY RECORDS PER DATE FROM  **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerDateFrom(String DateMonth, String DateYear) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerDateFrom = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdDateFromQuery);
			stmnt.setString(1, DateMonth);
			stmnt.setString(2, DateYear);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerDateFrom.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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

	/**  RETRIEVE ITEMS HISTORY RECORDS PER DATE TO  **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerDateTo(String DateMonth, String DateYear) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerDateTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdDateToQuery);
			stmnt.setString(1, DateMonth);
			stmnt.setString(2, DateYear);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerDateTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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

	/**  RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO  **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromAndSizeTo(String sizeFrom, String sizeTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromAndSizeTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromAndSizeToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSizeFromAndSizeTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	
	/**  RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO  **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerISAndSF(String IS, String SF) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerISAndSF = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdPerISAndSFQuery);
			stmnt.setString(1, IS);
			stmnt.setString(2, SF);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerISAndSF.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	

	/**  RETRIEVE ITEMS HISTORY RECORDS PER DATE FROM & DATE TO  **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerDateFromAndDateTo(int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerDateFromAndDateTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdDateFromAndDateToQuery);
			stmnt.setInt(1, yearFrom);
			stmnt.setInt(2, monthFrom);
			stmnt.setInt(3, yearTo);
			stmnt.setInt(4, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerDateFromAndDateTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths));
					
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

	/**  RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE **/
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToShape(String sizeFrom, String sizeTo, String shape)
			throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToShape = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToShapeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, shape);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSizeFromSizeToShape.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToGrade(String sizeFrom, String sizeTo, String grade)
			throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToGrade = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToGradeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, grade);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSizeFromSizeToGrade.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSFAndDteFromAndDteTo(String surfaceFinish, int yearFrom, int monthFrom,
			int yearTo, int monthTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSFAndDteFromAndDteTo = new ArrayList<>();
		try {
			PreparedStatement stmnt;
			ResultSet rs;
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSFDateFromAndDateToQuery);
			stmnt.setString(1, surfaceFinish);
			stmnt.setInt(2, yearFrom);
			stmnt.setInt(3, monthFrom);
			stmnt.setInt(4, yearTo);
			stmnt.setInt(5, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSFAndDteFromAndDteTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total") / RptSales.noOfMonths));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade(String sizeFrom, String sizeTo,
			String shape, String grade) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToShapeAndGradeQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setString(3, shape);
			stmnt.setString(4, grade);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSizeFromSizeToAndShapeAndGrade.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/AppConstants.NO_OF_MONTHS_FOR_SALE_RPT));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndDteFromDteTo(String sizeFrom, String sizeTo,
			int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndDteFrmDteTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToAndDteFrmDteToQuery);
			stmnt.setString(1, sizeFrom);
			stmnt.setString(2, sizeTo);
			stmnt.setInt(3, yearFrom);
			stmnt.setInt(4, monthFrom);
			stmnt.setInt(5, yearTo);
			stmnt.setInt(6, monthTo);
			rs = stmnt.executeQuery();
			if (rs.next()){
				do {
					getItmHstryRcrdPerSizeFromSizeToAndDteFrmDteTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo(String sizeFrom, String sizeTo, String shape,
			int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToAndShapeAndDteFrmDteToQuery);
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
					getItmHstryRcrdPerSizeFromSizeToAndShapeAndDteFromDteTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo(String sizeFrom, String sizeTo, String grade,
			int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToAndGradeAndDteFrmDteToQuery);
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
					getItmHstryRcrdPerSizeFromSizeToAndGradeAndDteFromDteTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths));
					
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
	public ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo(String sizeFrom, String sizeTo,
			String shape, String grade, int yearFrom,int monthFrom, int yearTo, int monthTo) throws SQLException {
		ArrayList<TblItemHistory> getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo = new ArrayList<>();
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(SaleReportQueries.getItmHstryRcrdSizeFromSizeToShapeAndGradeAndDateFromToQuery);
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
					getItmHstryRcrdPerSizeFromSizeToAndShapeAndGradeAndDateFromTo.add(new TblItemHistory(rs.getString("Stock_Description"), rs.getDouble("Jan"),
							rs.getDouble("Feb"), rs.getDouble("Mar"), rs.getDouble("Apr"), rs.getDouble("May"),
							rs.getDouble("Jun"), rs.getDouble("Jul"), rs.getDouble("Aug"), rs.getDouble("Sep"),
							rs.getDouble("Oct"), rs.getDouble("Nov"), rs.getDouble("Dec"), rs.getDouble("Total"),
							rs.getDouble("Total")/RptSales.noOfMonths));
					
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
