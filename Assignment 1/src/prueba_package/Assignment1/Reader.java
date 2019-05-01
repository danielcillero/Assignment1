package tryReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Reader {
	
	List<NodeList> nodes;
	
	public Reader(File XmlFile, String EqSsh) {
	this.nodes = nodesmaker(XmlFile, EqSsh);
	}
	
	public List<NodeList> nodesmaker (File XmlFile, String EqSsh) {
		
		List<NodeList> nodes = new ArrayList<NodeList>();
		
		try { 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(XmlFile);
		
		
		// normalize CIM XML file
		doc.getDocumentElement().normalize();
		
		if (EqSsh == "EQ") {		
		// Lists of the different main nodes needed
		NodeList baseVoltList = doc.getElementsByTagName("cim:BaseVoltage");
		NodeList subList = doc.getElementsByTagName("cim:Substation"); 
		NodeList voltList = doc.getElementsByTagName("cim:VoltageLevel");
		NodeList genUnitList = doc.getElementsByTagName("cim:GeneratingUnit");
		NodeList syncMachList = doc.getElementsByTagName("cim:SynchronousMachine");
		NodeList regControlList = doc.getElementsByTagName("cim:RegulatingControl");
		NodeList transformerList = doc.getElementsByTagName("cim:PowerTransformer");
		NodeList loadList = doc.getElementsByTagName("cim:EnergyConsumer");
		NodeList transfWindingList = doc.getElementsByTagName("cim:PowerTransformerEnd");
		NodeList breakerList = doc.getElementsByTagName("cim:Breaker");
		NodeList ratioTapChangList = doc.getElementsByTagName("cim:RatioTapChanger");
		
		// Extract all the parameters needed (Check voids in the end)
		// List<NodeList> nodes = new ArrayList<>();
		nodes.add(baseVoltList);
		nodes.add(subList);
		nodes.add(voltList);
		nodes.add(genUnitList);
		nodes.add(syncMachList);
		nodes.add(regControlList);
		nodes.add(transformerList);
		nodes.add(loadList);
		nodes.add(transfWindingList);
		nodes.add(breakerList);
		nodes.add(ratioTapChangList);
		
		} else if (EqSsh == "SSH") {
			// Lists of the different main nodes needed
			NodeList syncMachList = doc.getElementsByTagName("cim:SynchronousMachine");
			NodeList regControlList = doc.getElementsByTagName("cim:RegulatingControl");
			NodeList loadList = doc.getElementsByTagName("cim:EnergyConsumer");
			
			// Extract all the parameters needed (Check voids in the end)
			// List<NodeList> nodes = new ArrayList<>();
			nodes.add(syncMachList);
			nodes.add(regControlList);
			nodes.add(loadList);
		}
				
		} catch(Exception e){
			e.printStackTrace();
		} 

				
		return nodes;
	
	}

}
