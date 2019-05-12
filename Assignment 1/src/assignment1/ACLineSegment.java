package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ACLineSegment {

	String ID;
	String name;
	String equipContID;
	Double R;
	Double X;
	Double bch; // Susceptance
	Double gch;
	Double length;
	Double Zbase;
	Double Rpu;
	Double Xpu;
	Double bchpu; 
	Double gchpu;
	
	public ACLineSegment(Element lineSegmentEQ, BaseVoltage base, Double maxS) {
		this.ID = lineSegmentEQ.getAttribute("rdf:ID");
		this.name = lineSegmentEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.equipContID = getAttributesFromChildren(lineSegmentEQ,"cim:Equipment.EquipmentContainer");
		this.R = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.r").item(0).getTextContent());
		this.X = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.x").item(0).getTextContent());
		this.bch = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.bch").item(0).getTextContent());
		this.gch = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:ACLineSegment.gch").item(0).getTextContent());
		this.length = Double.parseDouble(lineSegmentEQ.getElementsByTagName("cim:Conductor.length").item(0).getTextContent());
		this.Zbase = base.nominalValue * base.nominalValue / maxS;
		this.Rpu = R/Zbase;
		this.Xpu = X/Zbase;
		this.bchpu = bch*Zbase;
		this.gchpu = gch*Zbase;
		
	}	
	
	public static String getAttributesFromChildren (Element eElement, String childNode) {
		
		for (int i = 0; i < eElement.getChildNodes().getLength(); i++) { // "for" with all the children nodes of the main node
			
			if (eElement.getChildNodes().item(i).getNodeName() == childNode) { 
				
				Element outputElement = (Element) eElement.getChildNodes().item(i); // New element with this node
				
				return outputElement.getAttribute("rdf:resource").substring(1); // Without # because of the substring(1), it deletes the first character
				
			}
			
		}
		return childNode;		
	}
	
	public static ArrayList<ACLineSegment> getElements(NodeList lineSegmentACListEQ, ArrayList<BaseVoltage> baseVoltList, Double maxS) {
		
		ArrayList<ACLineSegment> lineSegmentList = new ArrayList<>();
		
		for (int i=0 ; i < lineSegmentACListEQ.getLength() ; i++) {
			
			Node node = lineSegmentACListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = getAttributesFromChildren(eElementEQ,"cim:ConductingEquipment.BaseVoltage");
			
			for (BaseVoltage base:baseVoltList) {
								
				if (rdfID.equals(base.ID)) {
					
					ACLineSegment line = new ACLineSegment(eElementEQ,base,maxS);
					
					lineSegmentList.add(line);
					
				}
			
			}
						
		}
		
		return lineSegmentList;
		
	}

}
