/**
 * 
 */
package entities;

/**
 * 
 */
public class TblJobDetails {

	private int jobDetailID;
	private int jobID;
	private int orderID;
	private int stockID;
	private int bomRouteID;
	private boolean isActive;
	
	/**
	 * @param jobDetailID
	 * @param jobID
	 * @param orderID
	 * @param stockID
	 * @param bomRouteID
	 * @param isActive
	 */
	public TblJobDetails(int jobDetailID, int jobID, int orderID, int stockID, int bomRouteID, boolean isActive) {
		super();
		this.jobDetailID = jobDetailID;
		this.jobID = jobID;
		this.orderID = orderID;
		this.stockID = stockID;
		this.bomRouteID = bomRouteID;
		this.isActive = isActive;
	}

	public int getJobDetailID() {
		return jobDetailID;
	}

	public int getJobID() {
		return jobID;
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

	public void setJobID(int jobID) {
		this.jobID = jobID;
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
}
