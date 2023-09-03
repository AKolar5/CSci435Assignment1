package assignment_main;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

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
	
	
	private ArrayList<Element> getLeaves() {
		
		ArrayList<Element> leaves = new ArrayList<>();
		
		
		
		// adapted from this post https://stackoverflow.com/questions/20783506/get-leaf-nodes-xml-parsing-in-java
		try {
			  
			  final XPathExpression xpath = XPathFactory.newInstance().newXPath().compile("//*[count(./*) = 0]");
			  final NodeList nodeList = (NodeList) xpath.evaluate(doc, XPathConstants.NODESET);
			  System.out.println(nodeList.getLength());
			  for(int i = 0; i < nodeList.getLength(); i++) {
			    final Element el = (Element) nodeList.item(i);
			    leaves.add(el);
			  }
			} catch (Exception e) {
			  e.printStackTrace();
			}
		
		
		/*NodeList nList = doc.getElementsByTagName("node");
	    int length = nList.getLength();
	    System.out.println(length);
	    for (int i = 0; i < length; i++) {
	        
	    	System.out.println(nList.item(i));
	    	if (!nList.item(i).hasChildNodes()) {
	            
	        	Element leaf = (Element) nList.item(i);
	            leaves.add(leaf);       
	        }
	    }*/
		
		for (Element l: leaves) {
			System.out.println(l.getAttribute("text"));
		}
		
		return leaves;
		
	
	}
	
	private void highlightUI() {
		
		ArrayList<Element> leaves = new ArrayList<>();
		leaves = getLeaves();
		
		for (Element l: leaves) {
			l.setAttribute("border", "1");
		}
		
	}
	
	
	
	public static void main(String[] args) {
		//input one command line xml file
		
		//readxml file
	}

}
