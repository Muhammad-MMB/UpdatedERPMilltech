package entities;

public class tbl_machine_operation_states {

	private int machineOperationStateID;
	private String machineOperationStateName;
	private String machineOperationStateDescription;
	/**
	 * @param machineOperationStateID
	 * @param machineOperationStateName
	 * @param machineOperationStateDescription
	 */
	public tbl_machine_operation_states(int machineOperationStateID, String machineOperationStateName,
			String machineOperationStateDescription) {
		super();
		this.machineOperationStateID = machineOperationStateID;
		this.machineOperationStateName = machineOperationStateName;
		this.machineOperationStateDescription = machineOperationStateDescription;
	}
	
	public int getMachineOperationStateID() {
		return machineOperationStateID;
	}
	public String getMachineOperationStateName() {
		return machineOperationStateName;
	}
	public String getMachineOperationStateDescription() {
		return machineOperationStateDescription;
	}
	public void setMachineOperationStateID(int machineOperationStateID) {
		this.machineOperationStateID = machineOperationStateID;
	}
	public void setMachineOperationStateName(String machineOperationStateName) {
		this.machineOperationStateName = machineOperationStateName;
	}
	public void setMachineOperationStateDescription(String machineOperationStateDescription) {
		this.machineOperationStateDescription = machineOperationStateDescription;
	}
}
