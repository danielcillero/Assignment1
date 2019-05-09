package assignment1; /////////////

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PowerTransformerEnd {
	
	String ID;
	String name;
	Double R;
	Double X;
	String transformerID;
	String baseVoltID;
	String TerminalID;
	
	public PowerTransformerEnd(Element powerTransformerEndEQ) {
		this.ID = powerTransformerEndEQ.getAttribute("rdf:ID");
		this.name = powerTransformerEndEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.R = Double.parseDouble(powerTransformerEndEQ.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
		this.X = Double.parseDouble(powerTransformerEndEQ.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
		this.transformerID = getAttributesFromChildren(powerTransformerEndEQ,"cim:PowerTransformerEnd.PowerTransformer");
		this.baseVoltID = getAttributesFromChildren(powerTransformerEndEQ,"cim:TransformerEnd.BaseVoltage");
		this.TerminalID = getAttributesFromChildren(powerTransformerEndEQ,"cim:TransformerEnd.Terminal");
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
