package entities;

public class tbl_bom_route_metadata {

	private String bomRouteName;
	private boolean isSubLevel;
	private boolean isAvtive;
	
	/**
	 * 
	 */
	public tbl_bom_route_metadata() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param bomRouteName
	 * @param isSubLevel
	 * @param isAvtive
	 */
	public tbl_bom_route_metadata(String bomRouteName, boolean isSubLevel, boolean isAvtive) {
		super();
		this.bomRouteName = bomRouteName;
		this.isSubLevel = isSubLevel;
		this.isAvtive = isAvtive;
	}
	
	public String getBomRouteName() {
		return bomRouteName;
	}
	
	public boolean isSubLevel() {
		return isSubLevel;
	}
	public boolean isAvtive() {
		return isAvtive;
	}
	public void setBomRouteName(String bomRouteName) {
		this.bomRouteName = bomRouteName;
	}
	public void setSubLevel(boolean isSubLevel) {
		this.isSubLevel = isSubLevel;
	}
	public void setAvtive(boolean isAvtive) {
		this.isAvtive = isAvtive;
	}
}
