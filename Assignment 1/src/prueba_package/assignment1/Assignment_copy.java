package assignment1;

import java.io.File;
import java.util.List;

import org.w3c.dom.NodeList;

public class Assignment_copy {

	public static void main(String[] args) {
		File XmlFile = new File("Assignment_EQ_reduced.xml");
		Reader Reader = new Reader(XmlFile);
		List<NodeList> nodesList = Reader.nodesmaker(XmlFile);
		
	
	}

}
