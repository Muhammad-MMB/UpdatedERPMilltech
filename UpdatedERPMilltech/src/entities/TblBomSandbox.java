package entities;

public class TblBomSandbox {

	private int sandboxGroupID;
	private int endItemID;
	private String endItemName;
	private int inFeedItemID;
	private String inFeedItemName;
	private int machineID;
	private String machineName;
	private boolean morChildExists;
	private int parentItemID;
	private String routeName;
	
	/**
	 * @param endItemID
	 * @param endItemName
	 */
	public TblBomSandbox(int inFeedItemID, String inFeedItemName) {
		super();
		this.inFeedItemID = inFeedItemID;
		this.inFeedItemName = inFeedItemName;
	}

	/**
	 * @param sandboxGroupID
	 * @param endItemID
	 * @param endItemName
	 * @param inFeedItemID
	 * @param inFeedItemName
	 * @param machineID
	 * @param machineName
	 * @param morChildExists
	 * @param parentItemID
	 * @param routeName
	 */
	public TblBomSandbox(int sandboxGroupID, int endItemID, String endItemName, int inFeedItemID,
			String inFeedItemName, int machineID, String machineName, boolean morChildExists, int parentItemID,
			String routeName) {
		super();
		this.sandboxGroupID = sandboxGroupID;
		this.endItemID = endItemID;
		this.endItemName = endItemName;
		this.inFeedItemID = inFeedItemID;
		this.inFeedItemName = inFeedItemName;
		this.machineID = machineID;
		this.machineName = machineName;
		this.morChildExists = morChildExists;
		this.parentItemID = parentItemID;
		this.routeName = routeName;
	}
	public int getSandboxGroupID() {
		return sandboxGroupID;
	}
	public int getEndItemID() {
		return endItemID;
	}
	public String getEndItemName() {
		return endItemName;
	}
	public int getInFeedItemID() {
		return inFeedItemID;
	}
	public String getInFeedItemName() {
		return inFeedItemName;
	}
	public int getMachineID() {
		return machineID;
	}
	public String getMachineName() {
		return machineName;
	}
	public boolean isMorChildExists() {
		return morChildExists;
	}
	public int getParentItemID() {
		return parentItemID;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setSandboxGroupID(int sandboxGroupID) {
		this.sandboxGroupID = sandboxGroupID;
	}
	public void setEndItemID(int endItemID) {
		this.endItemID = endItemID;
	}
	public void setEndItemName(String endItemName) {
		this.endItemName = endItemName;
	}
	public void setInFeedItemID(int inFeedItemID) {
		this.inFeedItemID = inFeedItemID;
	}
	public void setInFeedItemName(String inFeedItemName) {
		this.inFeedItemName = inFeedItemName;
	}
	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public void setMorChildExists(boolean morChildExists) {
		this.morChildExists = morChildExists;
	}
	public void setParentItemID(int parentItemID) {
		this.parentItemID = parentItemID;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
}
