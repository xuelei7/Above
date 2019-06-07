
public class Wall {
	
	private int y;
	private final static int WIDTH = 80;
	private char wall[];
	
	public Wall(int h) {
		y = h;
		wall = new char[WIDTH];
		for (int i = 0; i < WIDTH; i++) {
			wall[i] = '#';
		}
	}

	public boolean isOnScreen(int h) {
		return h <= y && y < h + 25;
	}
	public void destroy(int w) {
		wall[w] = ' ';
	}

	public int get_Y() {
		return y;
	}
	public char get_status(int pos) {
		return wall[pos];
	}

	public String get_String(){
		String s = "";
		for (int i = 0; i < WIDTH; i++) {
			s += wall[i];
		}
		return s;
	}
	
	public void fall_down() {
		y++;
	}
}
