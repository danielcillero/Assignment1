import org.w3c.dom.Element;

public class PowerTransformer {

	String ID;
	String name;
	String equipContID;
	
	public PowerTransformer(Element powerTransformerEQ) {
		this.ID = powerTransformerEQ.getAttribute("rdf:ID");
		this.name = powerTransformerEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.equipContID = getAttributesFromChildren(powerTransformerEQ,"cim:Equipment.EquipmentContainer");
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
