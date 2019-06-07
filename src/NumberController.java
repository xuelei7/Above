
//物体の数量と最大数量を管理する
public class NumberController {
	private int mx_wall, wall;
	private int mx_bullet, bullet;
	private int mx_rock, rock;
	private int wall_min = 5;
	private int jump_height = 9;
	
	public NumberController(int wall, int bullet, int rock) {
		this.mx_wall = wall;
		wall = 0;

		this.mx_bullet = bullet;
		bullet = 100;
		
		this.mx_rock = rock;
		rock = 0;
	}
	
	public void update_mxwall(int time) {
	}
	public void update_mxbullet(int time) {
	}
	public void update_mxrock(int time) {
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
	
	public int get_wall() {
		return wall;
	}
	public int get_bullet() {
		return bullet;
	}
	public int get_rock() {
		return rock;
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
}
