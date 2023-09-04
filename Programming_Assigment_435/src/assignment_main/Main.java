/**
 * 
 * Main Class for the initial programming assignment in CS 435.
 * Input: folder containing .png files and their corresponding .xml files
 * Output: New .png files with the UI elements outlined, in the PNGOutputs folder.
 * 
 * @author Alex Kolar
 * 
 * 
 * Last Modified: 9/4/23
 * 
 */


package assignment_main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Main {

	private XML_parser parser; //used for parsing .xml files
	private File pngFile; //.png file to draw on
	private ArrayList<String> usedFileStrings; //the xml files we have already processed
	
	public Main(){
		parser = new XML_parser();		
		usedFileStrings = new ArrayList<>();	
	}
	
	
	/**
	 * Creates a yellow rectangle around the UI elements
	 * of a png image of an application interface.
	 * @throws IOException
	 */
	public void highlightUI() throws IOException {
		
		ArrayList<Element> leaves = parser.getLeaves(); // get the leaves of a xml doc
		int length = leaves.size(); // number of leaves
		int[] posArr; // arr storing bounds of UI element [topleftx, toplefty, bottomrightx, bottomrighty]
		Node testNode; //node currently being drawn around
		
		// getting the graphics editor set up for the file
		BufferedImage img = ImageIO.read(pngFile);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.YELLOW);
		g.setStroke(new BasicStroke(5)); 
		
		// get each leaf node's position, draw an outline using that position
		for(int i = 0; i < length; i++) { 
			testNode = leaves.get(i);
			posArr = parser.getPosOfNode(testNode);
			Rectangle outline = new Rectangle(posArr[0],posArr[1],posArr[2]-posArr[0],posArr[3]-posArr[1]);
			g.draw(outline);
		}

		saveAsPNG("PNGOutputs//" + this.pngFile.getName(), img); //save the new image 
			
	}
	
	/**
	 * 
	 * Saves BufferedImage as png
	 * 
	 * @param fileName Name of the new file
	 * @param img BufferedImage being saved
	 * @throws IOException
	 */
	private void saveAsPNG(String fileName, BufferedImage img) throws IOException {
		File file = new File(fileName);
		ImageIO.write(img, "PNG", file);
	}
	
	/**
	 * Determines if we should parse this file
	 * 
	 * @param file
	 * @return
	 */
	private boolean useFile(Path file) {
		
		String fileString = file.getFileName().toString();
		
		// don't use if we've already processed
		if (usedFileStrings.contains(fileString))
			return false;
		
		// don't parse if it a .png
		if (fileString.indexOf(".png") > 0) 
			return false;
		
		return true;
		
	}
	
	/**
	 * pngFile setter
	 * 
	 * @param name
	 */
	public void setPngFile(String name) {
		pngFile = new File(name);
	}

	
	public static void main(String[] args) {
		
		Main main = new Main();
		
		String pathString = args[0];		
		
		Path path = Paths.get(pathString);
		
		String pngString; //used to derive .png file name
		
		
		
		// read all of the files in the directory given by "path"
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            
			// for each file in the directory, check if it is .xml, then 
			// find corresponding png file. Use these two files to highlight UI.
			for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    if (main.useFile(file)) {
                    	
                    	//derive name of png file from corresponding xml file
                    	pngString = file.getFileName().toString();
                    	pngString = pngString.substring(0, pngString.indexOf(".xml"));
                    	pngString = pngString + ".png";
                    	
                    	//set the correct files from the folder
                    	main.setPngFile("Resources\\" + pngString);
                    	
                    	//set and process the xml file
                		main.parser.setFile(file.getFileName().toString());
                    	main.parser.processFile();
                    	
                    	try {                          			
                			//highlight the UI
                    		main.highlightUI();
                		} catch (IOException e) {
                			// TODO Auto-generated catch block
                			e.printStackTrace();
                		}
                    	
                    	//Update used files
                    	main.usedFileStrings.add(file.getFileName().toString());
                    	
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }						

	}

}
