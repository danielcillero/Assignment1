package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EnergyConsumerLoad {
	
	String ID;
	String name;
	Double P;
	Double Q;
	String equipContID;
	String baseVoltID;
	
	public EnergyConsumerLoad(Element energyConsumerLoadEQ, Element energyConsumerLoadSSH, NodeList voltLevel) {
		this.ID = energyConsumerLoadEQ.getAttribute("rdf:ID");
		this.name = energyConsumerLoadEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.P = Double.parseDouble(energyConsumerLoadSSH.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent());
		this.Q = Double.parseDouble(energyConsumerLoadSSH.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent());
		this.equipContID = getAttributesFromChildren(energyConsumerLoadEQ,"cim:Equipment.EquipmentContainer");
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
	
	public static ArrayList<EnergyConsumerLoad> getElements(NodeList loadListEQ, NodeList loadListSSH, NodeList voltListEQ) {
		
		ArrayList<EnergyConsumerLoad> loadList = new ArrayList<>();
		
		for (int i=0 ; i < loadListEQ.getLength() ; i++) {
			
			Node node = loadListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < loadListSSH.getLength() ; j++) {
				Node nodeEQ = loadListSSH.item(j);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
										
					EnergyConsumerLoad load = new EnergyConsumerLoad(eElementEQ,eElementSSH,voltListEQ);
					
					loadList.add(load);
					
				}
				
			}
		}
		
		return loadList;
	}
}
