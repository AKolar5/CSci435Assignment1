

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 
 * Parses .xml files and extracts data
 * 
 * Last Modified: 9/4/23
 * 
 * @author Alex Kolar
 *
 */
public class XML_parser {
	
	private File file; //file to be parsed
	private Document doc; //post processing usable object
	
	
	/**
	 * empty constructor
	 */
	public XML_parser() {
		
	}
	
	/**
	 * Set field xmlFile
	 * @param xmlFile
	 */
	public void setFile(String xmlFile) {
		file = new File("Resources\\" + xmlFile);
	}
	
	/**
	 * Initializes doc field
	 */
	public void processFile(){		
			
		try {
			
			// This code was taken from https://www.baeldung.com/java-xerces-dom-parsing
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(file);			
			doc.getDocumentElement().normalize();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Gets the bounds attribute of node n
	 * @param n
	 * @return an integer array of length 4 containing: [topleftx, toplefty, bottomrightx, bottomrighty]
	 */
	public int[] getPosOfNode(Node n) {
		
		//get attribute as string
		NamedNodeMap attributesNode = n.getAttributes();		
		String boundString = attributesNode.getNamedItem("bounds").getNodeValue();
		
		//parse string into usable data
		String topLeftXString = boundString.substring(1, boundString.indexOf(","));
		String topLeftYString = boundString.substring(boundString.indexOf(",")+1, boundString.indexOf("]"));		
		String secondSetString = boundString.substring(topLeftXString.length() + topLeftYString.length() + 4);		
		String bottomRightXString = secondSetString.substring(0, secondSetString.indexOf(","));
		String bottomRightYString = secondSetString.substring(secondSetString.indexOf(",")+1, secondSetString.indexOf("]"));
		
		int topLeftX = Integer.parseInt(topLeftXString);
		int topLeftY = Integer.parseInt(topLeftYString);
		int bottomRightX = Integer.parseInt(bottomRightXString);
		int bottomRightY = Integer.parseInt(bottomRightYString);
		
		int[] posArr = new int[] {topLeftX, topLeftY, bottomRightX, bottomRightY};
		
		return posArr;
				
	}
	
	/**
	 * Gets all of the leaves of the xml file being parsed
	 * 
	 * @return an ArrayList of all the nodes without children from the current file
	 */
	public ArrayList<Element> getLeaves() {
	
		ArrayList<Element> leaves = new ArrayList<>();
		
		// adapted from this post https://stackoverflow.com/questions/20783506/get-leaf-nodes-xml-parsing-in-java
		try {
			  
			  final XPathExpression xpath = XPathFactory.newInstance().newXPath().compile("//*[count(./*) = 0]");
			  final NodeList nodeList = (NodeList) xpath.evaluate(doc, XPathConstants.NODESET);
			  for(int i = 0; i < nodeList.getLength(); i++) {
			    final Element el = (Element) nodeList.item(i);
			    leaves.add(el);
			  }
			} catch (Exception e) {
			  e.printStackTrace();
			}
		
		return leaves;
			
	}
	
}
