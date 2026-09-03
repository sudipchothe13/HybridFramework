package UtilsLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import CommonLayer.ConfigReader;

public class DBUtility {

	private static Connection con;
	private static Statement stm;

	// Open connection (call once before all tests)
	public static void connectToDB() {
		try {
			Class.forName(ConfigReader.get("drivername"));
			String url = ConfigReader.get("dbURL");
			String username = ConfigReader.get("dbUsername");
			String password = ConfigReader.get("dbPassword");

			con = DriverManager.getConnection(url, username, password);
			stm = con.createStatement();
			System.out.println("Connection opened successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> selectQueryMap(String query) throws SQLException {

		Map<String, String> resultMap = new LinkedHashMap<>();

		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(query);
		ResultSetMetaData metaData = rs.getMetaData();

		while (rs.next()) {
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnName(i);
				String columnValue = rs.getString(i);
				resultMap.put(columnName, columnValue);
			}
		}

		return resultMap;
	}

	public static List<Map<String, String>> selectQueryMapList(String query) throws SQLException {

		List<Map<String, String>> resultList = new ArrayList<>();

		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(query);
		ResultSetMetaData metaData = rs.getMetaData();

		while (rs.next()) {
			Map<String, String> rowData = new LinkedHashMap<>();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnName(i);
				String columnValue = rs.getString(i);
				rowData.put(columnName, columnValue);
			}
			resultList.add(rowData);
		}

		return resultList;
	}

	public static List<String> executeQueryToDB(String query) throws SQLException {

		List<String> record = new ArrayList<>();

		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(query);

		try {
			int cols = rs.getMetaData().getColumnCount();
			System.out.println("cols = " + cols);

			if (rs.next()) {
				for (int i = 1; i <= cols; i++) {
					System.out.println(rs.getString(i));
					record.add(rs.getString(i));
				}
			}

			System.out.println("array = " + record);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return record;
	}

	// Fetch data (read-only)
	public static void retriveDateFromDB(String query) {
		ResultSet rs = null;
		try {
			if (con == null || con.isClosed()) {
				throw new SQLException("Connection is closed. Call connectToDB() first.");
			}

			rs = stm.executeQuery(query);
			int cols = rs.getMetaData().getColumnCount();

			if (rs.next()) {
				for (int i = 1; i <= cols; i++) {
					System.out.print(rs.getString(i) + "   ");
				}
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Do NOT close connection here
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Close connection (call once after all tests)
	public static void closeDB() {
		try {
			if (stm != null)
				stm.close();
			if (con != null && !con.isClosed())
				con.close();
			System.out.println("Connection closed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
