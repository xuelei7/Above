
//物体のdelayを管理する
//delayの数値の小さければスピードが大きくなる
public class DelayController {

	private int rock = 20;
	private int screen = 7;
	private int plane = 1;
	
	DelayController() {
	}
	
	public void update(int time) {
		update_rock(time);
		update_screen(time);
	}
	
	public void update_rock(int time) {
		
	}
	public void update_screen(int time) {
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
	
}
