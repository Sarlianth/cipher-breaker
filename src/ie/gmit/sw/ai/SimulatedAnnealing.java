package ie.gmit.sw.ai;

import java.security.SecureRandom;
import java.util.Map;

/*
 * Adrian Sypos - G00309646
 * SimulatedAnnealing - Class that will be responsible for the annealing
 */

public class SimulatedAnnealing {
	
	private Key key;
	private Playfair playFair;
	private Grams grams;
	private SecureRandom rand;
	private int temperature;
	private int transitions;
	
	private Map<String, Integer> gramsMap; 
	
	//Constructor
	public SimulatedAnnealing(int temperature, int transitions, String cipherText) {
		super();
		this.grams = new Grams("4grams.txt");
		this.playFair = new Playfair();
		this.playFair.setCipherText(cipherText);
		this.key = Key.keyInstance();
		this.temperature = temperature;
		this.transitions = transitions;
	}
	
	//Method to actually do the annealing
	public void annealing() throws Throwable {		
		
		//Load the 4grams
		gramsMap = grams.loadGrams();
		//Generate key
		String parent = key.generateKey();
		//Decrypt the text using generated key
		String decryptedText = playFair.decrypt(parent);
		//Score decrypted text
		double parentScore = grams.scoreText(decryptedText);
		//Save the best score for later comparisons
		double bestScore = parentScore;
		//Probability variable
		double probability;
		//New random to generate the value between 0.0 and 1.0 to avoid getting stuck
		rand = new SecureRandom();
		//Save the start score so we can later compare the improvement and break the loop if the score is satisfactory
		double startScore = bestScore;
		
		//Loop for each temperature
		for(int temp = temperature; temp > 0; temp--) {
			//Loop for each transition
			for (int index = transitions; index > 0; index--) {
				//Shuffle the parent key so we can get slightly different result
				String child = key.shuffleKey(parent);
				//Decrypt the text again using child key
				decryptedText = playFair.decrypt(child);
				//Score the text that has been decrypted using the child key
				double childScore = grams.scoreText(decryptedText);
				//Measure the delta
				double delta = childScore - parentScore;
				//If the delta is anywhere higher than 0, the child key is better than parent key, hence update our parent key
				if(delta > 0) {
					//Update parent key to child key
					parent = child;
					//Update new score
					parentScore = childScore;
				//If delta isn't higher than 0, meaning the new key isn't any better
				} else  {
					//Measure the probability
					probability = (Math.exp((delta / temp)));
					//If probability is higher than a random value between 0.0 and 1.0 update the key to avoid getting stuck
					if(probability > rand.nextDouble()) {
						//Update the key
						parent = child;
						//update the score
						parentScore = childScore;
					}
				}

				//If the parent score is higher than bestScore print result for debug purposes
				if(parentScore > bestScore) {
					//Save new bescScore
					bestScore = parentScore;
					//String bestKey = parent;
					System.out.printf("\nTemp: %d Transition: %d Key: %s Score: %.2f", temp, index, parent, bestScore);
					//System.out.printf("\nBest hit at Temp: %d\nBest Score: %f0.3\tFor Key: %s\nDecrypted message: %s\n", temp, bestScore, parent, decryptedText);
				}
				
			}

			//If the score is 150% better than it was at the start, print the result
			if(bestScore > (startScore/1.5)){
				System.out.printf("\n----------------------------------------\nHIT! Temp: %d Key: %s Score: %.2f\nDecrypted text: %s\n----------------------------------------\n", temp, parent, bestScore, playFair.decrypt(parent));
				//System.out.printf("\nBest hit at Temp: %d\n_________________________________\nBest Score: %f0.3\tFor Key: %s\nDecrypted message: %s\n", temp, bestScore, parent, playFair.decrypt(parent));
				//If the score is 160% better, break the loop because it has successfully been decrypted
				if(bestScore > (startScore/1.6)) break;
			}
		}
		
		System.out.println("Decrypted: " + playFair.decrypt(parent));
	}
}