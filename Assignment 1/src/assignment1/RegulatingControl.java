package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RegulatingControl {
	
	String ID;
	String name;
	Double targetValue;
	
	public RegulatingControl(Element regulatingControlEQ, Element regulatingControlSSH) {
		this.ID = regulatingControlEQ.getAttribute("rdf:ID");
		this.name = regulatingControlEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.targetValue = Double.parseDouble(regulatingControlSSH.getElementsByTagName("cim:RegulatingControl.targetValue").item(0).getTextContent());
	}
	
	public static ArrayList<RegulatingControl> getElements(NodeList regControlListEQ, NodeList regControlListSSH) {
		
		ArrayList<RegulatingControl> regulControlList = new ArrayList<>();
		
		for (int i=0 ; i < regControlListEQ.getLength() ; i++) {
			
			Node node = regControlListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < regControlListSSH.getLength() ; j++) {
				Node nodeEQ = regControlListSSH.item(i);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
					
					//System.out.println(rdfID + " is equal to " + rdfSSH); // For testing
										
					RegulatingControl regControl = new RegulatingControl(eElementEQ,eElementSSH);
					
					regulControlList.add(regControl);
					
				}
				
			}
		}
		
		return regulControlList;
	}

}
