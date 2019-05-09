package assignment1; /////////////

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Breaker {
	
	String ID;
	String name;
	boolean NormallyOpen;
	String equipContID;
	String baseVoltID;
	
	public Breaker(Element breakerEQ, NodeList voltLevel) {
		this.ID = breakerEQ.getAttribute("rdf:ID");
		this.name = breakerEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.NormallyOpen = Boolean.parseBoolean(breakerEQ.getElementsByTagName("cim:Switch.normalOpen").item(0).getTextContent());
		this.equipContID = getAttributesFromChildren(breakerEQ,"cim:Equipment.EquipmentContainer");
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

}
