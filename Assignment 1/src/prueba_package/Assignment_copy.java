import java.io.File;
import java.util.List;

import org.w3c.dom.NodeList;

public class Assignment_copy {

	public static void main(String[] args) {
		File XmlFile = new File("Assignment_EQ_reduced.xml");
		ReaderEQ ReaderEQ = new ReaderEQ(XmlFile);
		List<NodeList> nodesList = ReaderEQ.nodesmaker(XmlFile);
		
		// Check if it works
		
		int i = 0;
		
		while (i < 12) {
			if (nodesList.get(i) == null) {
				System.out.println("Node " + i + " is empty.");
			}
		}

	}

}
