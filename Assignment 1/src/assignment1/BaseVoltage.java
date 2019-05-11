package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseVoltage {
		
	String ID;
	Double nominalValue;
		
	public BaseVoltage(Element baseVoltageEQ) {
		this.ID = baseVoltageEQ.getAttribute("rdf:ID");
		this.nominalValue = Double.parseDouble(baseVoltageEQ.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent());

		}
	
	public static ArrayList<BaseVoltage> getElements(NodeList baseVoltListEQ) {
		 
		ArrayList<BaseVoltage> baseVoltList = new ArrayList<>();
			
			for (int i=0 ; i < baseVoltListEQ.getLength() ; i++) {
				
				Node node = baseVoltListEQ.item(i);
				Element eElementEQ = (Element) node;
				
				BaseVoltage basVolt = new BaseVoltage(eElementEQ);
				
				baseVoltList.add(basVolt);
				
			}
			
			return baseVoltList;
	}
	

}

