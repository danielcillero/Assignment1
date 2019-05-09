package assignment1; /////////////

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
	public static void main(String[] args) {
		
		File EQFile = new File("Assignment_EQ_reduced.xml");
		File SSHFile = new File("Assignment_SSH_reduced.xml");
		
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
		
		// Base Voltage
		
		ArrayList<BaseVoltage> baseVoltList = new ArrayList<>();
		
		for (int i=0 ; i < baseVoltListEQ.getLength() ; i++) {
			
			Node node = baseVoltListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			BaseVoltage basVolt = new BaseVoltage(eElementEQ);
			
			baseVoltList.add(basVolt);
			
		}
		
		// Substation
		
		ArrayList<Substation> subList = new ArrayList<>();
		
		for (int i=0 ; i < subListEQ.getLength() ; i++) {
			
			Node node = subListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			Substation substation = new Substation(eElementEQ);
			
			subList.add(substation);
			
		}		
		
		// Voltage Level
		
		ArrayList<VoltageLevel> voltLevelList = new ArrayList<>();
		
		for (int i=0 ; i < voltListEQ.getLength() ; i++) {
			
			Node node = voltListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			VoltageLevel voltLevel = new VoltageLevel(eElementEQ);
			
			voltLevelList.add(voltLevel);
			
		}	
		
		// Generating Units
		
		ArrayList<GeneratingUnit> genUnitList = new ArrayList<>();
		
		for (int i=0 ; i < genUnitListEQ.getLength() ; i++) {
			
			Node node = genUnitListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			GeneratingUnit genUnit = new GeneratingUnit(eElementEQ);
			
			genUnitList.add(genUnit);
			
		}
		
		// Synchronous Machines
		
		ArrayList<SyncMachine> syncMachList = new ArrayList<>();
		
		for (int i=0 ; i < syncMachListEQ.getLength() ; i++) {
			
			Node node = syncMachListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < syncMachListSSH.getLength() ; j++) {
				Node nodeEQ = syncMachListSSH.item(i);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
					
					//System.out.println(rdfID + " is equal to " + rdfSSH); // For testing
										
					SyncMachine symach = new SyncMachine(eElementEQ,eElementSSH,voltListEQ);
					
					syncMachList.add(symach);
					
				}
				
			}
		}
		
		// Regulating Control Units
		
		ArrayList<RegulatingControl> regulControlList = new ArrayList<>();
		
		for (int i=0 ; i < regControlListEQ.getLength() ; i++) {
			
			Node node = regControlListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < regControlListSSH.getLength() ; j++) {
				Node nodeEQ = regControlListSSH.item(i);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
					
					//System.out.println(rdfID + " is equal to " + rdfSSH); // For testing
										
					RegulatingControl regControl = new RegulatingControl(eElementEQ,eElementSSH);
					
					regulControlList.add(regControl);
					
				}
				
			}
		}
		
		// Power Transformers
		
		ArrayList<PowerTransformer> powerTransformerList = new ArrayList<>();
		
		for (int i=0 ; i < transformerListEQ.getLength() ; i++) {
			
			Node node = transformerListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			PowerTransformer transformer = new PowerTransformer(eElementEQ);
			
			powerTransformerList.add(transformer);
			
		}		
		
		// Energy Consumers (Loads)
		
		ArrayList<EnergyConsumerLoad> loadList = new ArrayList<>();
		
		for (int i=0 ; i < loadListEQ.getLength() ; i++) {
			
			Node node = loadListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < loadListSSH.getLength() ; j++) {
				Node nodeEQ = loadListSSH.item(i);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
					
					//System.out.println(rdfID + " is equal to " + rdfSSH); // For testing
										
					EnergyConsumerLoad load = new EnergyConsumerLoad(eElementEQ,eElementSSH,voltListEQ);
					
					loadList.add(load);
					
				}
				
			}
		}		
		
		// Power Transformer Ends (Transformer Windings)
		
		ArrayList<PowerTransformerEnd> powerTransformerEndList = new ArrayList<>();
		
		for (int i=0 ; i < transfWindingListEQ.getLength() ; i++) {
			
			Node node = transfWindingListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			PowerTransformerEnd winding = new PowerTransformerEnd(eElementEQ);
			
			powerTransformerEndList.add(winding);
			
		}
		
		// Breaker
		
		ArrayList<Breaker> breakerList = new ArrayList<>();
		
		for (int i=0 ; i < breakerListEQ.getLength() ; i++) {
			
			Node node = breakerListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			Breaker breaker = new Breaker(eElementEQ,voltListEQ);
			
			breakerList.add(breaker);
			
		}
				
		// Ratio Tap Changer (Step)
		
		ArrayList<RatioTapChanger> ratioTapChangerList = new ArrayList<>();
		
		for (int i=0 ; i < ratioTapChangListEQ.getLength() ; i++) {
			
			Node node = ratioTapChangListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			RatioTapChanger ratioTapChanger = new RatioTapChanger(eElementEQ);
			
			ratioTapChangerList.add(ratioTapChanger);
			
		}
		
		// Terminal
		
		ArrayList<Terminal> terminalList = new ArrayList<>();
		
		for (int i=0 ; i < terminalListEQ.getLength() ; i++) {
			
			Node node = terminalListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			Terminal termin = new Terminal(eElementEQ);
			
			terminalList.add(termin);
			
		}
		
		// Connectivity Nodes
		
		ArrayList<ConnectivityNode> conNodeList = new ArrayList<>();
		
		for (int i=0 ; i < connectivityNodeListEQ.getLength() ; i++) {
			
			Node node = connectivityNodeListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			ConnectivityNode conNode = new ConnectivityNode(eElementEQ,voltListEQ);
			
			conNodeList.add(conNode);
			
		}	
		
		// Linear Shunt Compensators
		
		ArrayList<ShuntCompensator> shuntCompensatorList = new ArrayList<>();
		
		for (int i=0 ; i < shuntCompensatorListEQ.getLength() ; i++) {
			
			Node node = shuntCompensatorListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			ShuntCompensator shunt = new ShuntCompensator(eElementEQ);
			
			shuntCompensatorList.add(shunt);
			
		}		
		
		// AC Line Segment
		
		ArrayList<ACLineSegment> lineSegmentList = new ArrayList<>();
		
		for (int i=0 ; i < lineSegmentACListEQ.getLength() ; i++) {
			
			Node node = lineSegmentACListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			ACLineSegment line = new ACLineSegment(eElementEQ);
			
			lineSegmentList.add(line);
			
		}
		
		// BusBar Section
		
		ArrayList<BusBarSection> busbarList = new ArrayList<>();
		
		for (int i=0 ; i < busbarListEQ.getLength() ; i++) {
			
			Node node = busbarListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			BusBarSection busbar = new BusBarSection(eElementEQ);
			
			busbarList.add(busbar);
			
		}
		
		// Try to see if it works
		
	//	System.out.println("For the SyncMach with ID " + syncMachList.get(0).ID + " the ID of the VoltageLevel is " + 
	//				syncMachList.get(0).equipContID + " and the base voltage ID is " + syncMachList.get(0).baseVoltID);
		
		
		// Topology
		
		ArrayList<Topology> topologyElements = new ArrayList<>();
		
		for (ACLineSegment line:lineSegmentList) {
			
			ArrayList<String> busIDs = new ArrayList<>();
			
			for (Terminal terminal1:terminalList) {
				
				if (line.ID.equals(terminal1.ConductingEquipment)) {
				
					for (ConnectivityNode node1:conNodeList) {
						
						if (terminal1.ConnectivityNode.equals(node1.ID)) {
						
							for (Terminal terminal2:terminalList) {
								
								if (node1.ID.equals(terminal2.ConnectivityNode) && !terminal2.equals(terminal1)) { // The ! symbol makes the negative
									
									for (Breaker breaker:breakerList) {
										
										if (terminal2.ConductingEquipment.equals(breaker.ID)) {
										
											for (Terminal terminal3:terminalList) {
												
												if (breaker.ID.equals(terminal3.ConductingEquipment) && !terminal3.equals(terminal1) 
														&& !terminal3.equals(terminal2)) {
													
													for (ConnectivityNode node2:conNodeList) {
														
														if (node2.ID.equals(terminal3.ConnectivityNode)) {
															
															for (Terminal terminal4:terminalList) {
																
																if (terminal4.ConnectivityNode.equals(node2.ID) && !terminal4.equals(terminal1) 
																		&& !terminal4.equals(terminal2) && !terminal4.equals(terminal3)) {
																	
																	for (BusBarSection busbar:busbarList) {
																		
																		if (terminal4.ConductingEquipment.equals(busbar.ID)) {
																			
																			busIDs.add(busbar.ID);
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}								
							}
						}
					}
				}
			}
			
			Topology topo = new Topology("AC Line", line.ID, busIDs.get(0), busIDs.get(1));
			topologyElements.add(topo);
		}
		
		for (PowerTransformer trans:powerTransformerList) {
			
			ArrayList<String> busIDs = new ArrayList<>();
			
			for (PowerTransformerEnd wind:powerTransformerEndList) {
				
				if (trans.ID.equals(wind.transformerID)) {
					
					System.out.println("Winding works"); // For testing
			
					for (Terminal terminal1:terminalList) {
				
						if (wind.ID.equals(terminal1.ConductingEquipment)) {
							
							System.out.println("terminal 1"); // For testing
				
							for (ConnectivityNode node1:conNodeList) {
						
								if (terminal1.ConnectivityNode.equals(node1.ID)) {
									
									System.out.println("node1 works"); // For testing
						
									for (Terminal terminal2:terminalList) {
								
										if (node1.ID.equals(terminal2.ConnectivityNode) && !terminal2.equals(terminal1)) { // The ! symbol makes the negative
									
											System.out.println("terminal2 works"); // For testing
											
											for (Breaker breaker:breakerList) {
												
												
										
												if (terminal2.ConductingEquipment.equals(breaker.ID)) {
										
													for (Terminal terminal3:terminalList) {
												
														if (breaker.ID.equals(terminal3.ConductingEquipment) && !terminal3.equals(terminal1) 
																&& !terminal3.equals(terminal2)) {
													
															for (ConnectivityNode node2:conNodeList) {
														
																if (node2.ID.equals(terminal3.ConnectivityNode)) {
															
																	for (Terminal terminal4:terminalList) {
																
																		if (terminal4.ConnectivityNode.equals(node2.ID) && !terminal4.equals(terminal1) 
																				&& !terminal4.equals(terminal2) && !terminal4.equals(terminal3)) {
																	
																			for (BusBarSection busbar:busbarList) {
																		
																				if (terminal4.ConductingEquipment.equals(busbar.ID)) {
																			
																					busIDs.add(busbar.ID);
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												} else {
													
													for (BusBarSection busbar:busbarList) {
														
														if (terminal2.ConductingEquipment.equals(busbar.ID)) {
													
															busIDs.add(busbar.ID); 
														}
													}
												}
											}
										}								
									}
								}
							}
						}
					}
				}
			}
			
			Topology topo = new Topology("Transformer", trans.ID, busIDs.get(0), busIDs.get(1));
			topologyElements.add(topo);
		}
		
		for (Topology topo:topologyElements) {
			System.out.println("The " + topo.Type + " which ID is " + topo.ID + " is connected to the busbar " + topo.IDBusBar1 + " and " + topo.IDBusBar2 + "\n");
		}
		
		
		

	}

}