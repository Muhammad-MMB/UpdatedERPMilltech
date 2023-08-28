package extras;

public class AppConstants {

	/** DATABASE CONFIGURATION -- LOCALHOST SERVER **/
	public final static String DATABSE_URL = "jdbc:sqlserver://localhost:1433;databaseName=Milltech_ERP; trustServerCertificate=true";
	public final static String DATABSE_USERNAME = "Muhammad_Admin";
	public final static String DATABASE_PASSWORD = "#National1";

	/** DATABASE CONFIGURATION -- REMOTE SERVER **/
	public final static String REMOTE_DATABSE_URL = "jdbc:sqlserver://192.168.2.11:1433;databaseName=Milltech_ERP; trustServerCertificate=true";
	public final static String REMOTE_DATABSE_USERNAME = "TCID";
	public final static String REMOTE_DATABASE_PASSWORD = "#National1";
	
	/** RESOURCE IMAGES **/
	public static String NEW_FILE = "newfile.png";
	public static String EXIT_APP = "exitApp.png";
	public static String INVENTORY_TRANSACTIONS = "inventoryTransactions.png";
	public static String HISTORICAL_SALE = "historySales.png";
	public static String SALES = "sales.png";
	public static String MAIN_INVENTORY = "Inventory-01.png";
	public static String IMPORT_TABLES = "importTables.png";
	public static String ENTITIES = "entities.png";
	public static String IMPORT = "import.png";
	public static String EXPORT = "export.png";
	public static String ABOUT = "about.png";
	public static String MMBLOGO = "mmblogo.png";
	public static String SHORT_GREEN = "shortGreen.png";
	public static String SHORT_RED = "shortRed.png";
	public static String SHORT_YELLOW = "shortYellow.png";
	public static String LONG_GREEN = "longGreen.gif";
	public static String LONG_RED = "longRed.gif";
	public static String LONG_YELLOW = "longYellow.gif";
	public static String QUESTION_MARK = "questionMark.png";
	public static String SEARCH = "search.png";
	public static String ACTIVE_BOM_ROUTE_ROOT = "activeTree.png";
	public static String INACTIVE_BOM_ROUTE_ROOT = "inActiveTree.png";
	public static String BOM_ROUTE_NODE_RIGHT = "rightExpandArrow.png";
	public static String BOM_ROUTE_NODE_EXPAND = "bottomExpandArrow.png";
	public static String BOM_ROUTE_NODE_LEAF = "leafTree.png";
	public static String RED_BLINK = "redBlink.gif";
	public static String GREEN_BLINK = "greenBlink.gif";
	
	/** STATIC GLOBAL VARIABLES **/
	public final static int NO_OF_MONTHS_FOR_SALE_RPT = 12;
	
	/** MACHINES STATES AS PER DATABASE  **/
	public final static int READY = 2;
	public final static int BUSY = 3;
	public final static int MAINTENANCE = 4;
	
	public final static String READY_NAME = "Ready - (No Active Job)";
	public final static String BUSY_NAME = "Busy - (Inprogress Active Job)";
	public final static String MAINTENANCE_NAME = "Maintenance - (Malfunctioned)";
	
	/** BOM ROUTES  **/
	public final static String ACTIVE_BOM_TREE_NAME = "Active BOM Routes ";
	public final static String INACTIVE_BOM_TREE_NAME = "In-Active BOM Routes ";
	public final static String SANDBOX_TREE_NAME = "Bill Of Materials - BOM SANDBOX ";
	
	
	
	
	
}
