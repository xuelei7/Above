import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Model {
	
	private int score;
	private int floor;
	private int time;
	private int height;
	private boolean game_start;
	private boolean game_over;
	
	private ConsoleView view;
	private Controller controller;
	private DelayController delay;
	private NumberController number;
	private DeathJudge judge;
	
	private LinkedList<Wall> walls;
	private LinkedList<Bullet> bullets;
	private LinkedList<Rock> rocks;
	private LinkedList<UndestroyablePart> undestroys;
	private LinkedList<Supply> supplies;
	private Plane plane;
	
	/*----------------
	 * 基本構築
	 * ---------------*/
	public Model(){
		game_set();
	}
	private void set_view() {
		view = new ConsoleView(this,80,21);
	}
	private void set_controller() {
		controller = new Controller(this);
	}
	private void set_delay() {
		delay = new DelayController();
	}
	private void set_judge() {
		judge = new DeathJudge(this);
	}
	private void set_variables() {
		score = 0;
		floor = 0;
		time = 0;
		height = -20;
		game_start = false;
		game_over = false;
	}
	private void set_objects() {
		set_number();
		set_plane();
		set_walls();
		set_bullets();
		set_rocks();
		set_supplies();
		set_undestroys();
	}
	private void set_number() {
		//wall, bullet, rock
		number = new NumberController(this,20,20,23);
	}
	private void set_plane() {
		//デフォルトのx座標，y座標
		plane = new Plane(this,39,1,'0');
	}
	private void set_walls() {
		walls = new LinkedList<Wall>();
		walls.add(new Wall(0));
	}
	private void set_bullets() {
		bullets = new LinkedList<Bullet>();
	}
	private void set_rocks() {
		rocks = new LinkedList<Rock>();
	}
	private void set_supplies() {
		supplies = new LinkedList<Supply>();
	}
	private void set_undestroys() {
		undestroys = new LinkedList<UndestroyablePart>();
	}

	/*----------------
	 * publish variables
	 * ---------------*/
	public int get_score() {
		return score + floor * number.get_score_per_floor();
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
	public LinkedList<UndestroyablePart> get_undestroys() {
		return undestroys;
	}
	public LinkedList<Supply> get_supplies() {
		return supplies;
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
	public DelayController get_delay() {
		return delay;
	}
	public DeathJudge get_judge() {
		return judge;
	}

	/*----------------
	 * update
	 * ---------------*/
	public void update() {
		update_delay();
		if (delay.time_to_update_screen(time)) {
			update_screen();
			update_wall();
			update_floor();
			update_undestroy();
		}
		if (delay.time_to_update_rock(time)) {
			update_rock();
		}
		if (delay.time_to_add_rock(time)) {
			add_rock();
		}
		if (delay.time_to_update_bullet(time)) {
			update_bullet();
		}
		if (delay.time_to_update_supply(time)) {
			update_supply();
		}
		supplies = judge.get_supply();
		if (delay.time_to_update_plane(time)) {
			update_plane();
		}
		supplies = judge.get_supply();
	}
	
	private void game_set() {
		set_view();
		set_controller();
		set_delay();
		set_judge();

		set_variables();
		set_objects();
	}

	private void add_rock() {
		rocks.add(new Rock(this, height + view.get_height() - 1));
	}
	private void update_rock() {
		LinkedList<Rock> tmp = new LinkedList<Rock>();
		for (Rock rock: rocks) {
			rock.update();
			if (rock.isOnScreen()) {
				tmp.add(rock);
			}
		}
		rocks = tmp;
	}
	public void supply_to_bullets() {
		number.add_bullet();
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
		if (height % number.get_floor_height() == 0) {
			int local_height = height + view.get_height();
			walls.add(new Wall(local_height));
			add_supply(local_height);
			add_undestroy(local_height);
		}
	}
	private void update_supply() {
		LinkedList<Supply> tmp = new LinkedList<Supply>();
		for (Supply supply:supplies) {
			supply.update();
			if (view.isOnScreen(supply.get_x(), supply.get_y()-1)) {
				tmp.add(supply);
			}
		}
	}
	private void add_undestroy(int h) {
		undestroys.add(new UndestroyablePart(this, h));
	}
	private void add_supply(int h) {
		Random r = new Random();
		int k = r.nextInt(number.get_supply_rate());
		if (k == 0) supplies.add(new Supply(this, h+1));
	}
	private void update_floor() {
		floor = plane.get_height() / number.get_floor_height();
	}
	private void update_undestroy() {
		LinkedList<UndestroyablePart> tmp = new LinkedList<UndestroyablePart>();
		for (UndestroyablePart undestroy : undestroys) {
			if (view.isOnScreen(undestroy.get_y())) {
				tmp.add(undestroy);
			}
		}
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
			if (isUndestroyable(x,y)) {
				continue;
			}
			if (isDestroyableWall(x,y)) {
				continue;
			}
			if (view.isOnScreen(x,y)) {
				tmp.add(bullet);
			}
		}
		bullets = tmp;
	}
	public boolean isUndestroyable(int x, int y) {
		for (UndestroyablePart undestroy: undestroys) {
			if (undestroy.get_y() == y) {
				int left = undestroy.get_x();
				int right = left + undestroy.get_length() - 1;
				return left <= x && x <= right;
			}
		}
		return false;
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
	private void destroyRock(int x, int y) {
		for (Rock rock: rocks) {
			if (rock.isRock(x,y)) {
				rock.destroy(x,y);
			}
		}
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
	public boolean isRock(int x, int y) {
		for (Rock rock: rocks) {
			if (rock.isRock(x,y)) {
				return true;
			}
		}
		return false;
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
	public void add_shooting_penalty() {
		score -= number.get_shooting_penalty();
	}
	
	public synchronized void process(String event) {
		
		if (!game_start) {
			if (!event.equals("TIME_ELAPSED")) {
				game_start = true;
				return;
			} else {
				view.game_start();
				return;
			}
		}

		if (game_over) {
			view.game_over();
			if (!event.equals("TIME_ELAPSED")) {
				game_set();
			}
			return;
		}

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
				plane.move_left();
				update();
			}
			else if (c == 'd') {
				plane.move_right();
				update();
			}
			else if (c == 'm' || c == 's') {
				plane.shoot();
			}
			else if ((c == 'n' || c == 'w') && !plane.jumping()) {
				plane.jump();
			} else {
				update();
			}
		}

		/*  OK  */
		if (judge.game_over()) {
			view.game_over();
			game_over = true;
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
