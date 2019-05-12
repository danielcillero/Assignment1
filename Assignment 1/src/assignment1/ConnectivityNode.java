package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConnectivityNode {

	String ID;
	String name;
	String conContID;
	String baseVoltID;
	
	public ConnectivityNode(Element conNodeEQ, NodeList voltLevel) {
		this.ID = conNodeEQ.getAttribute("rdf:ID");
		this.name = conNodeEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.conContID = getAttributesFromChildren(conNodeEQ,"cim:ConnectivityNode.ConnectivityNodeContainer");
		this.baseVoltID = rdfBaseVolt(voltLevel,conContID);
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
	
	public String rdfBaseVolt (NodeList voltageLevel, String parentNode) {
		
		String rdfBaseVolt = "There is no coincidence";
		
		for (int i = 0; i < voltageLevel.getLength(); i++) { ////////////////////
			Node voltNode = voltageLevel.item(i); //////////////////////
		
			Element voltElement = (Element) voltNode;
			
			String rdfIDvolt = voltElement.getAttribute("rdf:ID");
			
			if (rdfIDvolt.equals(parentNode)) {
				
				for (int j = 0; j < voltElement.getChildNodes().getLength(); j++) { // "for" with all the children nodes of the main node
					
					if (voltElement.getChildNodes().item(j).getNodeName() == "cim:VoltageLevel.BaseVoltage") { 
						
						Element outputElement = (Element) voltElement.getChildNodes().item(j); // New element with this node
												
						rdfBaseVolt = outputElement.getAttribute("rdf:resource").substring(1); // Without # because of the substring(1), it deletes the first character
						
					}
					
				}
								
			}
			
		}
		return rdfBaseVolt;
	}
	
	public static ArrayList<ConnectivityNode> getElements(NodeList connectivityNodeListEQ, NodeList voltListEQ) {
		
		ArrayList<ConnectivityNode> conNodeList = new ArrayList<>();
		
		for (int i=0 ; i < connectivityNodeListEQ.getLength() ; i++) {
			
			Node node = connectivityNodeListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			ConnectivityNode conNode = new ConnectivityNode(eElementEQ,voltListEQ);
			
			conNodeList.add(conNode);
			
		}
		
		return conNodeList;
	}
	
}
