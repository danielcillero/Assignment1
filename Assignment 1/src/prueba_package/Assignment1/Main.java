package tryReader;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

	public static void main(String[] args) {
		
		File EQFile = new File("Assignment_EQ_reduced.xml");
		File SSHFile = new File("Assignment_SSH_reduced.xml");
		
		Reader EQ = new Reader(EQFile,"EQ");
		Reader SSH = new Reader(SSHFile,"SSH");
		
		// EQ NodeLists
		NodeList baseVoltListEQ = EQ.nodes.get(0);
		NodeList subListEQ = EQ.nodes.get(1); 
		NodeList voltListEQ = EQ.nodes.get(2);
		NodeList genUnitListEQ = EQ.nodes.get(3);
		NodeList syncMachListEQ = EQ.nodes.get(4);
		NodeList regControlListEQ = EQ.nodes.get(5);
		NodeList transformerListEQ = EQ.nodes.get(6);
		NodeList loadListEQ = EQ.nodes.get(7);
		NodeList transfWindingListEQ = EQ.nodes.get(8);
		NodeList breakerListEQ = EQ.nodes.get(9);
		NodeList ratioTapChangListEQ = EQ.nodes.get(10);
		
		// SSH NodeLists
		NodeList syncMachListSSH = SSH.nodes.get(0);
		NodeList regControlListSSH = SSH.nodes.get(1);
		NodeList loadListSSH = SSH.nodes.get(2);
		
		// Synchronous Machines
		
		ArrayList<SyncMachine> syncMachList = new ArrayList<>();
		
		for (int i=0 ; i < syncMachListEQ.getLength() ; i++) {
			
			Node node = syncMachListEQ.item(i);
			Element eElementEQ = (Element) node;
			
			String rdfID = eElementEQ.getAttribute("rdf:ID");
			
			for (int j=0 ; j < syncMachListSSH.getLength() ; j++) {
				Node nodeEQ = syncMachListSSH.item(i);
				Element eElementSSH = (Element) nodeEQ;
				
				String rdfSSH = eElementSSH.getAttribute("rdf:about").substring(1);
				
				if (rdfID.equals(rdfSSH)) {
					
					System.out.println(rdfID + " is equal to " + rdfSSH); // For testing
										
					SyncMachine symach = new SyncMachine(eElementEQ,eElementSSH,voltListEQ);
					
					syncMachList.add(symach);
					
				}
				
			}
		}
		
		
		// Try to see if it works
		
		System.out.println("For the SyncMach with ID " + syncMachList.get(0).ID + " the ID of the VoltageLevel is " + 
					syncMachList.get(0).equipContID + " and the base voltage ID is " + syncMachList.get(0).baseVoltID);
		
		
				
				
		// equipcont _4ba71b59-ee2f-450b-9f7d-cc2f1cc5e386 base voltage is _862a4658-6b03-4550-9de2-b5c413912b75

	}

}
