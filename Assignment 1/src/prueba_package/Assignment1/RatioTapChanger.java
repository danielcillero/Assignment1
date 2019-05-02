package tryReader;

import org.w3c.dom.Element;

public class RatioTapChanger {

	String ID;
	String name;
	Double step;
	
	public RatioTapChanger(Element tapChangerEQ) {
		this.ID = tapChangerEQ.getAttribute("rdf:ID");
		this.name = tapChangerEQ.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		this.step = Double.parseDouble(tapChangerEQ.getElementsByTagName("cim:RatioTapChanger.stepVoltageIncrement").item(0).getTextContent());
	}
	

}
