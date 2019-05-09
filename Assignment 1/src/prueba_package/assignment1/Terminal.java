package assignment1; /////////////

import org.w3c.dom.Element;

public class Terminal {
	String ID;
	Double sequenceNumber;
	String ConductingEquipment;
	String ConnectivityNode;
	
	public Terminal(Element terminalEQ) {
		this.ID = terminalEQ.getAttribute("rdf:ID");
		this.sequenceNumber = Double.parseDouble(terminalEQ.getElementsByTagName("cim:ACDCTerminal.sequenceNumber").item(0).getTextContent());
		this.ConductingEquipment = getAttributesFromChildren(terminalEQ,"cim:Terminal.ConductingEquipment");
		this.ConnectivityNode = getAttributesFromChildren(terminalEQ,"cim:Terminal.ConnectivityNode");
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
