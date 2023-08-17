package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.TblMachineOprStatesDtls;

public class DaoMachineOprStatesDtls {

	Connection con = null;
	PreparedStatement stmnt = null;
	ResultSet rs = null;

	/** SEND ALL MACHINE STATUS LOGS ENTRIES INTO DB */
	public void setAllMachineStatusDetails(int machineID, int oldStatusID, int newStatusID,
			int userId, String userNotes, boolean isActive, String systemName) throws SQLException {
		final String setAllMachineStatusDetailsQuery = """
				INSERT INTO tbl_Machine_Operation_States_Details
				        ([MachineID]
				        ,[OldMachineOperationStateID]
				        ,[NewMachineOperationStateID]
				        ,[UserID]
				        ,[StatusChangeUserNotes]
				        ,[ActionDate]
				        ,[ActionTime]
				        ,[isActive]
				        ,[UserWorkstationName])
				  VALUES
				        (?,?,?,?,?,GETDATE(),GETDATE(),?,?)
					""";
		try {
			// DATEADD(DAY,0,DATEDIFF(DAY,0,GETDATE()))

			con = DataSource.getConnection();
			stmnt = con.prepareStatement(setAllMachineStatusDetailsQuery);
			stmnt.setInt(1, machineID);
			stmnt.setInt(2, oldStatusID);
			stmnt.setInt(3, newStatusID);
			stmnt.setInt(4, userId);
			stmnt.setString(5, userNotes);
			stmnt.setBoolean(6, isActive);
			stmnt.setString(7, systemName);
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

	/** RETRIEVE ALL MACHINSE CHANGE STATUS LOGS */
	public ArrayList<TblMachineOprStatesDtls> getAllMachineChangeStatusLogs() throws SQLException {
		ArrayList<TblMachineOprStatesDtls> getAllMachineChangeStatusLogsArray = new ArrayList<>();
		final String getAllMachineChangeStatusLogsQuery = """
				SELECT TOP 7 maccode.MachineCode as MachineCode, opsd.OldMachineOperationStateID as OldMachineStatus, opsd.NewMachineOperationStateID as NewMachineStatus,
				opsd.StatusChangeUserNotes as UserNotes, CAST(opsd.ActionDate AS date) as DateOnly,
				CONVERT(VARCHAR(8), opsd.ActionDate, 108) + ' ' + RIGHT(CONVERT(VARCHAR(30), opsd.ActionDate, 9), 2) as TimeOnly,
				users.UserName as userName, opsd.UserWorkstationName as WorkstationName 
				FROM tbl_Machine_Operation_States_Details opsd,tbl_Machines mac, tbl_Machine_Operation_States macsts, tbl_Users users, tbl_Machine_Codes maccode
				WHERE mac.MachineID = opsd.MachineID
				AND users.UserID = opsd.UserID
				AND opsd.NewMachineOperationStateID = macsts.MachineOperationStateID
				AND maccode.MachineCodeID = mac.MachineCodeID
				ORDER BY opsd.MachineStatusRecordID DESC
				""";
		try {
			con = DataSource.getConnection();
			stmnt = con.prepareStatement(getAllMachineChangeStatusLogsQuery);
			rs = stmnt.executeQuery();
			if (rs.next()) {
				do {
					getAllMachineChangeStatusLogsArray.add(new TblMachineOprStatesDtls(rs.getString("MachineCode"), rs.getInt("OldMachineStatus"), rs.getInt("NewMachineStatus"),
							rs.getString("UserNotes"), rs.getString("DateOnly"), rs.getString("TimeOnly"), rs.getString("userName"), rs.getString("WorkstationName")));
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
		return getAllMachineChangeStatusLogsArray;
	}


}
