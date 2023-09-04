/**
 * 
 */
package entities;

/**
 * @author Muhammad
 *
 */
public class TblJob {

	private int bomRouteID;
	private double jobQuantity;
	private String jobNotes;
	private TblJobState jobState;
	private boolean isActive;
	
	/**
	 * @param bomRouteID
	 * @param jobQuantity
	 * @param jobNotes
	 * @param jobState
	 * @param isActive
	 */
	public TblJob(int bomRouteID, double jobQuantity, String jobNotes, TblJobState jobState, boolean isActive) {
		super();
		this.bomRouteID = bomRouteID;
		this.jobQuantity = jobQuantity;
		this.jobNotes = jobNotes;
		this.jobState = jobState;
		this.isActive = isActive;
	}
	
	public int getBomRouteID() {
		return bomRouteID;
	}

	public double getJobQuantity() {
		return jobQuantity;
	}

	public String getJobNotes() {
		return jobNotes;
	}

	public TblJobState getJobState() {
		return jobState;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setBomRouteID(int bomRouteID) {
		this.bomRouteID = bomRouteID;
	}

	public void setJobQuantity(double jobQuantity) {
		this.jobQuantity = jobQuantity;
	}

	public void setJobNotes(String jobNotes) {
		this.jobNotes = jobNotes;
	}

	public void setJobState(TblJobState jobState) {
		this.jobState = jobState;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
