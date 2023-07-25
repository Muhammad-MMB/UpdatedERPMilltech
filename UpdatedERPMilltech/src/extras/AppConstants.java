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

	/** STATIC GLOBAL VARIABLES **/
	public final static int NO_OF_MONTHS_FOR_SALE_RPT = 12;
}
