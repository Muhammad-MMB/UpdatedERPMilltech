/**
 * 
 */
package entities;

/**
 * @author Muhammad
 *
 */
public class TblBomRoute {

	private int routeID;
	private int stockID;
	private String stockCode;
	private int inFeedStockID;
	private String inFeedStockCode;
	private String stockCodeDescription;
	private int machineID;
	private String machineName;
	private String routeName;
	private int routeGroupID;
	private Double tonsPerHour;
	private Double inFeedQuantityInHand;
	
	/**
	 * @param stockCode
	 */
	public TblBomRoute(String stockCode, int stockID) {
		super();
		this.stockCode = stockCode;
		this.stockID = stockID;
	}
	
	/**
	 * @param machineID
	 * @param machineName
	 */
	public TblBomRoute(int machineID, String machineName) {
		super();
		this.machineID = machineID;
		this.machineName = machineName;
	}
	
	/**
	 * @param routeID
	 * @param routeName
	 * @param routeGroupID
	 */
	public TblBomRoute(int routeID, String routeName, int routeGroupID, int stockID ) {
		super();
		this.routeID = routeID;
		this.routeName = routeName;
		this.routeGroupID = routeGroupID;
		this.stockID = stockID;
	}
	
	/**
	 * @param stockID
	 * @param stockCode
	 * @param stockCodeDescription
	 * @param machineID
	 * @param machineCode
	 */
	public TblBomRoute(int stockID, String stockCode, String stockCodeDescription, int machineID,
			String machineCode) {
		super();
		this.stockID = stockID;
		this.stockCode = stockCode;
		this.stockCodeDescription = stockCodeDescription;
		this.machineID = machineID;
		this.machineName = machineCode;
	}
	
	/**
     * @param routeID
     * @param stockID
     * @param stockCode
     * @param inFeedStockID
     * @param inFeedStockCode
     * @param machineID
     * @param machineName
     * @param routeName
     */
    public TblBomRoute(int routeID, int stockID, String stockCode, int inFeedStockID, String inFeedStockCode,
            int machineID, String machineName, String routeName, Double tonsPerHour, Double quantity) {
        super();
        this.routeID = routeID;
        this.stockID = stockID;
        this.stockCode = stockCode;
        this.inFeedStockID = inFeedStockID;
        this.inFeedStockCode = inFeedStockCode;
        this.machineID = machineID;
        this.machineName = machineName;
        this.routeName = routeName;
        this.tonsPerHour = tonsPerHour;
        this.inFeedQuantityInHand = quantity;
    }
    
    /** STRING RETURN TO DISPLAY ROUTE NAME IN COMBOBOX **/
    @Override
	public String toString() {
		return routeName;
	}
    
    public Double getInFeedQuantityInHand() {
		return inFeedQuantityInHand;
	}

	public void setInFeedQuantityInHand(Double inFeedQuantityInHand) {
		this.inFeedQuantityInHand = inFeedQuantityInHand;
	}
    public void setTonsPerHour(Double tonsPerHour) {
    	this.tonsPerHour = tonsPerHour;
    }
    public Double getTonsPerHour() {
    	return tonsPerHour;
    }

    public int getRouteID() {
		return routeID;
	}

	public int getRouteGroupID() {
		return routeGroupID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public void setRouteGroupID(int routeGroupID) {
		this.routeGroupID = routeGroupID;
	}

	public int getStockID() {
		return stockID;
	}

	public String getStockCode() {
		return stockCode;
	}

	public String getStockCodeDescription() {
		return stockCodeDescription;
	}

	public int getMachineID() {
		return machineID;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public void setStockCodeDescription(String stockCodeDescription) {
		this.stockCodeDescription = stockCodeDescription;
	}

	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public int getInFeedStockID() {
		return inFeedStockID;
	}

	public String getInFeedStockCode() {
		return inFeedStockCode;
	}

	public void setInFeedStockID(int inFeedStockID) {
		this.inFeedStockID = inFeedStockID;
	}

	public void setInFeedStockCode(String inFeedStockCode) {
		this.inFeedStockCode = inFeedStockCode;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
}
