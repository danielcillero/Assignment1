package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SyncMachine {
	
	String ID;
	String name;
	Double ratedS;
	Double P;
	Double Q;
	String genUnitID;
	String regControlID;
	String equipContID;
	String baseVoltID;
	
	public SyncMachine(Element syncMachEQ, Element syncMachSSH, NodeList voltLevel) {
		this.ID = syncMachEQ.getAttribute("rdf:ID");
		this.name = syncMachEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.ratedS = Double.parseDouble(syncMachEQ.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent());
		this.P = Double.parseDouble(syncMachSSH.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent());
		this.Q = Double.parseDouble(syncMachSSH.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent());
		this.genUnitID = getAttributesFromChildren(syncMachEQ,"cim:RotatingMachine.GeneratingUnit");
		this.regControlID = getAttributesFromChildren(syncMachEQ,"cim:RegulatingCondEq.RegulatingControl");
		this.equipContID = getAttributesFromChildren(syncMachEQ,"cim:Equipment.EquipmentContainer");
		this.baseVoltID = rdfBaseVolt(voltLevel,equipContID);
	}
	
	
	
	public String getAttributesFromChildren (Element eElement, String childNode) {
		
		for (int i = 0; i < eElement.getChildNodes().getLength(); i++) { // "for" with all the children nodes of the main node
			
			if (eElement.getChildNodes().item(i).getNodeName() == childNode) { 
				
				Element outputElement = (Element) eElement.getChildNodes().item(i); // New element with this node
				
				return outputElement.getAttribute("rdf:resource").substring(1); // Without # because of the substring(1), it deletes the first character
				
			}
			
		}
		return childNode;
		
	}
	
	public String rdfBaseVolt (NodeList voltageLevel, String parentNode) {
		
		String rdfBaseVolt = "There is no coincidence";
		
		for (int i = 0; i < voltageLevel.getLength(); i++) { ////////////////////
			Node voltNode = voltageLevel.item(i); //////////////////////
		
			Element voltElement = (Element) voltNode;
			
			String rdfIDvolt = voltElement.getAttribute("rdf:ID");
			
			if (rdfIDvolt.equals(parentNode)) {
				
				for (int j = 0; j < voltElement.getChildNodes().getLength(); j++) { // "for" with all the children nodes of the main node
					
					if (voltElement.getChildNodes().item(j).getNodeName() == "cim:VoltageLevel.BaseVoltage") { 
						
						Element outputElement = (Element) voltElement.getChildNodes().item(j); // New element with this node
												
						rdfBaseVolt = outputElement.getAttribute("rdf:resource").substring(1); // Without # because of the substring(1), it deletes the first character
						
					}
					
				}
								
			}
			
		}
		return rdfBaseVolt;
	}
	
	public static ArrayList<SyncMachine> getElements(NodeList syncMachListEQ, NodeList syncMachListSSH, NodeList voltListEQ) {
		
		ArrayList<SyncMachine> syncMachList = new ArrayList<>();
		
		for (int i=0 ; i < syncMachListEQ.getLength() ; i++) {
			
			Node node = syncMachListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < syncMachListSSH.getLength() ; j++) {
				Node nodeEQ = syncMachListSSH.item(j);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
					
					SyncMachine symach = new SyncMachine(eElementEQ,eElementSSH,voltListEQ);
					
					syncMachList.add(symach);
					
				}
				
			}
		}
		
		return syncMachList;
	}
}
	
	
