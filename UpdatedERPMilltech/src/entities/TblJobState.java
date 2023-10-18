/**
 * 
 */
package entities;

import javax.swing.JCheckBox;

/**
 * @author Muhammad
 *
 */
public class TblJobState {

	private int jobStateId;
	private String jobStateName;
	private JCheckBox checkBox;

	public TblJobState() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param jobStateId
	 * @param jobStateName
	 */
	public TblJobState(int jobStateId, String jobStateName) {
		super();
		this.jobStateId = jobStateId;
		this.jobStateName = jobStateName;
		this.checkBox = new JCheckBox(jobStateName);
	}

	public int getJobStateId() {
		return jobStateId;
	}

	public String getJobStateName() {
		return jobStateName;
	}

	public void setJobStateId(int jobStateId) {
		this.jobStateId = jobStateId;
	}

	public void setJobStateName(String jobStateName) {
		this.jobStateName = jobStateName;
	}
	/** GET CHECKBOX **/
	public JCheckBox getCheckBox() {
        return checkBox;
    }
}
