package entities;


public class TblMachines {

	private int machineID;
	private String factoryName;
	private String machineCodeName;
	private String machineName;
	private String machineDescription;
	private int machineStdHrsPerMonth;
	private String machineOperatingStatusName;
	private int machineOperatingStatusID;

	/**
     * @param machineID
     * @param machineName
     * @param machineOperatingStatusID
     */
    public TblMachines(int machineID, String machineName, int machineOperatingStatusID) {
        super();
        this.machineID = machineID;
        this.machineName = machineName;
        this.machineOperatingStatusID = machineOperatingStatusID;
    }

    /**
	 * @param machineID
	 * @param factoryName
	 * @param machineCodeName
	 * @param machineName
	 * @param machineDescription
	 * @param machineStdHrsPerMonth
	 * @param machineOperatingStatusName
	 * @param machineOperatingStatusID
	 */
	public TblMachines(int machineID, String factoryName, String machineCodeName, String machineName,
			String machineDescription, int machineStdHrsPerMonth, String machineOperatingStatusName,
			int machineOperatingStatusID) {
		super();
		this.machineID = machineID;
		this.factoryName = factoryName;
		this.machineCodeName = machineCodeName;
		this.machineName = machineName;
		this.machineDescription = machineDescription;
		this.machineStdHrsPerMonth = machineStdHrsPerMonth;
		this.machineOperatingStatusName = machineOperatingStatusName;
		this.machineOperatingStatusID = machineOperatingStatusID;
	}
	
	public int getMachineID() {
		return machineID;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public String getMachineCodeName() {
		return machineCodeName;
	}
	public String getMachineName() {
		return machineName;
	}
	public String getMachineDescription() {
		return machineDescription;
	}
	public int getMachineStdHrsPerMonth() {
		return machineStdHrsPerMonth;
	}
	public String getMachineOperatingStatusName() {
		return machineOperatingStatusName;
	}
	public int getMachineOperatingStatusID() {
		return machineOperatingStatusID;
	}
	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public void setMachineCodeName(String machineCodeName) {
		this.machineCodeName = machineCodeName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public void setMachineDescription(String machineDescription) {
		this.machineDescription = machineDescription;
	}
	public void setMachineStdHrsPerMonth(int machineStdHrsPerMonth) {
		this.machineStdHrsPerMonth = machineStdHrsPerMonth;
	}
	public void setMachineOperatingStatusName(String machineOperatingStatusName) {
		this.machineOperatingStatusName = machineOperatingStatusName;
	}
	public void setMachineOperatingStatusID(int machineOperatingStatusID) {
		this.machineOperatingStatusID = machineOperatingStatusID;
	}
	
	
	

}
