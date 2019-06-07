
public class Plane {
	private int x, y;
	private boolean jumping;
	private char me;
	
	public Plane(int x, int y, char c) {
		this.x = x;
		this.y = y;
		this.me = c;
	}
	public int get_x() {
		return x;
	}
	public int get_y() {
		return y;
	}
	public char get_me() {
		return me;
	}
	public void change_x(int d) {
		x += d;
	}
	public boolean jumping() {
		return jumping;
	}
	public boolean move_left() {
		if (x <= 0) return false;
		else {
			x--;
			return true;
		}
	}
	public boolean move_right() {
		if (x >= 79) return false;
		else {
			x++;
			return true;
		}
	}
	public void jump() {
		
	}
	public void shoot() {
		
	}
	public boolean nothing_under() {
		return false;
	}
	public void fall() {
		
	}
	public void continue_jumping() {
		
	}
}
