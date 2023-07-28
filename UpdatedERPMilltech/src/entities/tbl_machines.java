package entities;


public class tbl_machines {

	private String factoryName;
	private String machineCode;
	private String machineName;
	private String machineDescription;
	private int machineStdHrsPerMonth;
	private String machineOperatingStatusName;
	private int machineOperatingStatusID;


	/**
	 * @param factoryName
	 * @param machineName
	 * @param machineDescription
	 * @param machineStdHrsPerMonth
	 * @param machineOperatingStatusName
	 * @param machineOperatingStatusIcon
	 */
	public tbl_machines(String factoryName, String  machineCode, String machineName, String machineDescription, int machineStdHrsPerMonth,
			String machineOperatingStatusName, int machineOperatingStatusID) {
		super();
		this.factoryName = factoryName;
		this.machineCode = machineCode;
		this.machineName = machineName;
		this.machineDescription = machineDescription;
		this.machineStdHrsPerMonth = machineStdHrsPerMonth;
		this.machineOperatingStatusName = machineOperatingStatusName;
		this.machineOperatingStatusID = machineOperatingStatusID;
		
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public tbl_machines() {

	}

	public String getFactoryName() {
		return factoryName;
	}

	public String getMachineName() {
		return machineName;
	}

	public String getMachineedescription() {
		return machineDescription;
	}

	public int getMachineStdHrsPerMonth() {
		return machineStdHrsPerMonth;
	}

	public String getMachineOperatingStatusName() {
		return machineOperatingStatusName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public void setMachineedescription(String machineedescription) {
		this.machineDescription = machineedescription;
	}

	public void setMachineStdHrsPerMonth(int machineStdHrsPerMonth) {
		this.machineStdHrsPerMonth = machineStdHrsPerMonth;
	}

	public void setMachineOperatingStatusName(String machineOperatingStatusName) {
		this.machineOperatingStatusName = machineOperatingStatusName;
	}

	public int getMachineOperatingStatusID() {
		return machineOperatingStatusID;
	}

	public void setMachineOperatingStatusID(int machineOperatingStatusID) {
		this.machineOperatingStatusID = machineOperatingStatusID;
	}
	
	
	
}
