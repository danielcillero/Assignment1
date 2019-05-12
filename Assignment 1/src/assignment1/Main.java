package assignment1; /////////////

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;

public class Main {
	public static void main(String[] args) {
		
		File EQFile = new File("MicroGridTestConfiguration_T1_BE_EQ_V2.xml");
		File SSHFile = new File("MicroGridTestConfiguration_T1_BE_SSH_V2.xml");
		
		Reader EQ = new Reader(EQFile,"EQ");
		Reader SSH = new Reader(SSHFile,"SSH");
		
		// EQ NodeLists
		NodeList baseVoltListEQ = EQ.nodes.get(0);
		NodeList subListEQ = EQ.nodes.get(1); 
		NodeList voltListEQ = EQ.nodes.get(2);
		NodeList genUnitListEQ = EQ.nodes.get(3);
		NodeList syncMachListEQ = EQ.nodes.get(4);
		NodeList regControlListEQ = EQ.nodes.get(5);
		NodeList transformerListEQ = EQ.nodes.get(6);
		NodeList loadListEQ = EQ.nodes.get(7);
		NodeList transfWindingListEQ = EQ.nodes.get(8);
		NodeList breakerListEQ = EQ.nodes.get(9);
		NodeList ratioTapChangListEQ = EQ.nodes.get(10);
		NodeList terminalListEQ = EQ.nodes.get(11);
		NodeList connectivityNodeListEQ = EQ.nodes.get(12);
		NodeList shuntCompensatorListEQ = EQ.nodes.get(13);
		NodeList lineSegmentACListEQ = EQ.nodes.get(14);
		NodeList busbarListEQ = EQ.nodes.get(15);
		
		// SSH NodeLists
		NodeList syncMachListSSH = SSH.nodes.get(0);
		NodeList regControlListSSH = SSH.nodes.get(1);
		NodeList loadListSSH = SSH.nodes.get(2);
		NodeList breakerListSSH = SSH.nodes.get(3);
		
		// Base Voltage
		
		ArrayList <BaseVoltage> baseVoltList = BaseVoltage.getElements(baseVoltListEQ);
				
		// Substation
		
		ArrayList<Substation> subList = Substation.getElements(subListEQ);
		
		// Voltage Level
		
		ArrayList<VoltageLevel> voltLevelList = VoltageLevel.getElements(voltListEQ);
		
		// Generating Units
		
		ArrayList<GeneratingUnit> genUnitList = GeneratingUnit.getElements(genUnitListEQ);
		
		// Synchronous Machines
		
		ArrayList<SyncMachine> syncMachList = SyncMachine.getElements(syncMachListEQ, syncMachListSSH, voltListEQ);
				
		// Regulating Control Units
		
		ArrayList<RegulatingControl> regulControlList = RegulatingControl.getElements(regControlListEQ, regControlListSSH);
		
		// Power Transformers
		
		ArrayList<PowerTransformer> powerTransformerList = PowerTransformer.getElements(transformerListEQ);
		
		// Energy Consumers (Loads)
		
		ArrayList<EnergyConsumerLoad> loadList = EnergyConsumerLoad.getElements(loadListEQ, loadListSSH, voltListEQ);
						
		// Breaker
		
		ArrayList<Breaker> breakerList = Breaker.getElements(breakerListEQ, breakerListSSH, voltListEQ);
				
		// Ratio Tap Changer (Step)
		
		ArrayList<RatioTapChanger> ratioTapChangerList = RatioTapChanger.getElements(ratioTapChangListEQ);
		
		// Terminal
		
		ArrayList<Terminal> terminalList = Terminal.getElements(terminalListEQ);
		
		// Connectivity Nodes
		
		ArrayList<ConnectivityNode> conNodeList = ConnectivityNode.getElements(connectivityNodeListEQ, voltListEQ);
		
		// Linear Shunt Compensators
		
		ArrayList<ShuntCompensator> shuntCompensatorList = ShuntCompensator.getElements(shuntCompensatorListEQ);	
				
		// BusBar Section
		
		ArrayList<BusBarSection> busbarList = BusBarSection.getElements(busbarListEQ);
		
		// For power transformer ends and AC lines we need the base power which will be the maximum S of all the Synchronous Machines
		
		ArrayList<Double> maxSList = new ArrayList<>();
		
		for (SyncMachine sync:syncMachList) {
			
			maxSList.add(sync.ratedS);
		}
		
		Double maxS = 0.0;
		
		for (int i=0 ; i<maxSList.size() ; i++) {
			
			if (maxSList.get(i) > maxS) {
				maxS = maxSList.get(i);
			}
		}
		
		// AC Line Segment
		
		ArrayList<ACLineSegment> lineSegmentList = ACLineSegment.getElements(lineSegmentACListEQ, baseVoltList, maxS);
		
		// Power Transformer Ends (Transformer Windings)
		
		ArrayList<PowerTransformerEnd> powerTransformerEndList = PowerTransformerEnd.getElements(transfWindingListEQ, baseVoltList, maxS);
		
		
		
		
		// Topology creation from Topology class
		ArrayList<Topology> topologyElements = Topology.getElements(lineSegmentList, terminalList, conNodeList, breakerList, busbarList, powerTransformerList, powerTransformerEndList);
				
		
		// Ybus matrix creation from YbusCreation class
		ArrayList<Ybus> YbusMatrixElements = YbusCreation.createYbusMatrix(busbarList, topologyElements, lineSegmentList, powerTransformerEndList);
		
		// YbusMatrix test
		int count = 0;
		
		for (Ybus YbusElement:YbusMatrixElements) {
			
			count = count + 1;
			System.out.println("The admittance from bus " + YbusElement.FromBus + " to bus " + YbusElement.ToBus + " is " + YbusElement.Admittance + ". Iteration nº: " + count);
		}
		
		
		
		// Database creation.
		
		DatabaseCreation.createDatabase(lineSegmentList, baseVoltList, breakerList, busbarList, conNodeList, loadList,
				genUnitList, powerTransformerList, powerTransformerEndList, ratioTapChangerList, regulControlList,
				shuntCompensatorList, subList, syncMachList, terminalList, voltLevelList, YbusMatrixElements);
		
		
		
		//baseVoltList, subList , voltLevelList , genUnitList, syncMachList 
		//regulControlList, powerTransformerList, loadList, breakerList
		//ratioTapChangerList, terminalList, conNodeList, shuntCompensatorList	
		//busbarList, lineSegmentList, powerTransformerEndList
		
		

	}

}






