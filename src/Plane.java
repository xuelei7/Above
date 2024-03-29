
public class Plane {
	private int x, height;
	private int jumping;
	private boolean falling;
	private char me;
	Model model;
	
	public Plane(Model m, int x, int height, char c) {
		this.x = x;
		this.height = height;
		this.me = c;
		model = m;
		falling = false;
	}
	public int get_x() {
		return x;
	}
	public int get_height() {
		return height;
	}
	public char get_me() {
		return me;
	}
	public void change_x(int d) {
		x += d;
	}
	public boolean jumping() {
		return jumping > 0;
	}
	public boolean falling() {
		return falling;
	}
	public boolean move_left() {
		if (x <= 0 || model.isRock(x-1,height)) return false;
		else {
			x--;
			return true;
		}
	}
	public boolean move_right() {
		if (x >= 79 || model.isRock(x+1,height)) return false;
		else {
			x++;
			return true;
		}
	}
	public void jump() {
		if (!jumping() && !falling()) {
			jumping = model.get_number().jump_height();
			falling = true;
		}
	}
	public void shoot() {
		if (model.get_number().get_bullet() == 0) return;
		model.get_number().change_add_bullet(-1);
		model.add_bullet(x,height);
		model.add_shooting_penalty();
	}
	public boolean nothing_under() {
		return false;
	}
	public void fall() {
		if (!model.isWall(x,height-1)
		&& !model.isUndestroyable(x,height-1)
		&& !model.isRock(x,height-1)) {
			height--;
			falling = true;
		} else {
			falling = false;
		}
	}
	public void continue_jumping() {
		//add height
		if (!model.isWall(x,height+1)
		&& model.get_view().isOnScreen(x, height+1)
		&& !model.isUndestroyable(x,height+1)) {
			height++;
			jumping--;
		} else {
			jumping = 0;
		}
	}
}
