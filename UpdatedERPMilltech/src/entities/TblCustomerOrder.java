/**
 * 
 */
package entities;

import java.util.Date;

public class TblCustomerOrder {

	private int serialNo;
	private int orderID;
	private int orderNo;
	private String customerName;
	private String stockCode;
	private double orderQty;
	private double onHandQty;
	private double allocatedQty;
	private String customerNotes;
	private Date orderDate;
	private Date expDlvryDate;
	/**
	 * @param serialNo
	 * @param orderID
	 * @param orderNo
	 * @param customerName
	 * @param stockCode
	 * @param orderQty
	 * @param onHandQty
	 * @param allocatedQty
	 * @param customerNotes
	 * @param orderDate
	 * @param expDlvryDate
	 */
	public TblCustomerOrder(int serialNo, int orderID, int orderNo, String customerName, String stockCode,
			double orderQty, double onHandQty, double allocatedQty, String customerNotes, Date orderDate,
			Date expDlvryDate) {
		super();
		this.serialNo = serialNo;
		this.orderID = orderID;
		this.orderNo = orderNo;
		this.customerName = customerName;
		this.stockCode = stockCode;
		this.orderQty = orderQty;
		this.onHandQty = onHandQty;
		this.allocatedQty = allocatedQty;
		this.customerNotes = customerNotes;
		this.orderDate = orderDate;
		this.expDlvryDate = expDlvryDate;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public int getOrderID() {
		return orderID;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getStockCode() {
		return stockCode;
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
		return customerNotes;
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
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
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
		this.customerNotes = customerNotes;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setExpDlvryDate(Date expDlvryDate) {
		this.expDlvryDate = expDlvryDate;
	}
}
