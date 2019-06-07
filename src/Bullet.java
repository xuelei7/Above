
public class Bullet {
	private int x,y;
	private char c = '|';
	
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void update() {
		y++;
	}
	public int get_x() {
		return x;
	}	
	public int get_y() {
		return y;
	}
	public char get_char() {
		return c;
	}
	public boolean onScreen() {
		return true;
	}
}
