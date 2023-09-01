package assignment_main;



public class Main {

	private XML_parser parser;
	
	public Main(String fileName, int ac){
		parser = new XML_parser(fileName, ac);
	}
	

	
	public static void main(String[] args) {
		
		Main main = new Main("com.dropbox.android.xml", 1);
		
		

	}

}
