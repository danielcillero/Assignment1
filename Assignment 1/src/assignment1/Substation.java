package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Substation {
		
	String ID;
	String name;
	String regionID;
	
	public Substation(Element substationEQ) {
		this.ID = substationEQ.getAttribute("rdf:ID");
		this.name = substationEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.regionID = getAttributesFromChildren(substationEQ,"cim:Substation.Region");
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
	
	public static ArrayList<Substation> getElements(NodeList subListEQ) {
		
		ArrayList<Substation> subList = new ArrayList<>();
		
		for (int i=0 ; i < subListEQ.getLength() ; i++) {
			
			Node node = subListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			Substation substation = new Substation(eElementEQ);
			
			subList.add(substation);
			
		}
		
		return subList;
	}

}
