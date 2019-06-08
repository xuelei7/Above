import java.util.LinkedList;

public class DeathJudge {
	Model model;
	
	public DeathJudge(Model m) {
		model = m;
	}
	
	public boolean game_over() {
		if (plane_is_out_of_screen()) {
			return true;
		}
		if (plane_at_rock()) {
			return true;
		}
		return false;
	}
	private boolean plane_is_out_of_screen() {
		return !(model.get_view().isOnScreen(model.get_plane().get_x(), model.get_plane().get_height()));
	}
	public boolean plane_at_rock() {
		LinkedList<Rock> rocks = model.get_rocks();
		for (Rock rock: rocks) {
			if (rock.isRock(model.get_plane().get_x(), model.get_plane().get_height()))
				return true;
		}
		return false;
	}

	public LinkedList<Supply> get_supply() {
		Plane plane = model.get_plane();
		int px = plane.get_x();
		int py = plane.get_height();
		LinkedList<Supply> supplies = model.get_supplies();
		LinkedList<Supply> tmp = new LinkedList<Supply>();
		for (Supply supply: supplies) {
			int sx = supply.get_x();
			int sy = supply.get_y();
			if (px == sx && py == sy) {
				model.supply_to_bullets();
			} else {
				tmp.add(supply);
			}
		}
		return tmp;
	}
	
}
