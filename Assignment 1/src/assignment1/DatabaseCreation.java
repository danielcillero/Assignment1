package assignment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseCreation {
	
	
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String DB_TZ = "?serverTimezone=UTC";
	
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "root"; // insert the password to SQL server

	
	public static void createDatabase (ArrayList<ACLineSegment> lineSegmentList, ArrayList<BaseVoltage> baseVoltList,
			ArrayList<Breaker> breakerList, ArrayList<BusBarSection> busbarList, ArrayList<ConnectivityNode> conNodeList,
			ArrayList<EnergyConsumerLoad> loadList, ArrayList<GeneratingUnit> genUnitList, ArrayList<PowerTransformer> powerTransformerList,
			ArrayList<PowerTransformerEnd> powerTransformerEndList, ArrayList<RatioTapChanger> ratioTapChangerList,
			ArrayList<RegulatingControl> regulControlList, ArrayList<ShuntCompensator> shuntCompensatorList, ArrayList<Substation> subList,
			ArrayList<SyncMachine> syncMachList, ArrayList<Terminal> terminalList, ArrayList<VoltageLevel> voltLevelList, 
			ArrayList<Ybus> YbusMatrixElements) {
		
		Connection conn = null;
		Statement stmt = null;
		
		
		try{
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", USER, PASS);
			
			
			// Execute a query to create database
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP DATABASE IF EXISTS Assignment1"); // Delete database if created before
			stmt.executeUpdate("CREATE DATABASE Assignment1"); // Create database
			stmt.close();
			System.out.println("Database created successfully...");
			
			
			// Connect to the created database
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment1?serverTimezone=UTC", USER, PASS);
			stmt = conn.createStatement();
			
			
			//ACLINES TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE ACLines (ID VARCHAR(50), equipContID VARCHAR(50), "
					+ "r DECIMAL(10,6), x DECIMAL(10,6), bch DECIMAL(10,6), gch DECIMAL(10,6), "
					+ "length DECIMAL(10,6), zbase DECIMAL(10,6), rpu DECIMAL(10,6), xpu DECIMAL(10,6), "
					+ "bchpu DECIMAL(10,6), gchpu DECIMAL(10,6) )");
			
			System.out.println("Created table in given database successfully...");
			
			// Insert values into the table
			for (ACLineSegment line:lineSegmentList){
				stmt.executeUpdate("INSERT INTO ACLines VALUES('" + line.ID + "','" + line.equipContID +
						"','" + line.R + "','" + line.X + "','" + line.bch + "','" + line.gch + "','" + line.length +
						"','" + line.Zbase + "','" + line.Rpu + "','" + line.Xpu + "','" + line.bchpu + "','" + line.gchpu + "')"); 
			}
			System.out.println("Inserted records into the table...");
			
			
			//BASE VOLTAGE TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE BaseVoltages (ID VARCHAR(50), NominalValue DECIMAL(10,6)");
			
			System.out.println("Created table in given database successfully...");
			
			// Insert values into the table
			for (BaseVoltage basevolt:baseVoltList){
				stmt.executeUpdate("INSERT INTO BaseVoltages VALUES('" + basevolt.ID + "','" + basevolt.nominalValue + "')"); 
			}
			System.out.println("Inserted records into the table...");
			
			////////////////////////////////////////////////////////////
			
			//Breakers TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE Breakers (ID VARCHAR(50), name VARCHAR(50), "
					+ "r DECIMAL(10,6), x DECIMAL(10,6), bch DECIMAL(10,6), gch DECIMAL(10,6), "
					+ "length DECIMAL(10,6), zbase DECIMAL(10,6), rpu DECIMAL(10,6), xpu DECIMAL(10,6), "
					+ "bchpu DECIMAL(10,6), gchpu DECIMAL(10,6) )");
			
			System.out.println("Created table in given database successfully...");
			
			// Insert values into the table
			for (ACLineSegment line:lineSegmentList){
				stmt.executeUpdate("INSERT INTO ACLines VALUES('" + line.ID + "','" + line.equipContID +
						"','" + line.R + "','" + line.X + "','" + line.bch + "','" + line.gch + "','" + line.length +
						"','" + line.Zbase + "','" + line.Rpu + "','" + line.Xpu + "','" + line.bchpu + "','" + line.gchpu + "')"); 
			}
			
			System.out.println("Inserted records into the table...");
			
			
			conn.close();
			
		}catch(SQLException se){
		
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();}
		
		System.out.println("Goodbye!");
		
	}
}
