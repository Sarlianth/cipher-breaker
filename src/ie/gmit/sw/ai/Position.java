package ie.gmit.sw.ai;

/*
 * Adrian Sypos - G00309646
 * Position - Class representing position on the cipher table, handy to use for moving X and Y 
 */

public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position getPos(char target, char[][] table) {

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (target == table[i][j]) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
