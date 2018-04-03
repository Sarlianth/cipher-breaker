package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Grams {

	private String filename;
	private Map<String, Double> grams;
	private int externalCount;
	
	public Grams(String filename) {
		this.filename = filename;
		this.grams = new HashMap<String, Double>();
	}

	public Map<String, Double> loadGrams() {
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
			
			String line = "";
			while((line = br.readLine()) != null) {
				grams.put(line.split(" ")[0], Double.parseDouble(line.split(" ")[1]));
				count++;
			}
			setExternalCount(count);
			br.close();
			return this.grams;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getExternalCount() {
		return externalCount;
	}

	public void setExternalCount(int externalCount) {
		this.externalCount = externalCount;
	}

}
