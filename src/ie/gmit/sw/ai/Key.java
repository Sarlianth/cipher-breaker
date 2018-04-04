package ie.gmit.sw.ai;

import java.security.SecureRandom;

/*
 * Adrian Sypos - G00309646
 * Key - Class containing the Key object and methods to interact with the key
 */

public class Key {
	
	private static Key instance;
	
	public Key() {
		//Default constructor
	}
	
	//Create new object instance if doesn't exist
	public static Key keyInstance() {
		return (instance == null) ? new Key() : instance;
	}

	/*
	 * Method to generate a key and format it to the length of 35
	 * Replace any double characters with X, remove J and blanks and any other characters that are inappropriate
	 */
	public String generateKey() {
		//Create a String that will be returned when formatted
		StringBuilder newCipherKey = new StringBuilder();
		//Default cipherKey 35 length, without letter J
		String cipherKey = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		//If the cipherKey length is less than 25 remove all recurring characters and add default key, otherwise just use the default key
		newCipherKey.append((cipherKey.length() < 25) ? new FileWorker(null).removeRecurringChars(cipherKey + "ABCDEFGHIKLMNOPQRSTUVWXYZ") : cipherKey);
		
		//For loop to remove all reoccuring characters, loop for each element in the default key
		for (int i = 0; i < cipherKey.length(); i++) {	
			//Loop for each element in the cipherKey string
			for (int j = newCipherKey.length() - 1; j > 0; j--) {
				if (cipherKey.charAt(i) == newCipherKey.charAt(j)) {
					// Delete character
					if (i < j) {
						newCipherKey.deleteCharAt(j);
					}
				}
			}
		}

		//Finally return the generated key
		return newCipherKey.toString();
	}
	
	//Method to shuffle the key
	public String shuffleKey(String originalKey) {
		//Create new secureRandom object
		SecureRandom r = new SecureRandom();
		//Random integer value
		int x = r.nextInt(100);
		
		if(x >= 0 && x < 2) {
			return swapRows(originalKey, r.nextInt(4), r.nextInt(4));
		} else if ( x >= 2 && x < 4) {
			return swapCols(originalKey, r.nextInt(4), r.nextInt(4));
		} else if ( x >= 4 && x < 6) {
			return flipRows(originalKey);
		} else if ( x >= 6 && x < 8) {
			return flipCols(originalKey);
		} else if ( x >= 8 && x < 10) {
			return new StringBuffer(originalKey).reverse().toString();
		} else {
			int a = r.nextInt(originalKey.length()-1);
			int b = r.nextInt(originalKey.length()-1);
			b = (a == b) ? (b == originalKey.length()-1) ? b - 1 : b + 1 : r.nextInt(originalKey.length()-1);
			char[] res = originalKey.toCharArray();
			char tmp = res[a];
			res[a] = res[b];
			res[b] = tmp;
			return new String(res);
		}
	}
	
	//Method to flip rows
	private String flipRows(String key) {
		String[] rows = new String[5];
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 5; i++) {
			rows[i] = key.substring(i*5, i*5 + 5);
			rows[i] = new StringBuffer(rows[i]).reverse().toString();
			sb.append(rows[i]);
		}
		return sb.toString();
	}
	
	//Method to flip columns
	private String flipCols(String key) {
		char[] cols = key.toCharArray();
		int length = key.length() - (key.length()/5);
		
		for(int i = 0; i < key.length() / 5; i++) {
			for(int j = 0; j < key.length() / 5; j++) {
				char tmp = key.charAt(i*5 + j);
				cols[(i*5) + j] =  key.charAt(length + j);
				cols[length + j] =  tmp;
			}
			length -= 5;
		}
		return new String(cols);
	}
	
	//Method to swap rows
	private String swapRows(String key, int r1, int r2) {	
		return (r1 == r2) ? swapRows(key, new SecureRandom().nextInt(4), new SecureRandom().nextInt(4)) :  permute(key, r1, r2, true);
	}
	
	//Method to swap columns
	private String swapCols(String key, int c1, int c2) {
		return (c1 == c2) ? swapCols(key, new SecureRandom().nextInt(4), new SecureRandom().nextInt(4)) : permute(key, c1, c2, false);
	}
	
	//Method to permute the key
	private String permute(String key, int a, int b, boolean rw) {
			char[] newKey = key.toCharArray();
			if(rw) {
				a *= 5;
				b *= 5;
			} 
			for(int i = 0; i < key.length() / 5 ; i++) {
				int index = (rw) ? i : i*5;
				char tmp =  newKey[(index + a)];
				newKey[(index + a)] = newKey[(index + b)];
				newKey[(index + b)] = tmp;				
			}
			return new String(newKey);
	}
}
