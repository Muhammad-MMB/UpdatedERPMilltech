/**
 * 
 */
package entities;

/**
 * @author Muhammad
 *
 */
public class TblJob {

	private int jobID;
	private boolean isActive;
	
	/**
	 * @param jobID
	 * @param isActive
	 */
	public TblJob(int jobID, boolean isActive) {
		super();
		this.jobID = jobID;
		this.isActive = isActive;
	}

	public int getJobID() {
		return jobID;
	}

	public boolean isActive() {
		return isActive;
	}


	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/** CLASS FOR JOB CREATED RECORDS **/
	static public class JobCreated {
		
		private int serialNo;
		private int jobID;
		private int bomRouteID;
		private String endItemName;
		private String inFeedItemName;
		private String machineName;
		private double jobQuantity;
		private String jobNotes;
		private String jobStateName;
		private boolean jobPriority;
		private String dateOnly;
		private String timeOnly;
		/**
		 * @param serialNo
		 * @param jobID
		 * @param bomRouteID
		 * @param endItemName
		 * @param inFeedItemName
		 * @param machineName
		 * @param jobQuantity
		 * @param jobNotes
		 * @param jobStateName
		 * @param jobPriority
		 * @param dateOnly
		 * @param timeOnly
		 */
		public JobCreated(int serialNo, int jobID, int bomRouteID, String endItemName, String inFeedItemName,
				String machineName, double jobQuantity, String jobNotes, String jobStateName, boolean jobPriority,
				String dateOnly, String timeOnly) {
			this.serialNo = serialNo;
			this.jobID = jobID;
			this.bomRouteID = bomRouteID;
			this.endItemName = endItemName;
			this.inFeedItemName = inFeedItemName;
			this.machineName = machineName;
			this.jobQuantity = jobQuantity;
			this.jobNotes = jobNotes;
			this.jobStateName = jobStateName;
			this.jobPriority = jobPriority;
			this.dateOnly = dateOnly;
			this.timeOnly = timeOnly;
		}
		
		/**
		 * @param serialNo
		 * @param bomRouteID
		 * @param endItemName
		 * @param inFeedItemName
		 * @param machineName
		 * @param jobQuantity
		 */
		public JobCreated(int serialNo, int bomRouteID, String endItemName, String inFeedItemName, String machineName,
				double jobQuantity) {
			super();
			this.serialNo = serialNo;
			this.bomRouteID = bomRouteID;
			this.endItemName = endItemName;
			this.inFeedItemName = inFeedItemName;
			this.machineName = machineName;
			this.jobQuantity = jobQuantity;
		}

		public int getSerialNo() {
			return serialNo;
		}
		public int getJobID() {
			return jobID;
		}
		public int getBomRouteID() {
			return bomRouteID;
		}
		public String getEndItemName() {
			return endItemName;
		}
		public String getInFeedItemName() {
			return inFeedItemName;
		}
		public String getMachineName() {
			return machineName;
		}
		public double getJobQuantity() {
			return jobQuantity;
		}
		public String getJobNotes() {
			return jobNotes;
		}
		public String getJobStateName() {
			return jobStateName;
		}
		public boolean isJobPriority() {
			return jobPriority;
		}
		public String getDateOnly() {
			return dateOnly;
		}
		public String getTimeOnly() {
			return timeOnly;
		}
		public void setSerialNo(int serialNo) {
			this.serialNo = serialNo;
		}
		public void setJobID(int jobID) {
			this.jobID = jobID;
		}
		public void setBomRouteID(int bomRouteID) {
			this.bomRouteID = bomRouteID;
		}
		public void setEndItemName(String endItemName) {
			this.endItemName = endItemName;
		}
		public void setInFeedItemName(String inFeedItemName) {
			this.inFeedItemName = inFeedItemName;
		}
		public void setMachineName(String machineName) {
			this.machineName = machineName;
		}
		public void setJobQuantity(double jobQuantity) {
			this.jobQuantity = jobQuantity;
		}
		public void setJobNotes(String jobNotes) {
			this.jobNotes = jobNotes;
		}
		public void setJobStateName(String jobStateName) {
			this.jobStateName = jobStateName;
		}
		public void setJobPriority(boolean jobPriority) {
			this.jobPriority = jobPriority;
		}
		public void setDateOnly(String dateOnly) {
			this.dateOnly = dateOnly;
		}
		public void setTimeOnly(String timeOnly) {
			this.timeOnly = timeOnly;
		}
	}
}
