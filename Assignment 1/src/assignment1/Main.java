package assignment1; /////////////

import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.NodeList;


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
		
		
		// ARRAYLIST CREATION FOR ALL THE DIFERENT USED ELEMENTS
		// Depending on the class, different elements require different inputs - EQ lists, SSH lists...
		
		ArrayList <BaseVoltage> baseVoltList = BaseVoltage.getElements(baseVoltListEQ); // Base Voltage
		ArrayList<Substation> subList = Substation.getElements(subListEQ); // Substation
		ArrayList<VoltageLevel> voltLevelList = VoltageLevel.getElements(voltListEQ); // Voltage Level
		ArrayList<GeneratingUnit> genUnitList = GeneratingUnit.getElements(genUnitListEQ); // Generating Units
		ArrayList<SyncMachine> syncMachList = SyncMachine.getElements(syncMachListEQ, syncMachListSSH, voltListEQ); // Synchronous Machines
		ArrayList<RegulatingControl> regulControlList = RegulatingControl.getElements(regControlListEQ, regControlListSSH); // Regulating Control Units
		ArrayList<PowerTransformer> powerTransformerList = PowerTransformer.getElements(transformerListEQ); // Power Transformers
		ArrayList<EnergyConsumerLoad> loadList = EnergyConsumerLoad.getElements(loadListEQ, loadListSSH, voltListEQ); // Energy Consumers (Loads)
		ArrayList<Breaker> breakerList = Breaker.getElements(breakerListEQ, breakerListSSH, voltListEQ); // Breaker
		ArrayList<RatioTapChanger> ratioTapChangerList = RatioTapChanger.getElements(ratioTapChangListEQ); // Ratio Tap Changer (Step)
		ArrayList<Terminal> terminalList = Terminal.getElements(terminalListEQ); // Terminal
		ArrayList<ConnectivityNode> conNodeList = ConnectivityNode.getElements(connectivityNodeListEQ, voltListEQ); // Connectivity Nodes
		ArrayList<ShuntCompensator> shuntCompensatorList = ShuntCompensator.getElements(shuntCompensatorListEQ); // Linear Shunt Compensators
		ArrayList<BusBarSection> busbarList = BusBarSection.getElements(busbarListEQ); // BusBar Section
		
		ArrayList<ACLineSegment> lineSegmentList = ACLineSegment.getElements(lineSegmentACListEQ, baseVoltList, Sbase.Smax(syncMachList)); // AC Line Segment
		ArrayList<PowerTransformerEnd> powerTransformerEndList = PowerTransformerEnd.getElements(transfWindingListEQ, baseVoltList, Sbase.Smax(syncMachList)); // Power Transformer Ends (Transformer Windings)
		
		
		// Topology creation from Topology class
		
		ArrayList<Topology> topologyElements = Topology.getElements(lineSegmentList, terminalList, conNodeList, breakerList, busbarList, 
				powerTransformerList, powerTransformerEndList, syncMachList, shuntCompensatorList, loadList);
				
		// Create List of Real BusBars
		ArrayList<BusBarSection> realbusbarList = Ybus.realBuses(busbarList, topologyElements);
		
		// Ybus matrix creation from Ybus class
		ArrayList<Ybus> YbusMatrixElements = Ybus.createYbusMatrix(realbusbarList, topologyElements, lineSegmentList, powerTransformerEndList);
		
		// Create YbusMatrix table
		Complex[][] YbusMatrix = Ybus.YBusMatrix(YbusMatrixElements, realbusbarList);
		
		
		// Database creation.
		DatabaseCreation.createDatabase(lineSegmentList, baseVoltList, breakerList, busbarList, conNodeList, loadList,
				genUnitList, powerTransformerList, powerTransformerEndList, ratioTapChangerList, regulControlList,
				shuntCompensatorList, subList, syncMachList, terminalList, voltLevelList, YbusMatrixElements,YbusMatrix);
		
		
		// Print the columns of the YbusMatrix
		Ybus.printYbusMatrix(YbusMatrix);
		
	}
}


