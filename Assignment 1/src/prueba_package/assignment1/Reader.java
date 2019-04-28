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

public class Reader {
	
	File XmlFile;
	
	public Reader(File XmlFile) {
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
		
		
				
		} catch(Exception e){
			e.printStackTrace();
		} 

				
		return nodes;
	
	}
		

	public List<ArrayList<String>> baseVoltage (NodeList baseVoltList){
		// Create lists for the parameters needed
		// ArrayList<String> baseVolt = new ArrayList<String>();
		ArrayList<String> baseVoltID = new ArrayList<String>();
		ArrayList<String> baseVoltNomValue = new ArrayList<String>();
		
		for (int i = 0; i < baseVoltList.getLength(); i++) {
			Node node = baseVoltList.item(i);
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String nominalValue = eElement.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent();		
		
			// Add data obtained to a list			
			baseVoltID.add(rdfID);
			baseVoltNomValue.add(nominalValue);
			
		}
		
		List<ArrayList<String>> baseVoltage = new ArrayList<>();
		baseVoltage.add(baseVoltID);
		baseVoltage.add(baseVoltNomValue);
		
		return baseVoltage;
	}
	
	public List<ArrayList<String>> substation (NodeList subList){
		
		// Create lists for the parameters needed
		ArrayList<String> subID = new ArrayList<String>();
		ArrayList<String> subName = new ArrayList<String>();
		ArrayList<String> subRegion = new ArrayList<String>();
				
		for (int i = 0; i < subList.getLength(); i++) {
			Node node = subList.item(i);
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();		
			String region = getAttributesFromChildren(eElement,"cim:Substation.Region");
		
			// Add data obtained to a list
			subID.add(rdfID);
			subName.add(name);
			subRegion.add(region);
		}
		
		List<ArrayList<String>> substation = new ArrayList<>();
		substation.add(subID);
		substation.add(subName);
		substation.add(subRegion);
		
		return substation;
		
	}
	
	public List<ArrayList<String>> voltageLevel (NodeList voltList){
		
		// Create lists for the parameters needed
		ArrayList<String> voltID = new ArrayList<String>();
		ArrayList<String> voltName = new ArrayList<String>();
		ArrayList<String> voltSub = new ArrayList<String>();
		ArrayList<String> voltBaseVolt = new ArrayList<String>();
				
		for (int i = 0; i < voltList.getLength(); i++) {
			Node node = voltList.item(i);
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();		
			String voltSubID = getAttributesFromChildren(eElement,"cim:VoltageLevel.Substation");
			String voltBaseVoltID = getAttributesFromChildren(eElement,"cim:VoltageLevel.BaseVoltage");
		
			// Add data obtained to a list
			voltID.add(rdfID);
			voltName.add(name);
			voltSub.add(voltSubID);
			voltBaseVolt.add(voltBaseVoltID);
		}
		
		List<ArrayList<String>> voltageLevel = new ArrayList<>();
		voltageLevel.add(voltID);
		voltageLevel.add(voltName);
		voltageLevel.add(voltSub);
		voltageLevel.add(voltBaseVolt);
		
		return voltageLevel;
		
	}

	public List<ArrayList<String>> generationUnit (NodeList genUnitList){
		
		// Create lists for the parameters needed
		ArrayList<String> genUnitID = new ArrayList<String>();
		ArrayList<String> genUnitName = new ArrayList<String>();
		ArrayList<String> genUnitMaxP = new ArrayList<String>();
		ArrayList<String> genUnitMinP = new ArrayList<String>();
		ArrayList<String> genUnitEquipCont = new ArrayList<String>();
				
		for (int i = 0; i < genUnitList.getLength(); i++) { ////////////////////
			Node node = genUnitList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String maxP = eElement.getElementsByTagName("cim:GeneratingUnit.maxOperatingP").item(0).getTextContent();
			String minP = eElement.getElementsByTagName("cim:GeneratingUnit.minOperatingP").item(0).getTextContent();
			String genUnitEquipContID = getAttributesFromChildren(eElement,"cim:Equipment.EquipmentContainer");
		
			// Add data obtained to a list
			genUnitID.add(rdfID);
			genUnitName.add(name);
			genUnitMaxP.add(maxP);
			genUnitMinP.add(minP);
			genUnitEquipCont.add(genUnitEquipContID);
		}
		
		List<ArrayList<String>> generationUnit = new ArrayList<>();
		generationUnit.add(genUnitID);
		generationUnit.add(genUnitName);
		generationUnit.add(genUnitMaxP);
		generationUnit.add(genUnitMinP);
		generationUnit.add(genUnitEquipCont);
		
		return generationUnit;
	}

	public List<ArrayList<String>> syncMach (NodeList syncMachList){
		
		// Create lists for the parameters needed
		ArrayList<String> syncMachID = new ArrayList<String>();
		ArrayList<String> syncMachName = new ArrayList<String>();
		ArrayList<String> syncMachRatedS = new ArrayList<String>();
		ArrayList<String> syncMachGenUnit = new ArrayList<String>();
		ArrayList<String> syncMachRegControl = new ArrayList<String>();
		ArrayList<String> syncMachEquipCont = new ArrayList<String>();
		ArrayList<String> syncMachBaseVolt = new ArrayList<String>();
				
		for (int i = 0; i < syncMachList.getLength(); i++) { ////////////////////
			Node node = syncMachList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String ratedS = eElement.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent();
			String genUnitID = getAttributesFromChildren(eElement,"cim:RotatingMachine.GeneratingUnit");
			String regControlID = getAttributesFromChildren(eElement,"cim:RegulatingCondEq.RegulatingControl");
			String equipContID = getAttributesFromChildren(eElement,"cim:Equipment.EquipmentContainer");
			String baseVoltID = getAttributesFromChildren(eElement,"cim:VoltageLevel.BaseVoltage");
		
			// Add data obtained to a list
			syncMachID.add(rdfID);
			syncMachName.add(name);
			syncMachRatedS.add(ratedS);
			syncMachGenUnit.add(genUnitID);
			syncMachRegControl.add(regControlID);
			syncMachEquipCont.add(equipContID);
			syncMachBaseVolt.add(baseVoltID);
		}
		
		List<ArrayList<String>> syncMach = new ArrayList<>();
		syncMach.add(syncMachID);
		syncMach.add(syncMachName);
		syncMach.add(syncMachRatedS);
		syncMach.add(syncMachGenUnit);
		syncMach.add(syncMachRegControl);
		syncMach.add(syncMachEquipCont);
		syncMach.add(syncMachBaseVolt);
		
		return syncMach;
	}

	public List<ArrayList<String>> regControl (NodeList regControlList){
		
		// Create lists for the parameters needed
		ArrayList<String> regControlID = new ArrayList<String>();
		ArrayList<String> regControlName = new ArrayList<String>();
				
		for (int i = 0; i < regControlList.getLength(); i++) { ////////////////////
			Node node = regControlList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		
			// Add data obtained to a list
			regControlID.add(rdfID);
			regControlName.add(name);

		}
		
		List<ArrayList<String>> regControl = new ArrayList<>();
		regControl.add(regControlID);
		regControl.add(regControlName);
		
		return regControl;
	}	
	
	public List<ArrayList<String>> transformer (NodeList transformerList){
		
		// Create lists for the parameters needed
		ArrayList<String> transformerID = new ArrayList<String>();
		ArrayList<String> transformerName = new ArrayList<String>();
		ArrayList<String> transformerEquipCont = new ArrayList<String>();
				
		for (int i = 0; i < transformerList.getLength(); i++) { ////////////////////
			Node node = transformerList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String equipContID = getAttributesFromChildren(eElement,"cim:Equipment.EquipmentContainer");
		
			// Add data obtained to a list
			transformerID.add(rdfID);
			transformerName.add(name);
			transformerEquipCont.add(equipContID);
		}
		
		List<ArrayList<String>> transformer = new ArrayList<>();
		transformer.add(transformerID);
		transformer.add(transformerName);
		transformer.add(transformerEquipCont);
		
		return transformer;
	}
	
	public List<ArrayList<String>> load (NodeList loadList){
		
		// Create lists for the parameters needed
		ArrayList<String> loadID = new ArrayList<String>();
		ArrayList<String> loadName = new ArrayList<String>();
		ArrayList<String> loadEquipCont = new ArrayList<String>();
		ArrayList<String> loadBaseVolt = new ArrayList<String>();
				
		for (int i = 0; i < loadList.getLength(); i++) { ////////////////////
			Node node = loadList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String equipContID = getAttributesFromChildren(eElement,"cim:Equipment.EquipmentContainer");
			String baseVoltID = getAttributesFromChildren(eElement,"cim:VoltageLevel.BaseVoltage");
		
			// Add data obtained to a list
			loadID.add(rdfID);
			loadName.add(name);
			loadEquipCont.add(equipContID);
			loadBaseVolt.add(baseVoltID);
		}
		
		List<ArrayList<String>> load = new ArrayList<>();
		load.add(loadID);
		load.add(loadName);
		load.add(loadEquipCont);
		load.add(loadBaseVolt);
		
		return load;
	}
	
	public List<ArrayList<String>> transfWinding (NodeList transfWindingList){
		
		// Create lists for the parameters needed
		ArrayList<String> transfWindingID = new ArrayList<String>();
		ArrayList<String> transfWindingName = new ArrayList<String>();
		ArrayList<String> transfWindingR = new ArrayList<String>();
		ArrayList<String> transfWindingX = new ArrayList<String>();
		ArrayList<String> transfWindingTransf = new ArrayList<String>();
		ArrayList<String> transfWindingBaseVolt = new ArrayList<String>();
				
		for (int i = 0; i < transfWindingList.getLength(); i++) { ////////////////////
			Node node = transfWindingList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String res = eElement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent();
			String xind = eElement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent();
			String transformerID = getAttributesFromChildren(eElement,"cim:PowerTransformerEnd.PowerTransformer");
			String baseVoltID = getAttributesFromChildren(eElement,"cim:VoltageLevel.BaseVoltage");
		
			// Add data obtained to a list
			transfWindingID.add(rdfID);
			transfWindingName.add(name);
			transfWindingR.add(res);
			transfWindingX.add(xind);
			transfWindingTransf.add(transformerID);
			transfWindingBaseVolt.add(baseVoltID);
		}
		
		List<ArrayList<String>> transfWinding = new ArrayList<>();
		transfWinding.add(transfWindingID);
		transfWinding.add(transfWindingName);
		transfWinding.add(transfWindingR);
		transfWinding.add(transfWindingX);
		transfWinding.add(transfWindingTransf);
		transfWinding.add(transfWindingBaseVolt);
		
		return transfWinding;
	}
	
	public List<ArrayList<String>> breaker (NodeList breakerList){
		
		// Create lists for the parameters needed
		ArrayList<String> breakerID = new ArrayList<String>();
		ArrayList<String> breakerName = new ArrayList<String>();
		ArrayList<String> breakerOpen = new ArrayList<String>();
		ArrayList<String> breakerEquipCont = new ArrayList<String>();
		ArrayList<String> breakerBaseVolt = new ArrayList<String>();
				
		for (int i = 0; i < breakerList.getLength(); i++) { ////////////////////
			Node node = breakerList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String open = eElement.getElementsByTagName("cim:Switch.normalOpen").item(0).getTextContent();
			String equipContID = getAttributesFromChildren(eElement,"cim:Equipment.EquipmentContainer");
			String baseVoltID = getAttributesFromChildren(eElement,"cim:VoltageLevel.BaseVoltage");
		
			// Add data obtained to a list
			breakerID.add(rdfID);
			breakerName.add(name);
			breakerOpen.add(open);
			breakerEquipCont.add(equipContID);
			breakerBaseVolt.add(baseVoltID);
		}
		
		List<ArrayList<String>> breaker = new ArrayList<>();
		breaker.add(breakerID);
		breaker.add(breakerName);
		breaker.add(breakerOpen);
		breaker.add(breakerEquipCont);
		breaker.add(breakerBaseVolt);
		
		return breaker;
	}	
	
	public List<ArrayList<String>> ratioTapChang (NodeList ratioTapChangList){
		
		// Create lists for the parameters needed
		ArrayList<String> ratioTapChangID = new ArrayList<String>();
		ArrayList<String> ratioTapChangName = new ArrayList<String>();
		ArrayList<String> ratioTapChangStep = new ArrayList<String>();
				
		for (int i = 0; i < ratioTapChangList.getLength(); i++) { ////////////////////
			Node node = ratioTapChangList.item(i); //////////////////////
		
			Element eElement = (Element) node;
		
			// Data needed
			String rdfID = eElement.getAttribute("rdf:ID");
			String name = eElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String step = eElement.getElementsByTagName("cim:RatioTapChanger.stepVoltageIncrement").item(0).getTextContent();
		
			// Add data obtained to a list
			ratioTapChangID.add(rdfID);
			ratioTapChangName.add(name);
			ratioTapChangStep.add(step);
		}
		
		List<ArrayList<String>> ratioTapChang = new ArrayList<>();
		ratioTapChang.add(ratioTapChangID);
		ratioTapChang.add(ratioTapChangName);
		ratioTapChang.add(ratioTapChangStep);
		
		return ratioTapChang;
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

	

}
	


