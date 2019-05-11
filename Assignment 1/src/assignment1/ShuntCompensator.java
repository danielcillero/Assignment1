package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ShuntCompensator {
	
	String ID;
	String equipContID;
	
	public ShuntCompensator(Element shuntCompensaatorEQ) {
		this.ID = shuntCompensaatorEQ.getAttribute("rdf:ID");
		this.equipContID = getAttributesFromChildren(shuntCompensaatorEQ,"cim:Equipment.EquipmentContainer");
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
	
	public static ArrayList<ShuntCompensator> getElements (NodeList shuntCompensatorListEQ) {
		
		ArrayList<ShuntCompensator> shuntCompensatorList = new ArrayList<>();
		
		for (int i=0 ; i < shuntCompensatorListEQ.getLength() ; i++) {
			
			Node node = shuntCompensatorListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			ShuntCompensator shunt = new ShuntCompensator(eElementEQ);
			
			shuntCompensatorList.add(shunt);
			
		}
		
		return shuntCompensatorList;
	}

}
