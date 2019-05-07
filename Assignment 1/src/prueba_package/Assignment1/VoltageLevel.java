package assignment1; /////////////

import org.w3c.dom.Element;

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

}
