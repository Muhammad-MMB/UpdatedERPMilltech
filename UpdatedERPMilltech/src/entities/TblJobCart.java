/**
 * 
 */
package entities;

/**
 * 
 */
public class TblJobCart {

	private int jobCartID;
	private int orderID;
	private int stockID;
	private int bomRouteID;
	private boolean isActive;
	private String dateTime;
	private String orderNo;
	private double orderQty;
	private String customerOrderNotes;
	private int customerOrderStateID;
	
	/**
	 * @param orderNo
	 * @param orderQty
	 */
	public TblJobCart(int orderID, String orderNo, String customerOrderNotes, double orderQty, int stockID, int bomRouteID, boolean isActive, int customerOrderStateID) {
		super();
		this.orderID = orderID;
		this.orderNo = orderNo;
		this.customerOrderNotes = customerOrderNotes;
		this.orderQty = orderQty;
		this.stockID = stockID;
		this.bomRouteID = bomRouteID;
		this.isActive = isActive;
		this.customerOrderStateID = customerOrderStateID;
	}
	public int getJobCartID() {
		return jobCartID;
	}
	public void setJobCartID(int jobCartID) {
		this.jobCartID = jobCartID;
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
	public String getDateTime() {
		return dateTime;
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
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public double getOrderQty() {
		return orderQty;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}
	public String getCustomerOrderNotes() {
		return customerOrderNotes;
	}
	public void setCustomerOrderNotes(String customerOrderNotes) {
		this.customerOrderNotes = customerOrderNotes;
	}
	public int getCustomerOrderStateID() {
		return customerOrderStateID;
	}
	public void setCustomerOrderStateID(int customerOrderStateID) {
		this.customerOrderStateID = customerOrderStateID;
	}
}
