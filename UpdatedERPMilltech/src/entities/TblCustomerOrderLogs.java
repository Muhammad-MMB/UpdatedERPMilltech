/**
 * 
 */
package entities;

import java.util.Date;

/**
 * 
 */
public class TblCustomerOrderLogs {
	private int customerOrderLogID;
	private int customerOrderID;
	private int customerOrderStateIDFrom;
	private int customerOrderStateIDTo;
	private Date customerOrderDateOccurance;
	private int userID;
	private String userSystemName;
	/**
	 * @param customerOrderLogID
	 * @param customerOrderID
	 * @param customerOrderStateIDFrom
	 * @param customerOrderStateIDTo
	 * @param customerOrderDateOccurance
	 * @param userID
	 * @param userSystemName
	 */
	public TblCustomerOrderLogs(int customerOrderLogID, int customerOrderID, int customerOrderStateIDFrom,
			int customerOrderStateIDTo, Date customerOrderDateOccurance, int userID, String userSystemName) {
		super();
		this.customerOrderLogID = customerOrderLogID;
		this.customerOrderID = customerOrderID;
		this.customerOrderStateIDFrom = customerOrderStateIDFrom;
		this.customerOrderStateIDTo = customerOrderStateIDTo;
		this.customerOrderDateOccurance = customerOrderDateOccurance;
		this.userID = userID;
		this.userSystemName = userSystemName;
	}
	public int getCustomerOrderLogID() {
		return customerOrderLogID;
	}
	public int getCustomerOrderID() {
		return customerOrderID;
	}
	public int getCustomerOrderStateIDFrom() {
		return customerOrderStateIDFrom;
	}
	public int getCustomerOrderStateIDTo() {
		return customerOrderStateIDTo;
	}
	public Date getCustomerOrderDateOccurance() {
		return customerOrderDateOccurance;
	}
	public int getUserID() {
		return userID;
	}
	public String getUserSystemName() {
		return userSystemName;
	}
	public void setCustomerOrderLogID(int customerOrderLogID) {
		this.customerOrderLogID = customerOrderLogID;
	}
	public void setCustomerOrderID(int customerOrderID) {
		this.customerOrderID = customerOrderID;
	}
	public void setCustomerOrderStateIDFrom(int customerOrderStateIDFrom) {
		this.customerOrderStateIDFrom = customerOrderStateIDFrom;
	}
	public void setCustomerOrderStateIDTo(int customerOrderStateIDTo) {
		this.customerOrderStateIDTo = customerOrderStateIDTo;
	}
	public void setCustomerOrderDateOccurance(Date customerOrderDateOccurance) {
		this.customerOrderDateOccurance = customerOrderDateOccurance;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setUserSystemName(String userSystemName) {
		this.userSystemName = userSystemName;
	}
}
