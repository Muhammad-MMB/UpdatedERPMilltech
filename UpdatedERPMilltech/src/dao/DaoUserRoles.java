
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblUserRoles;

/**
 * @author Muhammad
 *
 */
public class DaoUserRoles {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** RETRIEVE LIST OF ALL USER ROLES **/
	public ArrayList<TblUserRoles> fetchAllUserRoles() throws SQLException {
		ArrayList<TblUserRoles> fetchAllUserRoles = new ArrayList<>();
		final String fetchAllUserRolesQuery = """
				SELECT RoleID, RoleName, RoleDescription, IsActive, DateTime
				FROM tbl_User_Roles userRole
				WHERE userRole.IsActive = 1
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(fetchAllUserRolesQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					fetchAllUserRoles.add(new TblUserRoles(rs.getInt("RoleID"),
							rs.getString("RoleName"), rs.getString("RoleDescription"),  rs.getBoolean("IsActive"), rs.getString("DateTime")));
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmnt != null) {
				stmnt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return fetchAllUserRoles;
	}
}
