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
		delay = new DelayController(20,20);
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
		plane = new Plane(0,0,'0');
	}
	private void set_variables() {
		score = 0;
		floor = 0;
		time = 0;
		height = -20;
	}
	private void set_objects() {
		set_walls();
	}
	private void set_walls() {
		walls = new LinkedList<Wall>();
		walls.add(new Wall(-1));
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
	public int get_bullet() {
		return number.get_bullet();
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

	/*----------------
	 * update
	 * ---------------*/
	public void update() {
//		update_delay();
		update_screen();
		update_wall();
//		update_rock();
//		update_bullet();
		update_plane();
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
			if (bullet.onScreen())
				tmp.add(bullet);
		}
		bullets = tmp;
	}
	public void update_plane() {
		if (plane.jumping()) {
			plane.continue_jumping();
		}
		else {
			falldown_plane();
		}
	}
	public void update_screen() {
		delay.update_screen(time);
		if (delay.time_to_update_screen(time)) {
			height++;
		}
	}
	public void falldown_plane() {
		if (plane.nothing_under()) {
			plane.fall();
		}
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
			} else if (c == 'd') {
				if (!plane.move_right()) update();
			}
			/*else if (event == "s") {
				plane.shoot();
			} else if (event == "w" && !plane.jumping()) {
				plane.jump();
			}*/ else {
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
