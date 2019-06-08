import java.util.LinkedList;

public class ConsoleView {

	//変数
	private char[][] screen;
	private int width;
	private int height;
	private final static int WIDTH = 80;
	private final static int HEIGHT = 24;

	private Model model;

	/* ------------------
	 * 基本構築:
	 * 
	 * 縦横の設定
	 * Modelとの連携
	 * 
	 * -----------------*/
	public ConsoleView(){
		this(WIDTH,HEIGHT);
	}
	public ConsoleView(int w, int h) {
		height = h;
		width = w;
		screen = new char[height][width];
		clear();
	}
	public ConsoleView(Model m,int width, int height) {
		this(width, height);
		model = m;
	}
	public boolean isOnScreen(int x, int y) {
		return 0 <= x && x < width && isOnScreen(y);
	}
	public boolean isOnScreen(int y) {
		y = calcY(y);
		return 0 <= y && y < height;
	}
	public int get_width() {
		return width;
	}
	public int get_height() {
		return height;
	}

	
	/*--------------------
	 * screen出力
	 * game over画面出力
	 * -------------------*/
	public void update() {
		clear();
		paint_wall();
		paint_supply();
		draw_plane();
		update_bullet();
		update_undestroys();
		update_rock();
		paint_first_line();
		paint_screen();
	}
	private void paint_wall() {
		LinkedList<Wall> walls = model.get_walls();
		for (Wall wall : walls) {
			drawString(wall.get_String(), 0, calcY(wall.get_y()));
		}
	}
	private void paint_supply() {
		LinkedList<Supply> supplies = model.get_supplies();
		for (Supply supply: supplies) {
			put(supply.get_char(), supply.get_x(), calcY(supply.get_y()));
		}
	}
	private void draw_plane() {
		int px = model.get_plane().get_x();
		int py = model.get_plane().get_height();
		char me = model.get_plane().get_me();
		put(me,px, height - (py - model.get_height()));
	}
	private void update_rock() {
		LinkedList<Rock> rocks = model.get_rocks();
		for (Rock rock: rocks) {
			drawRock(rock);
		}
	}
	private void update_undestroys() {
		LinkedList<UndestroyablePart> undestroys = model.get_undestroys();
		for (UndestroyablePart undestroy: undestroys) {
			drawString(undestroy.get_String(), undestroy.get_x(), calcY(undestroy.get_y()));
		}
	}
	private void update_bullet() {
		LinkedList<Bullet> bullets = model.get_bullets();
		for (Bullet bullet : bullets) {
			put(bullet.get_char(), bullet.get_x(), calcY(bullet.get_y()));
		}
	}
	public void game_over() {
		clear();
		System.out.println("GAME OVER");
		drawString("Score: " + model.get_score(),0,0);
		drawString("Floor: " + model.get_score(),0,1);
		paint_screen();
	}
	private void clear() {
		clear(' ');
	}
	private void clear(char c) {
		for (int h = 0; h < screen.length; h++)
			for (int w = 0; w < screen[h].length; w++)
				screen[h][w] = c;
	}
	private void paint_first_line() {
		System.out.println("Score: " + model.get_score() + " Floor: " + model.get_floor() + " Bullet: " + model.get_number().get_bullet());
	}
	private void paint_screen() {
		for (int h = 0; h < screen.length; h++){
			for (int w = 0; w < screen[h].length; w++) {
				System.out.print(screen[h][w]);
			}
			System.out.println();
		}
	}
	private int calcY(int num) {
		return height - (num - model.get_height());
	}
	
	
	
	/* ------------------
	 * 描画機能
	 * 
	 * clear機能
	 * paint:画面出力機能
	 * 図形描画機能（点，線，四角形，楕円）
	 * 
	 * -----------------*/
	public void put(char c, int x, int y) {
		if (0 <= x && x < width && 0 <= y && y < height)
			screen[y][x] = c;
	}
	public void drawString(String s, int x, int y) {
		for (int i = 0; i < s.length(); i++) {
			put(s.charAt(i),x+i,y);
		}
	}
	public void drawRect(char c, int x, int y, int w, int h) {
		for (int i = x; i < x + w; i++) {
			put(c,i,y);
			put(c,i,y+h-1);
		}
		for (int i = y; i < y + h; i++) {
			put(c,x,i);
			put(c,x+w-1,i);
		}
	}
	public void drawOval(char c, int x, int y, int w, int h) {
		for (int i = y - h; i <= y + h; i++) {
			for (int j = x - w; j <= x + w; j++) {
				double xx = j - x;
				double yy = i - y;
				double tmp = xx*xx/w/w + yy*yy/h/h;
				if (tmp <= 1.2) {
					put(c,j,i);
				}
			}
		}
	}
	private void drawRock(Rock rock) {
		char status[][] = rock.get_status();
		for (int i = 0; i < status.length; i++) {
			for (int j = 0; j < status[i].length; j++) {
				if (status[i][j] != ' ')
					put(status[i][j],rock.pos_x(j),calcY(rock.pos_y(i)));
			}
		}
	}
}