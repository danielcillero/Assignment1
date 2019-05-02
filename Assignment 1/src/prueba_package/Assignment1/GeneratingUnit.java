import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GeneratingUnit {

	String ID;
	String name;
	Double maxP;
	Double minP;
	String equipContID;
	
	public GeneratingUnit(Element generatingUnitEQ) {
		this.ID = generatingUnitEQ.getAttribute("rdf:ID");
		this.name = generatingUnitEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.maxP = Double.parseDouble(generatingUnitEQ.getElementsByTagName("cim:GeneratingUnit.maxOperatingP").item(0).getTextContent());
		this.minP = Double.parseDouble(generatingUnitEQ.getElementsByTagName("cim:GeneratingUnit.minOperatingP").item(0).getTextContent());
		this.equipContID = getAttributesFromChildren(generatingUnitEQ,"cim:Equipment.EquipmentContainer");
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
