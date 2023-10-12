package entities;

public class TblStockList {
	private int stock_id;
	private String stock_code;
	private String stock_group;
	private String stock_category;
	private String stock_size;
	private String stock_shape;
	private String stock_grade;
	private String stock_type;
	private String stock_description;
	private String stock_internal_structure;
	private String stock_surface_finish;

	public TblStockList() {

	}

	/**
	 * @param stock_ID
	 * @param stock_code
	 */
	public TblStockList(int stock_ID, String stock_code) {
		super();
		stock_id = stock_ID;
		this.stock_code = stock_code;
	}

	public TblStockList(String stock_code, String stock_group, String stock_category, String stock_size,
			String stock_shape, String stock_grade, String stock_type, String stock_description,
			String stock_internal_structure, String stock_surface_finish) {
		super();
		this.stock_code = stock_code;
		this.stock_group = stock_group;
		this.stock_category = stock_category;
		this.stock_size = stock_size;
		this.stock_shape = stock_shape;
		this.stock_grade = stock_grade;
		this.stock_type = stock_type;
		this.stock_description = stock_description;
		this.stock_internal_structure = stock_internal_structure;
		this.stock_surface_finish = stock_surface_finish;
	}

	public String getStock_code() {
		return stock_code;
	}

	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}

	public String getStock_group() {
		return stock_group;
	}

	public void setStock_group(String stock_group) {
		this.stock_group = stock_group;
	}

	public String getStock_category() {
		return stock_category;
	}

	public void setStock_category(String stock_category) {
		this.stock_category = stock_category;
	}

	public String getStock_size() {
		return stock_size;
	}

	public void setStock_size(String stock_size) {
		this.stock_size = stock_size;
	}

	public String getStock_shape() {
		return stock_shape;
	}

	public void setStock_shape(String stock_shape) {
		this.stock_shape = stock_shape;
	}

	public String getStock_grade() {
		return stock_grade;
	}

	public void setStock_grade(String stock_grade) {
		this.stock_grade = stock_grade;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}

	public String getStock_description() {
		return stock_description;
	}

	public void setStock_description(String stock_description) {
		this.stock_description = stock_description;
	}

	public String getStock_internal_structure() {
		return stock_internal_structure;
	}

	public void setStock_internal_structure(String stock_internal_structure) {
		this.stock_internal_structure = stock_internal_structure;
	}

	public String getStock_surface_finish() {
		return stock_surface_finish;
	}

	public void setStock_surface_finish(String stock_surface_finish) {
		this.stock_surface_finish = stock_surface_finish;
	}
	
	public int getStock_ID() {
		return stock_id;
	}

	public void setStock_ID(int stock_ID) {
		stock_id = stock_ID;
	}

	/** STRING RETURN TO DISPLAY ROUTE NAME IN COMBOBOX **/
    @Override
	public String toString() {
		return stock_code;
	}
    
    
    /** CLASS FOR STOCK SIZE SETUPS **/
    static public class StockSizeSetup{
    	
    	private int stockID;
    	private String stockSize;
		
    	/**
		 * @param stockID
		 * @param stockSize
		 */
		public StockSizeSetup(int stockID, String stockSize) {
			super();
			this.stockID = stockID;
			this.stockSize = stockSize;
		}
		
		public int getStockID() {
			return stockID;
		}
		public String getStockSize() {
			return stockSize;
		}
		public void setStockID(int stockID) {
			this.stockID = stockID;
		}
		public void setStockSize(String stockSize) {
			this.stockSize = stockSize;
		}
    	
		/** STRING RETURN TO DISPLAY ROUTE NAME IN COMBOBOX **/
	    @Override
		public String toString() {
			return stockSize;
		}
    }
    /** CLASS FOR STOCK GRADE SETUPS **/
    static public class StockGradeSetup{
    	
    	private int stockID;
    	private String stockGrade;
		
		/**
		 * @param stockID
		 * @param stockGrade
		 */
		public StockGradeSetup(int stockID, String stockGrade) {
			super();
			this.stockID = stockID;
			this.stockGrade = stockGrade;
		}

		public int getStockID() {
			return stockID;
		}

		public String getStockGrade() {
			return stockGrade;
		}

		public void setStockID(int stockID) {
			this.stockID = stockID;
		}

		public void setStockGrade(String stockGrade) {
			this.stockGrade = stockGrade;
		}

		/** STRING RETURN TO DISPLAY ROUTE NAME IN COMBOBOX **/
	    @Override
		public String toString() {
			return stockGrade;
		}
    }
}
