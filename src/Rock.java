import java.util.Random;

public class Rock {
	private int height, width;
	private int x, y;
	private Random r = new Random();
	private Model model;
	private char c = '@';

	Rock(Model m, int y) {
		model = m;
		this.y = y + 5;
		x = r.nextInt(model.get_view().get_width());
		height = r.nextInt(model.get_number().get_mx_rockheight());
		width = r.nextInt(model.get_number().get_mx_rockwidth());
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
	public void update() {
		y--;
	}
	public boolean isOnScreen() {
		int upper = y + height;
		return upper >= model.get_height();
	}
}
