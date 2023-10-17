package extras;

public class AppConstants {

	/** DATABASE CONFIGURATION -- LOCALHOST SERVER **/
	public static final String DATABSE_URL = "jdbc:sqlserver://localhost:1433;databaseName=Milltech_ERP; trustServerCertificate=true";
	public static final String DATABSE_USERNAME = "Muhammad_Admin";
	public static final String DATABASE_PASSWORD = "#National1";

	/** DATABASE CONFIGURATION -- REMOTE SERVER **/
	public static final String REMOTE_DATABSE_URL = "jdbc:sqlserver://192.168.2.11:1433;databaseName=Milltech_ERP; trustServerCertificate=true";
	public static final String REMOTE_DATABSE_USERNAME = "TCID";
	public static final String REMOTE_DATABASE_PASSWORD = "#National1";

	/** RESOURCE IMAGES **/
	public static final String NEW_FILE = "newfile.png";
	public static final String EXIT_APP = "exitApp.png";
	public static final String INVENTORY_TRANSACTIONS = "inventoryTransactions.png";
	public static final String HISTORICAL_SALE = "historySales.png";
	public static final String SALES = "sales.png";
	public static final String MAIN_INVENTORY = "Inventory-01.png";
	public static final String IMPORT_TABLES = "importTables.png";
	public static final String ENTITIES = "entities.png";
	public static final String IMPORT = "import.png";
	public static final String EXPORT = "export.png";
	public static final String ABOUT = "about.png";
	public static final String MMBLOGO = "mmblogo.png";
	public static final String SHORT_GREEN = "shortGreen.png";
	public static final String SHORT_RED = "shortRed.png";
	public static final String SHORT_YELLOW = "shortYellow.png";
	public static final String LONG_GREEN = "longGreen.gif";
	public static final String LONG_RED = "longRed.gif";
	public static final String LONG_YELLOW = "longYellow.gif";
	public static final String QUESTION_MARK = "questionMark.png";
	public static final String SEARCH = "search.png";
	public static final String STATIC_GREEN_TICK = "greenTick.png";
	public static final String STATIC_RED_CROSS = "redCross.png";
	public static final String BOM_ROUTE_NODE_RIGHT = "rightExpandArrow.png";
	public static final String BOM_ROUTE_NODE_EXPAND = "bottomExpandArrow.png";
	public static final String BOM_ROUTE_NODE_LEAF = "leafTree.png";
	public static final String RED_BLINK = "redBlink.gif";
	public static final String GREEN_BLINK = "greenBlink.gif";
	public static final String REFRESH = "refresh.png";
	public static final String PLUS = "plus.png";
	public static final String VIEW = "view.png";
	public static final String LOGIN_BACKGROUND = "loginBackground.png";
	public static final String USER_SYMBOL = "userSymbol.png";
	public static final String MMB_ANGLE_LOGO = "mmbAngleLogo.png";
	public static final String USER_NAME_SYMBOL = "userNameSymbol.png";
	public static final String PASSWORD_SYMBOL = "passwordSymbol.png";
	public static final String LOGIN_SYMBOL = "loginSymbol.png";
	public static final String USER_ROLE = "userRole.png";

	/** STATIC GLOBAL VARIABLES **/
	public static final int NO_OF_MONTHS_FOR_SALE_RPT = 12;

	/** MACHINES STATES ID'S MAPPED AS PER DATABASE **/
	public static final int READY = 2;
	public static final int BUSY = 3;
	public static final int MAINTENANCE = 4;

	/** MACHINES STATES VALUES **/
	public static final String READY_NAME = "Ready - (No Active Job)";
	public static final String BUSY_NAME = "Busy - (Inprogress Active Job)";
	public static final String MAINTENANCE_NAME = "Maintenance - (Malfunctioned)";

	/** BILL OF MATERIAL ROUTES VALUES **/
	public static final String JOB_PATH_TREE_NAME = "Job Path ";
	public static final String ACTIVE_BOM_TREE_NAME = "Active BOM Routes ";
	public static final String INACTIVE_BOM_TREE_NAME = "In-Active BOM Routes ";
	public static final String SANDBOX_TREE_NAME = "Bill Of Materials - BOM SANDBOX ";
	
	/** CUSTOMER ORDER PRIORITY TEXT **/
	public static final String CUSTOMER_ORDER_PRIORITY_TEXT = "ASAP";
	
	/** CUSTOMER ORDER STATES ID'S MAPPED AS PER DATABASE **/
	public static final int CUSTOMER_ORDER_STATE_FILLED = 1;
	public static final int CUSTOMER_ORDER_STATE_UNFILLED = 2;
	public static final int CUSTOMER_ORDER_STATE_INPROGRESS = 3;
}
