/**
 * 
 */
package entities;

import java.util.Date;

public class TblCustomerOrder {

	private int serialNo;
	private int orderID;
	private String orderNo;
	private String customerName;
	private int stockID;
	private String stockCode;
	private int bomRouteID;
	private double orderQty;
	private double onHandQty;
	private double allocatedQty;
	private String customerOrderNotes;
	private Date orderDate;
	private Date expDlvryDate;
	
	public TblCustomerOrder() {
		
	}
	
	/**
	 * @param serialNo
	 * @param orderID
	 * @param orderNo
	 * @param customerName
	 * @param stockID
	 * @param stockCode
	 * @param bomRouteID
	 * @param orderQty
	 * @param onHandQty
	 * @param allocatedQty
	 * @param customerNotes
	 * @param orderDate
	 * @param expDlvryDate
	 */
	public TblCustomerOrder(int serialNo, int orderID, String orderNo, String customerName, int stockID, String stockCode,
			int bomRouteID, double orderQty, double onHandQty, double allocatedQty, String customerNotes,
			Date orderDate, Date expDlvryDate) {
		super();
		this.serialNo = serialNo;
		this.orderID = orderID;
		this.orderNo = orderNo;
		this.customerName = customerName;
		this.stockID = stockID;
		this.stockCode = stockCode;
		this.bomRouteID = bomRouteID;
		this.orderQty = orderQty;
		this.onHandQty = onHandQty;
		this.allocatedQty = allocatedQty;
		this.customerOrderNotes = customerNotes;
		this.orderDate = orderDate;
		this.expDlvryDate = expDlvryDate;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public int getOrderID() {
		return orderID;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public int getStockID() {
		return stockID;
	}
	public String getStockCode() {
		return stockCode;
	}
	public int getBomRouteID() {
		return bomRouteID;
	}
	public double getOrderQty() {
		return orderQty;
	}
	public double getOnHandQty() {
		return onHandQty;
	}
	public double getAllocatedQty() {
		return allocatedQty;
	}
	public String getCustomerNotes() {
		return customerOrderNotes;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public Date getExpDlvryDate() {
		return expDlvryDate;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public void setBomRouteID(int bomRouteID) {
		this.bomRouteID = bomRouteID;
	}
	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}
	public void setOnHandQty(double onHandQty) {
		this.onHandQty = onHandQty;
	}
	public void setAllocatedQty(double allocatedQty) {
		this.allocatedQty = allocatedQty;
	}
	public void setCustomerNotes(String customerNotes) {
		this.customerOrderNotes = customerNotes;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setExpDlvryDate(Date expDlvryDate) {
		this.expDlvryDate = expDlvryDate;
	}
}
