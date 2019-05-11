package assignment1; /////////////

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RatioTapChanger {

	String ID;
	String name;
	Double step;
	
	public RatioTapChanger(Element tapChangerEQ) {
		this.ID = tapChangerEQ.getAttribute("rdf:ID");
		this.name = tapChangerEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.step = Double.parseDouble(tapChangerEQ.getElementsByTagName("cim:RatioTapChanger.stepVoltageIncrement").item(0).getTextContent());
	}
	
	public static ArrayList<RatioTapChanger> getElements(NodeList ratioTapChangListEQ) {
		
		ArrayList<RatioTapChanger> ratioTapChangerList = new ArrayList<>();
		
		for (int i=0 ; i < ratioTapChangListEQ.getLength() ; i++) {
			
			Node node = ratioTapChangListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			RatioTapChanger ratioTapChanger = new RatioTapChanger(eElementEQ);
			
			ratioTapChangerList.add(ratioTapChanger);
			
		}
		
		return ratioTapChangerList;
	}

}
