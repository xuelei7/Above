import java.util.LinkedList;

public class DeathJudge {
	Model model;
	
	public DeathJudge(Model m) {
		model = m;
	}
	
	public boolean game_over() {
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
