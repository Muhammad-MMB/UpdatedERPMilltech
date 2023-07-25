package dao;

public class AppQueries {

	/** TBL_ITEM_HISTORY QUERIES */

	/** GET ITEM HISTORY RECORDs PER YEAR */
	public final static String getItmHstryRcrdLastYearQuery = """
			SELECT SL.Stock_Description,
			SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			SUM(ABS(IH.IH_QTY)) As Total
			FROM tbl_item_history IH, tbl_stock_list SL
			WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			AND IH.IH_DATE BETWEEN
			DATEFROMPARTS(YEAR(GETDATE()) - 1, MONTH(GETDATE()), 1)
			AND
			DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), -1)
			GROUP BY SL.Stock_ID, SL.Stock_Description
			ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS */
	public final static String getAllItmHstryRcrdQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM */
	public final static String getItmHstryRcrdSizeFromQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			    AND SL.Stock_Size >= ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE TO */
	public final static String getItmHstryRcrdSizeToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			    AND SL.Stock_Size <= ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
			    ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SHAPE */
	public final static String getItmHstryRcrdShapeQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Shape = ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER GRADE */
	public final static String getItmHstryRcrdGradeQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Grade = ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER INTERNAL STRUCTURE */
	public final static String getItmHstryRcrdISQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			    AND SL.Stock_Internal_Structure = ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SURFACE FINISH */
	public final static String getItmHstryRcrdSFQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Surface_Finish = ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER DATE FROM */
	public final static String getItmHstryRcrdDateFromQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND MONTH(IH.IH_DATE) >= ? AND YEAR(IH.IH_DATE) >= ?
			   	GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER DATE TO */
	public final static String getItmHstryRcrdDateToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND MONTH(IH.IH_DATE) <= ? AND YEAR(IH.IH_DATE) <= ?
			   	GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO */
	public final static String getItmHstryRcrdSizeFromAndSizeToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER INTERNAL STRUCTURE AND SURFACE FINISH **/
	public final static String getItmHstryRcrdPerISAndSFQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Internal_Structure = ? AND SL.Stock_Surface_Finish = ?
			    GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER DATE FROM & DATE TO */
	public final static String getItmHstryRcrdDateFromAndDateToQuery = """
			   	SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
				AND IH.IH_DATE >= DATEFROMPARTS(?,?,1)
				AND IH.IH_DATE < DATEADD(month,1,DATEFROMPARTS(?,?,1))
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE */
	public final static String getItmHstryRcrdSizeFromSizeToShapeQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND SL.Stock_Shape = ?
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SURFACE FINISH & DATE FROM & DATE TO */
	public final static String getItmHstryRcrdSFDateFromAndDateToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
				WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
				AND SL.Stock_Surface_Finish = ?
				AND IH.IH_DATE >= DATEFROMPARTS(?,?,1)
				AND IH.IH_DATE < DATEADD(month,1,DATEFROMPARTS(?,?,1))
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & GRADE */
	public final static String getItmHstryRcrdSizeFromSizeToGradeQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND SL.Stock_Grade = ?
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/** RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE & GRADE */
	public final static String getItmHstryRcrdSizeFromSizeToShapeAndGradeQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND SL.Stock_Shape = ? AND SL.Stock_Grade = ?
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/**
	 * RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & DATE FROM & DATE TO
	 */
	public final static String getItmHstryRcrdSizeFromSizeToAndDteFrmDteToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
				AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND IH.IH_DATE >= DATEFROMPARTS(?,?,1)
				AND IH.IH_DATE < DATEADD(month,1,DATEFROMPARTS(?,?,1))
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/**
	 * RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SHAPE & SIZE TO & DATE FROM &
	 * DATE TO
	 */
	public final static String getItmHstryRcrdSizeFromSizeToAndShapeAndDteFrmDteToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
				AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND SL.Stock_Shape = ?
				AND IH.IH_DATE >= DATEFROMPARTS(?,?,1)
				AND IH.IH_DATE < DATEADD(month,1,DATEFROMPARTS(?,?,1))
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/**
	 * RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SHAPE & SIZE TO & DATE FROM &
	 * DATE TO
	 */
	public final static String getItmHstryRcrdSizeFromSizeToAndGradeAndDteFrmDteToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
				AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND SL.Stock_Grade = ?
				AND IH.IH_DATE >= DATEFROMPARTS(?,?,1)
				AND IH.IH_DATE < DATEADD(month,1,DATEFROMPARTS(?,?,1))
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/**
	 * RETRIEVE ITEMS HISTORY RECORDS PER SIZE FROM & SIZE TO & SHAPE & GRADE & DATE
	 * FROM & DATE TO
	 */
	public final static String getItmHstryRcrdSizeFromSizeToShapeAndGradeAndDateFromToQuery = """
			    SELECT SL.Stock_Description,
			    SUM(IIF(IH.IH_DATE LIKE '____-01%', ABS(IH.IH_QTY), 0)) as Jan,
			    SUM(IIF(IH.IH_DATE LIKE '____-02%', ABS(IH.IH_QTY), 0)) as Feb,
			    SUM(IIF(IH.IH_DATE LIKE '____-03%', ABS(IH.IH_QTY), 0)) as Mar,
			    SUM(IIF(IH.IH_DATE LIKE '____-04%', ABS(IH.IH_QTY), 0)) as Apr,
			    SUM(IIF(IH.IH_DATE LIKE '____-05%', ABS(IH.IH_QTY), 0)) as May,
			    SUM(IIF(IH.IH_DATE LIKE '____-06%', ABS(IH.IH_QTY), 0)) as Jun,
			    SUM(IIF(IH.IH_DATE LIKE '____-07%', ABS(IH.IH_QTY), 0)) as Jul,
			    SUM(IIF(IH.IH_DATE LIKE '____-08%', ABS(IH.IH_QTY), 0)) as Aug,
			    SUM(IIF(IH.IH_DATE LIKE '____-00%', ABS(IH.IH_QTY), 0)) as Sep,
			    SUM(IIF(IH.IH_DATE LIKE '____-10%', ABS(IH.IH_QTY), 0)) as Oct,
			    SUM(IIF(IH.IH_DATE LIKE '____-11%', ABS(IH.IH_QTY), 0)) as Nov,
			    SUM(IIF(IH.IH_DATE LIKE '____-12%', ABS(IH.IH_QTY), 0)) as Dec,
			    SUM(ABS(IH.IH_QTY)) As Total
			    FROM tbl_item_history IH, tbl_stock_list SL
			    WHERE IH.IH_TYPE = 'S' AND SL.Stock_ID = IH.Stock_ID
			   	AND SL.Stock_Size >= ? AND SL.Stock_Size <= ?
				AND SL.Stock_Shape = ? AND SL.Stock_Grade = ?
				AND IH.IH_DATE >= DATEFROMPARTS(?,?,1)
				AND IH.IH_DATE < DATEADD(month,1,DATEFROMPARTS(?,?,1))
				GROUP BY SL.Stock_ID, SL.Stock_Description
				ORDER BY SL.Stock_ID ASC
			""";

	/******* END OF TBL_ITEM_HISTORY QUERIES *******/
}
