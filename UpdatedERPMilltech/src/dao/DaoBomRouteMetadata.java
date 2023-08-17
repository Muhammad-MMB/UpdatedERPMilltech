package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoBomRouteMetadata {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** SEND BOM ROUTE METADATA ENTRY INTO DATABASE **/
	public void setBomRouteMetadata(String routeName, boolean isSubLevel, boolean isActive) throws SQLException {
		final String setBomRouteMetadataQuery = """
				INSERT INTO tbl_Bom_Route_Metadata(BomRouteID, RouteName, IsSubLevel, IsActive, DateCreated)
				VALUES((SELECT TOP 1 (BOMRouteID) FROM tbl_Bom_Route ORDER BY BOMRouteID DESC), ?, ?, ?, GETDATE())
					""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setBomRouteMetadataQuery);
			stmnt.setString(1,routeName);
			stmnt.setBoolean(2, isSubLevel);
			stmnt.setBoolean(3, isActive);
			stmnt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmnt != null) {
				stmnt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
}
