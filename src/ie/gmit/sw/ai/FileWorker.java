package ie.gmit.sw.ai;

/*
 * Adrian Sypos - G00309646
 * FileWorker - Class that will be responsible for handling files, reading and saving to and from files
 */

public class FileWorker {
	
	private String filename;
	
	//Constructor
	public FileWorker(String fileName) {
		this.filename = fileName;
	}
	
	//Method to remove duplicates and replace them with X
	public String removeRecurringChars(String l) {
		char[] line = l.toUpperCase().toCharArray();
		
		for(int i = 0; i < line.length; i++) {
			if(i != line.length - 1) 
				line[i+1] = (line[i] == line[i+1]) ? 'X' : line[i+1];
		}
		return new String(line);
	}
}
