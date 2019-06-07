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
		return 0 <= x && x < width && model.get_height() <= y && y < (model.get_height() + height);
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
		draw_plane();
		update_rock();
		update_bullet();
		paint_first_line();
		paint_screen();
	}
	private void paint_wall() {
		LinkedList<Wall> walls = model.get_walls();
		for (Wall wall : walls) {
			drawString(wall.get_String(), 0, height - (wall.get_Y() - model.get_height()));
		}
	}
	private void draw_plane() {
		int px = model.get_plane().get_x();
		int py = model.get_plane().get_height();
		char me = model.get_plane().get_me();
		put(me,px, height - (py - model.get_height()));
	}
	private void update_rock() {
		
	}
	private void update_bullet() {
		
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
		System.out.println("Time: " + model.get_time() + " Height: " + model.get_height());
		System.out.println("Plane height: " + model.get_plane().get_height());
		System.out.println("Above: " + (model.get_height() + height) + " Below: " + model.get_height());
		System.out.println("Score: " + model.get_score() + " Floor: " + model.get_floor() + " Bullet: " + model.get_bullet());
	}
	private void paint_screen() {
		for (int h = 0; h < screen.length; h++){
			for (int w = 0; w < screen[h].length; w++) {
				System.out.print(screen[h][w]);
			}
			System.out.println();
		}
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
		w = w / 2;
		h = h / 2;
		for (int i = y - h; i <= y + h; i++) {
			for (int j = x - w; j <= x + w; j++) {
				double xx = j - x;
				double yy = i - y;
				double tmp = xx*xx/w/w + yy*yy/h/h;
				if (0.8 <= tmp && tmp <= 1.2) {
					put(c,j,i);
				}
			}
		}
	}
	
}