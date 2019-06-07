import java.io.IOException;
import java.util.LinkedList;

public class Model {
	
	private int score;
	private int floor;
	private int time;
	private int height;
	
	private ConsoleView view;
	private Controller controller;
	private DelayController delay;
	private NumberController number;
	private DeathJudge judge;
	
	private LinkedList<Wall> walls;
	private LinkedList<Bullet> bullets;
	private LinkedList<Rock> rocks;
	private LinkedList<UndestroyablePart> unDestroys;
	private Plane plane;
	
	/*----------------
	 * 基本構築
	 * ---------------*/
	public Model(){
		set_view();
		set_controller();
		set_delay();
		set_number();
		set_judge();
		set_plane();

		set_variables();
		set_objects();
	}
	private void set_view() {
		view = new ConsoleView(this,80,22);
	}
	private void set_controller() {
		controller = new Controller(this);
	}
	private void set_delay() {
		//screen, rock
		delay = new DelayController();
	}
	private void set_number() {
		//wall, bullet, rock
		number = new NumberController(20,20,23);
	}
	private void set_judge() {
		judge = new DeathJudge(this);
	}
	private void set_plane() {
		//デフォルトのx座標，y座標
		plane = new Plane(this,0,0,'0');
	}
	private void set_variables() {
		score = 0;
		floor = 0;
		time = 0;
		height = -20;
	}
	private void set_objects() {
		set_walls();
		set_bullets();
		set_rocks();
	}
	private void set_walls() {
		walls = new LinkedList<Wall>();
		walls.add(new Wall(-1));
	}
	private void set_bullets() {
		bullets = new LinkedList<Bullet>();
	}
	private void set_rocks() {
		rocks = new LinkedList<Rock>();
	}
	
	/*----------------
	 * publish variables
	 * ---------------*/
	public int get_score() {
		return score;
	}
	public int get_floor() {
		return floor;
	}
	public int get_time() {
		return time;
	}
	public int get_height() {
		return height;
	}
	public LinkedList<Wall> get_walls() {
		return walls;
	}
	public LinkedList<Bullet> get_bullets() {
		return bullets;
	}
	public LinkedList<Rock> get_rocks() {
		return rocks;
	}
	public LinkedList<UndestroyablePart> get_unDestroys() {
		return unDestroys;
	}
	public Plane get_plane() {
		return plane;
	}
	public NumberController get_number() {
		return number;
	}
	public ConsoleView get_view() {
		return view;
	}

	/*----------------
	 * update
	 * ---------------*/
	public void update() {
//		update_delay();
		if (delay.time_to_update_screen(time)) {
			update_screen();
			update_wall();
		}
//		update_rock();
		if (delay.time_to_update_bullet(time)) {
			update_bullet();
		}
		if (delay.time_to_update_plane(time)) {
			update_plane();
		}
	}
	
	private void update_delay() {
		delay.update(time);
	}
	private void update_wall() {
		LinkedList<Wall> tmp = new LinkedList<Wall>();
		for (Wall wall: walls) {
			if (wall.isOnScreen(height)) {
				tmp.add(wall);
			}
		}
		walls = tmp;
		if (height % 8 == 0)
			walls.add(new Wall(height + 23));
	}
	private void update_rock() {
		LinkedList<Rock> tmp = new LinkedList<Rock>();
		//update old rocks
		for (Rock rock: rocks) {
			rock.falldown();
			if (rock.inScreen()) {
				tmp.add(rock);
			}
		}
		if (number.add_rock()) {
			tmp.add(new Rock());
		}
		rocks = tmp;
	}
	public void update_bullet() {
		LinkedList<Bullet> tmp = new LinkedList<Bullet>();
		for (Bullet bullet: bullets) {
			bullet.update();
			int x = bullet.get_x();
			int y = bullet.get_y();
			if (isRock(x,y)) {
				destroyRock(x,y);
				continue;
			}
			if (isDestroyableWall(x,y)) {
				destroyWall(x,y);
				continue;
			}
			if (view.isOnScreen(x,y)) {
				tmp.add(bullet);
			}
		}
		bullets = tmp;
	}
	private boolean isDestroyableWall(int x, int y) {
		for (Wall wall: walls) {
			if (wall.get_y() == y) {
				if (wall.isDestroyable(x)) {
					wall.destroy(x);
					return true;
				}
			}
		}
		return false;
	}
	private boolean isRock(int x, int y) {
		return false;
	}
	private void destroyWall(int x, int y) {

	}
	private void destroyRock(int x, int y) {

	}
	public void update_plane() {
		if (plane.jumping()) {
			plane.continue_jumping();
		}
		else {
			plane.fall();
		}
	}
	public void update_screen() {
		delay.update_screen(time);
		if (delay.time_to_update_screen(time)) {
			height++;
		}
	}
	public boolean isWall(int x, int y) {
		for (Wall wall: walls) {
			if (wall.get_y() == y) {
				return wall.isWall(x);
			}
		}
		return false;
	}
	public void add_bullet(int x, int y) {
		bullets.add(new Bullet(x,y));
	}
	
	public synchronized void process(String event) {
		//時間経過
		time++;
		
		//入力がない場合
		if (event.equals("TIME_ELAPSED")) {
			update();
		}
		
		//入力がある場合
		else {
			System.out.println(event);
			char c = event.charAt(0);
			if (c == 'a') {
				if (!plane.move_left()) update();
			}
			else if (c == 'd') {
				if (!plane.move_right()) update();
			}
			else if (c == 'm') {
				plane.shoot();
			}
			else if (c == 'w' && !plane.jumping()) {
				plane.jump();
			} else {
				update();
			}
		}

		/*  OK  */
		if (judge.game_over()) {
			view.game_over();
		} else {
			view.update();
		}
		
	}
	public void run() throws IOException, InterruptedException {
		controller.run();
	}	
	public static void main(String[] args) throws InterruptedException, IOException {
		Model model = new Model();
		model.run();
	}
}
