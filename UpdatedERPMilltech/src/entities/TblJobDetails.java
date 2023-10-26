/**
 * 
 */
package entities;

/**
 * 
 */
public class TblJobDetails extends TblJob {

	private int jobDetailID;
	private int orderID;
	private String customerOrderNo;
	private int stockID;
	private String stockCode;
	private double customerOrderQty;
	private int bomRouteID;
	private boolean isActive;

	public TblJobDetails(int jobID, double jobQty, boolean jobPriority,  String jobStateName,
			String jobNotes, String jobDateOnly, String jobTimeOnly, String  customerOrderNo, String stockCode, double customerOrderQty) {
		super(jobID, jobQty, jobPriority, jobStateName, jobNotes, jobDateOnly, jobTimeOnly);
		this.customerOrderNo = customerOrderNo;
		this.stockCode = stockCode;
		this.customerOrderQty = customerOrderQty;
	}

	public int getJobDetailID() {
		return jobDetailID;
	}

	public int getOrderID() {
		return orderID;
	}

	public int getStockID() {
		return stockID;
	}

	public int getBomRouteID() {
		return bomRouteID;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setJobDetailID(int jobDetailID) {
		this.jobDetailID = jobDetailID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public void setBomRouteID(int bomRouteID) {
		this.bomRouteID = bomRouteID;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCustomerOrderNo() {
		return customerOrderNo;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public double getCustomerOrderQty() {
		return customerOrderQty;
	}

	public void setCustomerOrderQty(double customerOrderQty) {
		this.customerOrderQty = customerOrderQty;
	}
	
}
