/**
 * @author Muhammad
 *
 */
package entities;

public class TblUserRoles {

	private int roleID;
	private String roleName;
	private String roleDescription;
	private boolean roleStatus;
	private String dateTime;
	
	/**
	 * @param roleID
	 * @param roleName
	 * @param roleDescription
	 * @param roleStatus
	 * @param dateTime
	 */
	public TblUserRoles(int roleID, String roleName, String roleDescription, boolean roleStatus, String dateTime) {
		super();
		this.roleID = roleID;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roleStatus = roleStatus;
		this.dateTime = dateTime;
	}

	public int getRoleID() {
		return roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public boolean isRoleStatus() {
		return roleStatus;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public void setRoleStatus(boolean roleStatus) {
		this.roleStatus = roleStatus;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	/** STRING RETURN TO DISPLAY ROUTE NAME IN COMBOBOX **/
    @Override
	public String toString() {
		return roleName;
	}
}
