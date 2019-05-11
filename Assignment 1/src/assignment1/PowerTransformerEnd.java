package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PowerTransformerEnd {
	
	String ID;
	String name;
	Double R;
	Double X;
	Double bch;
	Double gch;
	String transformerID;
	String baseVoltID;
	String TerminalID;
	Double Zbase;
	Double Rpu;
	Double Xpu;
	Double bchpu; 
	Double gchpu;
	
	public PowerTransformerEnd(Element powerTransformerEndEQ, BaseVoltage base, Double maxS) {
		this.ID = powerTransformerEndEQ.getAttribute("rdf:ID");
		this.name = powerTransformerEndEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.R = Double.parseDouble(powerTransformerEndEQ.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
		this.X = Double.parseDouble(powerTransformerEndEQ.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
		this.bch = Double.parseDouble(powerTransformerEndEQ.getElementsByTagName("cim:PowerTransformerEnd.b").item(0).getTextContent());
		this.gch = Double.parseDouble(powerTransformerEndEQ.getElementsByTagName("cim:PowerTransformerEnd.g").item(0).getTextContent());
		this.transformerID = getAttributesFromChildren(powerTransformerEndEQ,"cim:PowerTransformerEnd.PowerTransformer");
		this.baseVoltID = getAttributesFromChildren(powerTransformerEndEQ,"cim:TransformerEnd.BaseVoltage");
		this.TerminalID = getAttributesFromChildren(powerTransformerEndEQ,"cim:TransformerEnd.Terminal");
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
	
	public static ArrayList<PowerTransformerEnd> getElements(NodeList transfWindingListEQ, ArrayList<BaseVoltage> baseVoltList, Double maxS) {
		
		ArrayList<PowerTransformerEnd> powerTransformerEndList = new ArrayList<>();
		
		for (int i=0 ; i < transfWindingListEQ.getLength() ; i++) {
			
			Node node = transfWindingListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = getAttributesFromChildren(eElementEQ,"cim:TransformerEnd.BaseVoltage");
			
			for (BaseVoltage base:baseVoltList) {
								
				if (rdfID.equals(base.ID)) {
					
					PowerTransformerEnd winding = new PowerTransformerEnd(eElementEQ,base,maxS);
					
					powerTransformerEndList.add(winding);
					
				}
			
			}
						
		}
		
		return powerTransformerEndList;
	}
	
}
