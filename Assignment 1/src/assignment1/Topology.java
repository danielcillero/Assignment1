package assignment1; ///////////// 

import java.util.ArrayList;

public class Topology {
	
	String Type;
	String ID;
	String IDBusBar1;
	String IDBusBar2;
		
	public Topology(String Type, String ID, String IDBusBar1, String IDBusBar2) {
		this.Type = Type;
		this.ID = ID;
		this.IDBusBar1 = IDBusBar1;
		this.IDBusBar2 = IDBusBar2;

		}

	public static ArrayList<Topology> getElements (ArrayList<ACLineSegment> lineSegmentList, ArrayList<Terminal> terminalList, ArrayList<ConnectivityNode> conNodeList, ArrayList<Breaker> breakerList, 
			ArrayList<BusBarSection> busbarList, ArrayList<PowerTransformer> powerTransformerList, ArrayList<PowerTransformerEnd> powerTransformerEndList, 
			ArrayList<SyncMachine> syncMachList, ArrayList<ShuntCompensator> shuntList, ArrayList<EnergyConsumerLoad> loadList){
		
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
										
										
										
										if (terminal2.ConductingEquipment.equals(breaker.ID) && breaker.Open == false) {
										
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
												
												for (Terminal terminal3:terminalList) {
													
													if (terminal3.ConnectivityNode.equals(node1.ID) && !terminal3.equals(terminal1) 
														&& !terminal3.equals(terminal2)) {
														
														for (SyncMachine sync:syncMachList) {
															
															if (sync.ID.equals(terminal3.ConductingEquipment)) {
																busIDs.add(busbar.ID);
																
																break;
															}
														}
														
														for (ShuntCompensator shunt:shuntList) {
															
															if (shunt.ID.equals(terminal3.ConductingEquipment)) {
																busIDs.add(busbar.ID);
																
																break;
															}
														}
														
														for (EnergyConsumerLoad load:loadList) {
															
															if (load.ID.equals(terminal3.ConductingEquipment)) {
																busIDs.add(busbar.ID);
																
																break;
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
			
			if (busIDs.size()==1){
				Topology topo = new Topology("AC Line", line.ID, busIDs.get(0), null);
				topologyElements.add(topo);
				
			}else if (busIDs.isEmpty()) {
				
			} else {			
				Topology topo = new Topology("AC Line", line.ID, busIDs.get(0), busIDs.get(1));
				topologyElements.add(topo);
			}
			
		}
		
		for (PowerTransformer trans:powerTransformerList) {
			
			ArrayList<String> busIDs = new ArrayList<>();
			
			for (PowerTransformerEnd wind:powerTransformerEndList) {
				
				if (trans.ID.equals(wind.transformerID)) {
								
					for (Terminal terminal1:terminalList) {
				
						if (wind.TerminalID.equals(terminal1.ID)) {
											
							for (ConnectivityNode node1:conNodeList) {
						
								if (terminal1.ConnectivityNode.equals(node1.ID)) {
															
									for (Terminal terminal2:terminalList) {
								
										if (node1.ID.equals(terminal2.ConnectivityNode) && !terminal2.equals(terminal1)) { // The ! symbol makes the negative
											
											for (Breaker breaker:breakerList) {
												
												
										
												if (terminal2.ConductingEquipment.equals(breaker.ID) && breaker.Open == false) {
										
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
			
			if (busIDs.size()==1){
				Topology topo = new Topology("Transformer", trans.ID, busIDs.get(0), null);
				topologyElements.add(topo);
				
			}else if (busIDs.isEmpty()) {
				
			} else {
				Topology topo = new Topology("Transformer", trans.ID, busIDs.get(0), busIDs.get(1));
				topologyElements.add(topo);
			}
			
			
		}
		
		return topologyElements;
	}
	
}
