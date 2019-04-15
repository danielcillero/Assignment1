import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Reader {
	
	public static void ReaderEQ(File XmlFile) {
	try {
		// File XmlFile = new File("opencim3sub.xml");
		
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
		extractBaseVoltage (baseVoltList);
		extractSubstation (subList);
		extractVoltageLevel (voltList);
		extractGenerationUnit (genUnitList);
		extractSyncMach (syncMachList);
		extractRegControl (regControlList);
		extractTransformer (transformerList);
		extractLoad (loadList);
		extractTransfWinding (transfWindingList);
		extractBreaker (breakerList);
		extractRatioTapChang (ratioTapChangList);
		
		
	}
	
	catch(Exception e){
		e.printStackTrace();
	}
	
	}

	public static void extractBaseVoltage (NodeList baseVoltList){
		// Create lists for the parameters needed
		ArrayList<String> baseVoltID = new ArrayList<String>;
		ArrayList<String> baseVoltNomValue = new ArrayList<String>;
		
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
	}
	
	public static void extractSubstation (NodeList subList){
		
		// Create lists for the parameters needed
		ArrayList<String> subID = new ArrayList<String>;
		ArrayList<String> subName = new ArrayList<String>;
		ArrayList<String> subRegion = new ArrayList<String>;
				
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
	}
	
	public static void extractVoltageLevel (NodeList voltList){
		
		// Create lists for the parameters needed
		ArrayList<String> voltID = new ArrayList<String>;
		ArrayList<String> voltName = new ArrayList<String>;
		ArrayList<String> voltSub = new ArrayList<String>;
		ArrayList<String> voltBaseVolt = new ArrayList<String>;
				
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
	}

	public static void extractGenerationUnit (NodeList genUnitList){
		
		// Create lists for the parameters needed
		ArrayList<String> genUnitID = new ArrayList<String>;
		ArrayList<String> genUnitName = new ArrayList<String>;
		ArrayList<String> genUnitMaxP = new ArrayList<String>;
		ArrayList<String> genUnitMinP = new ArrayList<String>;
		ArrayList<String> genUnitEquipCont = new ArrayList<String>;
				
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
	}

	public static void extractSyncMach (NodeList syncMachList){
		
		// Create lists for the parameters needed
		ArrayList<String> syncMachID = new ArrayList<String>;
		ArrayList<String> syncMachName = new ArrayList<String>;
		ArrayList<String> syncMachRatedS = new ArrayList<String>;
		ArrayList<String> syncMachGenUnit = new ArrayList<String>;
		ArrayList<String> syncMachRegControl = new ArrayList<String>;
		ArrayList<String> syncMachEquipCont = new ArrayList<String>;
		ArrayList<String> syncMachBaseVolt = new ArrayList<String>;
				
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
	}

	public static void extractRegControl (NodeList regControlList){
		
		// Create lists for the parameters needed
		ArrayList<String> regControlID = new ArrayList<String>;
		ArrayList<String> regControlName = new ArrayList<String>;
				
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
	}	
	
	public static void extractTransformer (NodeList transformerList){
		
		// Create lists for the parameters needed
		ArrayList<String> transformerID = new ArrayList<String>;
		ArrayList<String> transformerName = new ArrayList<String>;
		ArrayList<String> transformerEquipCont = new ArrayList<String>;
				
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
	}
	
	public static void extractLoad (NodeList loadList){
		
		// Create lists for the parameters needed
		ArrayList<String> loadID = new ArrayList<String>;
		ArrayList<String> loadName = new ArrayList<String>;
		ArrayList<String> loadEquipCont = new ArrayList<String>;
		ArrayList<String> loadBaseVolt = new ArrayList<String>;
				
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
	}
	
	public static void extractTransfWinding (NodeList transfWindingList){
		
		// Create lists for the parameters needed
		ArrayList<String> transfWindingID = new ArrayList<String>;
		ArrayList<String> transfWindingName = new ArrayList<String>;
		ArrayList<String> transfWindingR = new ArrayList<String>;
		ArrayList<String> transfWindingX = new ArrayList<String>;
		ArrayList<String> transfWindingTransf = new ArrayList<String>;
		ArrayList<String> transfWindingBaseVolt = new ArrayList<String>;
				
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
	}
	
	public static void extractBreaker (NodeList breakerList){
		
		// Create lists for the parameters needed
		ArrayList<String> breakerID = new ArrayList<String>;
		ArrayList<String> breakerName = new ArrayList<String>;
		ArrayList<String> breakerOpen = new ArrayList<String>;
		ArrayList<String> breakerEquipCont = new ArrayList<String>;
		ArrayList<String> breakerBaseVolt = new ArrayList<String>;
				
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
	}	
	
	public static void extractRatioTapChang (NodeList ratioTapChangList){
		
		// Create lists for the parameters needed
		ArrayList<String> ratioTapChangID = new ArrayList<String>;
		ArrayList<String> ratioTapChangName = new ArrayList<String>;
		ArrayList<String> ratioTapChangStep = new ArrayList<String>;
				
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
}
	


