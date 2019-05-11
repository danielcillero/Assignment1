package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VoltageLevel {

	String ID;
	String name;
	String substationID;
	String baseVoltageID;
	
	public VoltageLevel(Element voltageLevelEQ) {
		this.ID = voltageLevelEQ.getAttribute("rdf:ID");
		this.name = voltageLevelEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.substationID = getAttributesFromChildren(voltageLevelEQ,"cim:VoltageLevel.Substation");
		this.baseVoltageID = getAttributesFromChildren(voltageLevelEQ,"cim:VoltageLevel.BaseVoltage");
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
	
	public static ArrayList<VoltageLevel> getElements(NodeList voltListEQ) {
		
		ArrayList<VoltageLevel> voltLevelList = new ArrayList<>();
		
		for (int i=0 ; i < voltListEQ.getLength() ; i++) {
			
			Node node = voltListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			VoltageLevel voltLevel = new VoltageLevel(eElementEQ);
			
			voltLevelList.add(voltLevel);
			
		}
		
		return voltLevelList;
	}

}
