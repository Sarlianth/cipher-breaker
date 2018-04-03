package ie.gmit.sw.ai;

import java.util.LinkedList;
import java.util.List;

public class Cipher implements Crypt{

	private List<Position> positions;

	private char[][] table;
	private StringBuilder text;
	private String cipher_text;

	public Cipher() {
		this.positions = new LinkedList<Position>();
		this.text = new StringBuilder();
		this.table = new char[5][5];
		this.cipher_text = "";
	}
	
	@Override
	public String decrypt(String key) {
		String decryptionKey = key;
		char[][] table = new char[5][5];

		int index = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				table[i][j] = decryptionKey.charAt(index);
				index++;
			}
		}

		this.table = table;
		
		StringBuilder builder = new StringBuilder();
		
		for(index = 0; index < this.cipher_text.length() / 2; index ++) {
			char a = this.cipher_text.charAt(2 * index);
			char b = this.cipher_text.charAt(2 * index + 1);
			int r1 = (int) Position.getPosition(a, table).getX();
			int c1 = (int) Position.getPosition(a, table).getY();
			int r2 = (int) Position.getPosition(b, table).getX();
			int c2 = (int) Position.getPosition(b, table).getY();

			if (r1 == r2) {
				c1 = (c1 + 4) % 5; 
				c2 = (c2 + 4) % 5;
			} else if (c1 == c2) {
				r1 = (r1 + 4) % 5;
				r2 = (r2 + 4) % 5;
			} else {
		        int temp = c1;
		        c1 = c2;
		        c2 = temp;
		    }
			builder.append(table[r1][c1] +""+ table[r2][c2]);
		}
		
		return builder.toString();
	}

	@Override
	public String encrypt(String key, String text) {
		return null;
	}
	
	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public char[][] getTable() {
		return table;
	}

	public void setTable(char[][] table) {
		this.table = table;
	}

	public StringBuilder getText() {
		return text;
	}

	public void setText(String text) {
		this.text.append(text);
	}

	public String getCipher_text() {
		return cipher_text;
	}

	public void setCipher_text(String cipher_text) {
		this.cipher_text = cipher_text;
	}
}
