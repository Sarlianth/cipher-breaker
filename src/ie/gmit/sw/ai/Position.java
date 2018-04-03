package ie.gmit.sw.ai;

public class Position {

	private int x, y;
	
	private Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position getPosition(char target, char[][] table) {

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
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
