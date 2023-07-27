package entities;

import javax.swing.ImageIcon;

public class tbl_machines {

	private String factoryName;
	private String machineName;
	private String machineedescription;
	private int machineStdHrsPerMonth;
	private String machineOperatingStatusName;
	private int machineOperatingStatusID;


	/**
	 * @param factoryName
	 * @param machineName
	 * @param machineedescription
	 * @param machineStdHrsPerMonth
	 * @param machineOperatingStatusName
	 * @param machineOperatingStatusIcon
	 */
	public tbl_machines(String factoryName, String machineName, String machineedescription, int machineStdHrsPerMonth,
			String machineOperatingStatusName, int machineOperatingStatusID) {
		super();
		this.factoryName = factoryName;
		this.machineName = machineName;
		this.machineedescription = machineedescription;
		this.machineStdHrsPerMonth = machineStdHrsPerMonth;
		this.machineOperatingStatusName = machineOperatingStatusName;
		this.machineOperatingStatusID = machineOperatingStatusID;
		
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
		return machineedescription;
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
		this.machineedescription = machineedescription;
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
