package entities;

public class tbl_mchne_ops_sts_dtls {

	private int machineID;
	private int oldMachineOperationStatusID;
	private int newMachineOperationStatusID;
	private int userID;
	private String machineStatusUserNotes;
	private String actionDate;
	private String userWorkstationName;
	private String machineCodeName;
	private String dateOnly;
	private String timeOnly;
	private String userName;

	/**
	 * @param machineID
	 * @param oldMachineOperationStatusID
	 * @param newMachineOperationStatusID
	 * @param userID
	 * @param machineStatusUserNotes
	 * @param actionDate
	 * @param userWorkstationName
	 * @param machineCodeName
	 * @param dateOnly
	 * @param timeOnly
	 */
	public tbl_mchne_ops_sts_dtls(int machineID, int oldMachineOperationStatusID, int newMachineOperationStatusID,
			int userID, String machineStatusUserNotes, String actionDate, String userWorkstationName,
			String machineCodeName, String dateOnly, String timeOnly) {
		super();
		this.machineID = machineID;
		this.oldMachineOperationStatusID = oldMachineOperationStatusID;
		this.newMachineOperationStatusID = newMachineOperationStatusID;
		this.userID = userID;
		this.machineStatusUserNotes = machineStatusUserNotes;
		this.actionDate = actionDate;
		this.userWorkstationName = userWorkstationName;
		this.machineCodeName = machineCodeName;
		this.dateOnly = dateOnly;
		this.timeOnly = timeOnly;
	}

	/**
	 * @param oldMachineOperationStatusID
	 * @param newMachineOperationStatusID
	 * @param machineStatusUserNotes
	 * @param machineCodeName
	 * @param dateOnly
	 * @param timeOnly
	 * @param userName
	 */
	public tbl_mchne_ops_sts_dtls(String machineCodeName, int oldMachineOperationStatusID, int newMachineOperationStatusID,
			String machineStatusUserNotes,  String dateOnly, String timeOnly, String userName, String userWorkstationName) {
		super();
		this.oldMachineOperationStatusID = oldMachineOperationStatusID;
		this.newMachineOperationStatusID = newMachineOperationStatusID;
		this.machineStatusUserNotes = machineStatusUserNotes;
		this.machineCodeName = machineCodeName;
		this.dateOnly = dateOnly;
		this.timeOnly = timeOnly;
		this.userName = userName;
		this.userWorkstationName = userWorkstationName;
	}

	public int getMachineID() {
		return machineID;
	}

	public int getOldMachineOperationStatusID() {
		return oldMachineOperationStatusID;
	}

	public int getNewMachineOperationStatusID() {
		return newMachineOperationStatusID;
	}

	public int getUserID() {
		return userID;
	}

	public String getMachineStatusUserNotes() {
		return machineStatusUserNotes;
	}

	public String getActionDate() {
		return actionDate;
	}

	public String getUserWorkstationName() {
		return userWorkstationName;
	}

	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	public void setOldMachineOperationStatusID(int oldMachineOperationStatusID) {
		this.oldMachineOperationStatusID = oldMachineOperationStatusID;
	}

	public void setNewMachineOperationStatusID(int newMachineOperationStatusID) {
		this.newMachineOperationStatusID = newMachineOperationStatusID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setMachineStatusUserNotes(String machineStatusUserNotes) {
		this.machineStatusUserNotes = machineStatusUserNotes;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	public void setUserWorkstationName(String userWorkstationName) {
		this.userWorkstationName = userWorkstationName;
	}

	public String getMachineCodeName() {
		return machineCodeName;
	}

	public String getDateOnly() {
		return dateOnly;
	}

	public String getTimeOnly() {
		return timeOnly;
	}

	public void setMachineCodeName(String machineCodeName) {
		this.machineCodeName = machineCodeName;
	}

	public void setDateOnly(String dateOnly) {
		this.dateOnly = dateOnly;
	}

	public void setTimeOnly(String timeOnly) {
		this.timeOnly = timeOnly;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
