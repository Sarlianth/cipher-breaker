package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
 * Adrian Sypos - G00309646
 * Grams - Class responsible for managing 4grams, loading them into a map and actually grading the text whether or not it's english readible
 */

public class Grams {
	
	private String filename;
	private Map<String, Integer> grams;
	private long no;
	
	public Grams(String fileName) {
		this.filename = fileName;
		this.grams = new HashMap<String, Integer>();
	}

	//Method thats resposible for loading the 4grams into a hashMap
	public Map<String, Integer> loadGrams()  throws Exception {
		long count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
		String line = "";
		
		//Loading grams into map
		while((line = br.readLine()) != null) {
			grams.put(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
			//Sum up the total 4grams
			count += Double.parseDouble(line.split(" ")[1]);
		}
		//Save the total number of 4grams
		setNo(count);
		br.close();	
		return this.grams;
	}
	
	//Method thats responsible for scoring the text whether or not its human readible (english)
	public double scoreText(String cipherText) {
		double score = 0;
		int frequency = 0;
		
		//Loop through the ciptherText and compare the 4grams
		for(int i=0; i< cipherText.length() - 4; i++){
			//Check if the 4gram is actually there or is it null
			if(grams.get(cipherText.substring(i, i+4)) != null){
				//If the 4gram exists get its substring and use as frequency to sum up the score
				frequency = grams.get(cipherText.substring(i, i+4));
			}else{
				//If 4gram doesnt exist or is null, set frequency to 1
				frequency = 1;
			}
			//Calculate the score and sum it all up
			score += Math.log10((double) frequency/this.getNo());
		}
		
		//Return the score
		return score;
	}
	
	public void setNo(long no) {
		this.no = no;
	}
	
	public long getNo() {
		return this.no;
	}
}
