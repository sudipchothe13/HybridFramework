//package UtilsLayer;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.testng.Assert;
//
//import BaseLayer.BaseClass;
//
//public class UtilsDB extends BaseClass{
//	
//	public static void retriveDateFromDB(String query) throws SQLException {
//		
//		try {
//			ResultSet rs = stm.executeQuery(query);
//
//			int cols = rs.getMetaData().getColumnCount();
//
//			rs.next();
//
//			for (int i = 1; i <= cols; i++) {
//				System.out.print(rs.getString(i)+"   ");
//			}
//			 con.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//
//	    public static void assertDBValue(String query, String columnName, String expectedValue) {
//	        try {
//	         
//	        	BaseClass.connectToDB();
//
//	            ResultSet rs = con.createStatement().executeQuery(query);
//	        
//	            if (rs.next()) {
//	               
//	                String actualValue = rs.getString(columnName); 
//
//	                Assert.assertEquals(actualValue, expectedValue, "Value from database does not match expected value");
//	            } else {
//	                // Handle the case when no data is found in the result set
//	                Assert.fail("No data found in the database for the given query");
//	            }
//
//	            // Close the database connection
//	            con.close();
//	        } catch (Exception e) {
//	            // Handle any exceptions that may occur during the database operation
//	            e.printStackTrace();
//	            Assert.fail("An error occurred while accessing the database");
//	        }
//	    }
//	}
//
//	
//	
//
//
//	    
//	
//
//
//
