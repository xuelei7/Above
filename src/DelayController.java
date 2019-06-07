
//物体のdelayを管理する
//delayの数値の小さければスピードが大きくなる
public class DelayController {

	private int mx_rock = 20;
	private int mx_screen = 13;
	private int screen = mx_screen;
	private int rock = mx_rock;
	private int screen_div = 200;
	private int rock_div = 100;
	private int rock_add = 50;
	private int plane = 2;
	private int bullet = 1;
	private int supply = 1;

	DelayController() {
	}
	
	public void update(int time) {
		//update_rock(time);
		update_screen(time);
	}
	
	public void update_rock(int time) {
		
	}
	public void update_screen(int time) {
		screen = mx_screen - time / screen_div;
		rock = mx_rock - time / rock_div;
	}
	
	public int get_rock() {
		return rock;
	}
	public int get_screen() {
		return screen;
	}
	
	public boolean time_to_update_screen(int time) {
		return (time % screen == 0);
	}
	public boolean time_to_update_plane(int time) {
		return (time % plane == 0);
	}
	public boolean time_to_update_bullet(int time) {
		return (time % bullet == 0);
	}
	public boolean time_to_update_supply(int time) {
		return (time % supply == 0);
	}
	public boolean time_to_add_rock(int time) {
		return (time % rock_add == 0);
	}
	public boolean time_to_update_rock(int time) {
		return (time % rock == 0);
	} 
}
