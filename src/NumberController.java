
//物体の数量と最大数量を管理する
public class NumberController {
	private int mx_wall, wall;
	private int mx_bullet;
	private int mx_rock, rock;
	private int mx_undestroy_length;
	private int undestroy_length = 10;
	private int mx_supply = 2;
	private int supply_num = 5;
	private int supply_rate = 2;
	private int bullet = 10;
	private int bullet_add = 5;
	private int shooting_penalty = 10;
	private int wall_min = 5;
	private int jump_height = 9;
	private int floor_height = 7;
	private int score_per_floor = 100;
	private int mx_rockheight;
	private int mx_rockwidth;
	Model model;
	
	public NumberController(Model m, int wall, int bullet, int rock) {
		this.mx_wall = wall;
		this.wall = 0;

		this.mx_bullet = bullet;
		
		this.mx_rock = rock;
		this.rock = 0;

		mx_rockheight = m.get_view().get_height() / 5;
		mx_rockwidth = m.get_view().get_width() / 5;

		model = m;
		mx_undestroy_length = m.get_view().get_width() / 3;
	}
	
	public void change_add_wall(int d) {
		wall += d;
	}
	public void change_add_bullet(int d) {
		bullet += d;
	}
	public void change_add_rock(int d) {
		rock = d;
	}
	
	public void change_delete_wall(int d) {
		wall -= d;
	}
	public void change_delete_bullet(int d) {
		bullet -= d;
	}
	public void change_delete_rock(int d) {
		rock -= d;
	}
	
	public int get_mx_supply() {
		return mx_supply;
	}
	public int get_supply_num() {
		return supply_num;
	}
	public int get_wall() {
		return wall;
	}
	public int get_bullet() {
		return bullet;
	}
	public int get_rock() {
		return rock;
	}
	public int get_floor_height() {
		return floor_height;
	}
	public int get_score_per_floor() {
		return score_per_floor;
	}
	public int get_supply_rate() {
		return supply_rate;
	}
	public int get_shooting_penalty() {
		return shooting_penalty;
	}
	public int get_mx_undestroy_length() {
		return mx_undestroy_length;
	}
	public int get_mx_rockheight() {
		return mx_rockheight;
	}
	public int get_mx_rockwidth() {
		return mx_rockwidth;
	}
	public int jump_height() {
		return jump_height;
	}
	public int wall_min() {
		return wall_min;
	}
	
	public boolean add_rock() {
		return false;
	}
	public void add_bullet() {
		bullet += bullet_add;
	}
}
