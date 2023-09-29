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
	private Date orderDate;
	private Date expDlvryDate;
	/**
	 * @param serialNo
	 * @param orderID
	 * @param orderNo
	 * @param customerName
	 * @param stockCode
	 * @param orderQty
	 * @param orderDate
	 * @param expDlvryDate
	 */
	public TblCustomerOrder(int serialNo, int orderID, int orderNo, String customerName, String stockCode,
			double orderQty, Date orderDate, Date expDlvryDate) {
		super();
		this.serialNo = serialNo;
		this.orderID = orderID;
		this.orderNo = orderNo;
		this.customerName = customerName;
		this.stockCode = stockCode;
		this.orderQty = orderQty;
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
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setExpDlvryDate(Date expDlvryDate) {
		this.expDlvryDate = expDlvryDate;
	}
}
