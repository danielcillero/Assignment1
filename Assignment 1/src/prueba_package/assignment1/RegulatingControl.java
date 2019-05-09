package assignment1; /////////////

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

}
