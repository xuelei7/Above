import java.util.Random;

public class Rock {
	private int height, width;
	private int x, y;
	private Random r = new Random();
	private Model model;
	private char c = '@';
	private char rock[][];

	Rock(Model m, int y) {
		model = m;
		height = r.nextInt(model.get_number().get_mx_rockheight()) + 1;
		width = r.nextInt(model.get_number().get_mx_rockwidth()) + 1;
		this.y = y + height;
		x = r.nextInt(model.get_view().get_width());
		rock = new char[height][width];
		drawRock();
	}
	public int get_x() {
		return x;
	}
	public int get_y() {
		return y;
	}
	public int get_height() {
		return height;
	}
	public int get_width() {
		return width;
	}
	public char get_char() {
		return c;
	}
	public char[][] get_status() {
		return rock;
	}
	public int pos_x(int pos) {
		return pos + x;
	}
	public int pos_y(int pos) {
		return y - pos;
	}
	public void update() {
		y--;
	}
	public boolean isOnScreen() {
		return y >= model.get_height();
	}
	public boolean isRock(int px, int py) {
		if (py <= y - height || y < py) return false;
		if (px < x || x + width <= px) return false;
		return rock[y - py][px - x] == c;
	}
	private void drawRock() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rock[i][j] = c;
			}
		}
		rock[0][0] = ' ';
		rock[0][width-1] = ' ';
		rock[height-1][0] = ' ';
		rock[height-1][width-1] = ' ';
	}
	public void destroy(int px, int py) {
		rock[y - py][px - x] = ' ';
	}
}
