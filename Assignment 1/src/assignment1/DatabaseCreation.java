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
			stmt.executeUpdate("CREATE TABLE ACLines (ID VARCHAR(50), name VARCHAR(50), equipContID VARCHAR(50), r DECIMAL(10,6),"
					+ " x DECIMAL(10,6), bch DECIMAL(10,6), gch DECIMAL(10,6), length DECIMAL(10,6), zbase DECIMAL(10,6),"
					+ " rpu DECIMAL(10,6), xpu DECIMAL(10,6), bchpu DECIMAL(10,6), gchpu DECIMAL(10,6) )");
			
			System.out.println("Created ACLines table in given database successfully...");
			
			// Insert values into the table
			for (ACLineSegment line:lineSegmentList){
				stmt.executeUpdate("INSERT INTO ACLines VALUES('" + line.ID + "','" + line.name + "','" + line.equipContID +
						"','" + line.R + "','" + line.X + "','" + line.bch + "','" + line.gch + "','" + line.length +
						"','" + line.Zbase + "','" + line.Rpu + "','" + line.Xpu + "','" + line.bchpu + "','" + line.gchpu + "')"); 
			}
			System.out.println("Inserted records into ACLines table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//BASE VOLTAGE TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE BaseVoltages (ID VARCHAR(50), NominalValue DECIMAL(10,6) )");
			
			System.out.println("Created BaseVoltages table in given database successfully...");
			
			// Insert values into the table
			for (BaseVoltage basevolt:baseVoltList){
				stmt.executeUpdate("INSERT INTO BaseVoltages VALUES('" + basevolt.ID + "','" + basevolt.nominalValue + "')"); 
			}
			System.out.println("Inserted records into BaseVoltages table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//Breakers TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE Breakers (ID VARCHAR(50), name VARCHAR(50), "
					+ "state VARCHAR(50), equipContID VARCHAR(50), baseVoltID VARCHAR(50) )");
			
			System.out.println("Created Breakers table in given database successfully...");
			
			// Insert values into the table
			for (Breaker breaker:breakerList){
				stmt.executeUpdate("INSERT INTO Breakers VALUES('" + breaker.ID + "','" + breaker.name +
						"','" + String.valueOf(breaker.Open) + "','" + breaker.equipContID + "','" + breaker.baseVoltID + "')"); 
			}
			
			System.out.println("Inserted records into Breakers table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//Busbar TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE Busbars (ID VARCHAR(50), name VARCHAR(50), equipContID VARCHAR(50) )");
			
			System.out.println("Created Busbars table in given database successfully...");
			
			// Insert values into the table
			for (BusBarSection busbar:busbarList){
				stmt.executeUpdate("INSERT INTO Busbars VALUES('" + busbar.ID + "','" + busbar.name + "','" + busbar.equipContID + "')"); 
			}
			System.out.println("Inserted records into Busbars table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//ConnectivityNodes TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE ConnectivityNodes (ID VARCHAR(50), name VARCHAR(50), "
					+ "conContID VARCHAR(50), baseVoltID VARCHAR(50) )");
			
			System.out.println("Created ConnectivityNodes table in given database successfully...");
			
			// Insert values into the table
			for (ConnectivityNode conn_node:conNodeList){
				stmt.executeUpdate("INSERT INTO ConnectivityNodes VALUES('" + conn_node.ID + "','" + conn_node.name +
						"','" + conn_node.conContID + "','" + conn_node.baseVoltID + "')"); 
			}
			
			System.out.println("Inserted records into ConnectivityNodes table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//Loads TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE Loads (ID VARCHAR(50), name VARCHAR(50), P DECIMAL(10,6), "
					+ " Q DECIMAL(10,6), equipContID VARCHAR(50), baseVoltID VARCHAR(50) )");
			
			System.out.println("Created Loads table in given database successfully...");
			
			// Insert values into the table
			for (EnergyConsumerLoad load:loadList){
				stmt.executeUpdate("INSERT INTO Loads VALUES('" + load.ID + "','" + load.name +
						"','" + load.P + "','" + load.Q + "','" + load.equipContID + "','" + load.baseVoltID + "')"); 
			}
			
			System.out.println("Inserted records into Loads table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//GeneratingUnit TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE GeneratingUnits (ID VARCHAR(50), name VARCHAR(50), maxP DECIMAL(10,6), "
					+ " minP DECIMAL(10,6), equipContID VARCHAR(50) )");
			
			System.out.println("Created GeneratingUnits table in given database successfully...");
			
			// Insert values into the table
			for (GeneratingUnit gen_unit:genUnitList){
				stmt.executeUpdate("INSERT INTO GeneratingUnits VALUES('" + gen_unit.ID + "','" + gen_unit.name +
						"','" + gen_unit.maxP + "','" + gen_unit.minP + "','" + gen_unit.equipContID + "')"); 
			}
			
			System.out.println("Inserted records into GeneratingUnits table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//PowerTransformers TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE PowerTransformers (ID VARCHAR(50), name VARCHAR(50), equipContID VARCHAR(50) )");
			
			System.out.println("Created PowerTransformers table in given database successfully...");
			
			// Insert values into the table
			for (PowerTransformer power_transf:powerTransformerList){
				stmt.executeUpdate("INSERT INTO PowerTransformers VALUES('" + power_transf.ID + "','" + power_transf.name +
						"','" + power_transf.equipContID + "')"); 
			}
			
			System.out.println("Inserted records into PowerTransformers table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//PowerTransformersEnds TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE PowerTransformersEnds (ID VARCHAR(50), name VARCHAR(50), r DECIMAL(10,6), x DECIMAL(10,6),"
					+ " b DECIMAL(10,6), g DECIMAL(10,6), transformerID VARCHAR(50), baseVoltID VARCHAR(50), terminalID VARCHAR(50), "
					+ " zbase DECIMAL(10,6), rpu DECIMAL(10,6), xpu DECIMAL(10,6), bpu DECIMAL(10,6), gpu DECIMAL(10,6) )");
			
			System.out.println("Created PowerTransformersEnds table in given database successfully...");
			
			// Insert values into the table
			for (PowerTransformerEnd winding:powerTransformerEndList){
				stmt.executeUpdate("INSERT INTO PowerTransformersEnds VALUES('" + winding.ID + "','" + winding.name + "','" + winding.R +
						"','" + winding.X + "','" + winding.bch + "','" + winding.gch + "','" + winding.transformerID +
						"','" + winding.baseVoltID + "','" + winding.TerminalID + "','" + winding.Zbase + "','" + winding.Rpu +
						"','" + winding.Xpu + "','" + winding.bchpu + "','" + winding.gchpu + "')"); 
			}
			System.out.println("Inserted records into PowerTransformersEnds table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//RatioTapChangers TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE RatioTapChangers (ID VARCHAR(50), name VARCHAR(50), step DECIMAL(10,6) )");
			
			System.out.println("Created RatioTapChangers table in given database successfully...");
			
			// Insert values into the table
			for (RatioTapChanger tap_changer:ratioTapChangerList){
				stmt.executeUpdate("INSERT INTO RatioTapChangers VALUES('" + tap_changer.ID + "','" + tap_changer.name +
						"','" + tap_changer.step + "')"); 
			}
			
			System.out.println("Inserted records into RatioTapChangers table...");
			
			
			//RegulatingControls TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE RegulatingControls (ID VARCHAR(50), name VARCHAR(50), targetValue DECIMAL(10,6) )");
			
			System.out.println("Created RegulatingControls table in given database successfully...");
			
			// Insert values into the table
			for (RegulatingControl reg_control:regulControlList){
				stmt.executeUpdate("INSERT INTO RegulatingControls VALUES('" + reg_control.ID + "','" + reg_control.name +
						"','" + reg_control.targetValue + "')"); 
			}
			
			System.out.println("Inserted records into RegulatingControls table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//ShuntCompensators TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE ShuntCompensators (ID VARCHAR(50), equipContID VARCHAR(50) )");
			
			System.out.println("Created ShuntCompensators table in given database successfully...");
			
			// Insert values into the table
			for (ShuntCompensator shunt_comp:shuntCompensatorList){
				stmt.executeUpdate("INSERT INTO ShuntCompensators VALUES('" + shunt_comp.ID + "','" + shunt_comp.equipContID + "')"); 
			}
			
			System.out.println("Inserted records into ShuntCompensators table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//Substations TABLE CREATION
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE Substations (ID VARCHAR(50), name VARCHAR(50), regionID VARCHAR(50) )");
			
			System.out.println("Created Substations table in given database successfully...");
			
			// Insert values into the table
			for (Substation subs:subList){
				stmt.executeUpdate("INSERT INTO Substations VALUES('" + subs.ID + "','" + subs.name +
						"','" + subs.regionID + "')"); 
			}
			
			System.out.println("Inserted records into Substations table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//SynchMachines TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE SynchMachines (ID VARCHAR(50), name VARCHAR(50), ratedS DECIMAL(10,6), P DECIMAL(10,6),"
					+ " Q DECIMAL(10,6), genUnitID VARCHAR(50), regControlID VARCHAR(50), equipContID VARCHAR(50), baseVoltID VARCHAR(50) )");
			
			System.out.println("Created SynchMachines table in given database successfully...");
			
			// Insert values into the table
			for (SyncMachine synch_mach:syncMachList){
				stmt.executeUpdate("INSERT INTO SynchMachines VALUES('" + synch_mach.ID + "','" + synch_mach.name + "','" + synch_mach.ratedS +
						"','" + synch_mach.P + "','" + synch_mach.Q + "','" + synch_mach.genUnitID + "','" + synch_mach.regControlID +
						"','" + synch_mach.equipContID + "','" + synch_mach.baseVoltID + "')"); 
			}
			System.out.println("Inserted records into SynchMachines table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//Terminals TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE Terminals (ID VARCHAR(50), sequenceNumber INT, condEquipID VARCHAR(50), connNodeID VARCHAR(50) )");
			
			System.out.println("Created Terminals table in given database successfully...");
			
			// Insert values into the table
			for (Terminal terminal:terminalList){
				stmt.executeUpdate("INSERT INTO Terminals VALUES('" + terminal.ID + "','" + terminal.sequenceNumber + 
						"','" +terminal.ConductingEquipment + "','" + terminal.ConnectivityNode + "')"); 
			}
			System.out.println("Inserted records into Terminals table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//VoltageLevels TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE VoltageLevels (ID VARCHAR(50), name VARCHAR(50), SubstationID VARCHAR(50), baseVoltID VARCHAR(50) )");
			
			System.out.println("Created VoltageLevels table in given database successfully...");
			
			// Insert values into the table
			for (VoltageLevel voltlevel:voltLevelList){
				stmt.executeUpdate("INSERT INTO VoltageLevels VALUES('" + voltlevel.ID + "','" + voltlevel.name + 
						"','" +voltlevel.substationID + "','" + voltlevel.baseVoltageID + "')"); 
			}
			System.out.println("Inserted records into VoltageLevels table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			//YbusMatrix TABLE
			// Create Table with corresponding columns
			stmt.executeUpdate("CREATE TABLE YbusMatrix (FromBus VARCHAR(50), ToBus VARCHAR(50), Admittance VARCHAR(50) )");
			
			System.out.println("Created YbusMatrix table in given database successfully...");
			
			// Insert values into the table
			for (Ybus ybusElement:YbusMatrixElements){
				stmt.executeUpdate("INSERT INTO YbusMatrix VALUES('" + ybusElement.FromBus + "','" + ybusElement.ToBus + 
						"','" + ybusElement.Admittance.toString() + "')"); 
			}
			System.out.println("Inserted records into YbusMatrix table...");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			
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
