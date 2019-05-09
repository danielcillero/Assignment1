package assignment1; /////////////

import org.w3c.dom.Element;

public class ACLineSegment {

	String ID;
	String equipContID;
	Double R;
	Double X;
	Double bch; // Susceptance
	int prueba;
	
	public ACLineSegment(Element lineSegmentEQ) {
		this.ID = lineSegmentEQ.getAttribute("rdf:ID");
		this.equipContID = getAttributesFromChildren(lineSegmentEQ,"cim:Equipment.EquipmentContainer");
		this.R = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.r").item(0).getTextContent());
		this.X = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.x").item(0).getTextContent());
		this.bch = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.bch").item(0).getTextContent());
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
