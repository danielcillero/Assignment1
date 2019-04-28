package assignment1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReaderSSH {
	
	File XmlFile;

	public ReaderSSH(File XmlFile) {	
		this.XmlFile = XmlFile;
	}

	public List<NodeList> nodesmaker (File XmlFile) {
		
		List<NodeList> nodes = new ArrayList<NodeList>();
		
		try { 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(XmlFile);
		
		
		// normalize CIM XML file
		doc.getDocumentElement().normalize();
		
				
		// Lists of the different main nodes needed
		NodeList syncMachListSSH = doc.getElementsByTagName("cim:SynchronousMachine");
		NodeList regControlListSSH = doc.getElementsByTagName("cim:RegulatingControl");
		NodeList loadListSSH = doc.getElementsByTagName("cim:EnergyConsumer");
		
		// Extract all the parameters needed (Check voids in the end)
		// List<NodeList> nodes = new ArrayList<>();
		nodes.add(syncMachListSSH);
		nodes.add(regControlListSSH);
		nodes.add(loadListSSH);
		
		
				
		} catch(Exception e){
			e.printStackTrace();
		} 

				
		return nodes;
	
	}
	
	public List<ArrayList<String>> syncMachPQ (NodeList syncMachListSSH, ArrayList<String> syncMachID){
				
		// Create lists for the parameters needed
		ArrayList<String> syncMachP = new ArrayList<String>();
		ArrayList<String> syncMachQ = new ArrayList<String>();
				
		for (int i = 0; i < syncMachListSSH.getLength(); i++) { ////////////////////
			Node node = syncMachListSSH.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfabout = eElement.getAttribute("rdf:about");
			String p = eElement.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent();
			String q = eElement.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent();
		
			// Add data obtained to a list
			
			for (int j = 0; j < syncMachListSSH.getLength(); j++) {
				if (rdfabout == syncMachID.get(j)) {
					syncMachP.add(p);
					syncMachQ.add(q);
				}
			}
					
		}
		List<ArrayList<String>> syncMachPQ = new ArrayList<>(); 
		syncMachPQ.add(syncMachP);
		syncMachPQ.add(syncMachQ);
		
		return syncMachPQ;
	}
	
	public ArrayList<Double> regControlTargetValue (NodeList regControlListSSH, ArrayList<String> regControlID){
		
		// Create lists for the parameters needed
		ArrayList<Double> regControlTargetValue = new ArrayList<Double>();
				
		for (int i = 0; i < regControlListSSH.getLength(); i++) { ////////////////////
			Node node = regControlListSSH.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfabout = eElement.getAttribute("rdf:about");
			String targetValue = eElement.getElementsByTagName("cim:RegulatingControl.targetValue").item(0).getTextContent();
			Double targVal = Double.parseDouble(targetValue);
		
			// Add data obtained to a list
			
			for (int j = 0; j < regControlListSSH.getLength(); j++) {
				if (rdfabout == regControlID.get(j)) {
					regControlTargetValue.add(targVal);
				}
			}
					
		}
		
		return regControlTargetValue;
	}
	
	
	
	public List<ArrayList<String>> loadPQ (NodeList loadListSSH, ArrayList<String> loadID){
		
		// Create lists for the parameters needed
		ArrayList<String> loadP = new ArrayList<String>();
		ArrayList<String> loadQ = new ArrayList<String>();
				
		for (int i = 0; i < loadListSSH.getLength(); i++) { ////////////////////
			Node node = loadListSSH.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfabout = eElement.getAttribute("rdf:about");
			String p = eElement.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent();
			String q = eElement.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent();
		
			// Add data obtained to a list
			
			for (int j = 0; j < loadListSSH.getLength(); j++) {
				if (rdfabout == loadID.get(j)) {
					loadP.add(p);
					loadQ.add(q);
				}
			}
					
		}
		List<ArrayList<String>> loadPQ = new ArrayList<>(); 
		loadPQ.add(loadP);
		loadPQ.add(loadQ);
		
		return loadPQ;
	}	
		
}
