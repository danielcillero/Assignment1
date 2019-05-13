package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Breaker {
	
	String ID;
	String name;
	boolean Open;
	String equipContID;
	String baseVoltID;
	
	public Breaker(Element breakerEQ, Element breakerSSH, NodeList voltLevel) {
		this.ID = breakerEQ.getAttribute("rdf:ID");
		this.name = breakerEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.Open = Boolean.parseBoolean(breakerSSH.getElementsByTagName("cim:Switch.open").item(0).getTextContent());
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
	
	public static ArrayList<Breaker> getElements(NodeList breakerListEQ, NodeList breakerListSSH, NodeList voltListEQ) {
		
		ArrayList<Breaker> breakerList = new ArrayList<>();
		
		for (int i=0 ; i < breakerListEQ.getLength() ; i++) {
			
			Node node = breakerListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < breakerListSSH.getLength() ; j++) {
				Node nodeSSH = breakerListSSH.item(j);
				Element eElementSSH = (Element) nodeSSH;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
										
					Breaker breaker = new Breaker(eElementEQ,eElementSSH,voltListEQ);
					
					breakerList.add(breaker);
					
				}
				
			}

		}
		
		return breakerList;
	}

}
