package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.*;

public class FillAllEntities {

	AllEntities allEntitiesObject;

	public ArrayList<String> getAllTablesName() throws SQLException {
		ArrayList<String> allTablesNameData = new ArrayList<>();
		final String getAllEntitiesNameQuery = "SELECT TABLE_NAME FROM information_schema.tables";
		try {
			Connection con = DataSource.getConnection();
			PreparedStatement stmnt = con.prepareStatement(getAllEntitiesNameQuery);
			ResultSet rs = stmnt.executeQuery();
			if (rs.next() == false) {
				System.out.println("No value found");
			} else {
				do {
					allEntitiesObject = new AllEntities(rs.getString("TABLE_NAME"), rs.getString("TABLE_NAME"));
					allTablesNameData.add(allEntitiesObject.getEntitiesName());
				} while (rs.next());
				stmnt.close();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allTablesNameData;
	}
}
