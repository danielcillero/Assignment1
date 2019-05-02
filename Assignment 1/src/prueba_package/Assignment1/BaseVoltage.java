
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseVoltage {
		
	String ID;
	Double nominalValue;
		
	public BaseVoltage(Element baseVoltageEQ) {
		this.ID = baseVoltageEQ.getAttribute("rdf:ID");
		this.nominalValue = baseVoltageEQ.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent();

		}
}

