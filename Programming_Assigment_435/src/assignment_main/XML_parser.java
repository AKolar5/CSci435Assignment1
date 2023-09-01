package assignment_main;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XML_parser {
	
	private File file;
	private Document doc;
	
	public XML_parser(String fileName, int actionCode) {
		file = new File(fileName);
		processFile(actionCode);
		
	}
	public void processFile(int actionCode) {
		
		try {
			
			// This code was taken from https://www.baeldung.com/java-xerces-dom-parsing
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(file);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
		switch(actionCode) {
		case 0:
		default: highlightUI();
		}
		
		
		
	}
	
	
	private void getLeaves() {
		
		ArrayList<Element> leaves = new ArrayList<>();
		Element docElement = doc.getDocumentElement();
	    NodeList nList = docElement.getChildNodes();
	    
	    int length = nList.getLength();
	    for (int i = 0; i < length; i++) {
	        if (!nList.item(i).hasChildNodes()) {
	            Element leaf = (Element) nList.item(i);
	            leaves.add(leaf);       
	        }
	    }
		
		for (Element l: leaves) {
			System.out.println(l.toString());
		}
		
	
	}
	
	private void highlightUI() {
		
		getLeaves();		
	}
	
	
	
	public static void main(String[] args) {
		//input one command line xml file
		
		//readxml file
	}

}
