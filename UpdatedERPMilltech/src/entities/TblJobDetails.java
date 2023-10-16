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
	private int jobStateID;
	private double jobQty;
	private boolean isActive;
	private boolean jobPriority;
	
	
	/**
	 * @param jobDetailID
	 * @param jobID
	 * @param orderID
	 * @param stockID
	 * @param bomRouteID
	 * @param jobStateID
	 * @param jobQty
	 * @param isActive
	 * @param jobPriority
	 */
	public TblJobDetails(int jobDetailID, int jobID, int orderID, int stockID, int bomRouteID, int jobStateID,
			double jobQty, boolean isActive, boolean jobPriority) {
		super();
		this.jobDetailID = jobDetailID;
		this.jobID = jobID;
		this.orderID = orderID;
		this.stockID = stockID;
		this.bomRouteID = bomRouteID;
		this.jobStateID = jobStateID;
		this.jobQty = jobQty;
		this.isActive = isActive;
		this.jobPriority = jobPriority;
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


	public int getJobStateID() {
		return jobStateID;
	}


	public double getJobQty() {
		return jobQty;
	}


	public boolean isActive() {
		return isActive;
	}


	public boolean isJobPriority() {
		return jobPriority;
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


	public void setJobStateID(int jobStateID) {
		this.jobStateID = jobStateID;
	}


	public void setJobQty(double jobQty) {
		this.jobQty = jobQty;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public void setJobPriority(boolean jobPriority) {
		this.jobPriority = jobPriority;
	}
	
	
	
}
