import java.util.Random;

public class Supply {
    private int x,y;
    private char c = '$';
    Random r = new Random();
    Model model;

    Supply(Model m, int y) {
        model = m;
        x = r.nextInt(m.get_view().get_width());
        this.y = y;
    }
    public int get_x() {
        return x;
    }
    public int get_y() {
        return y;
    }
    public char get_char() {
        return c;
    }  
    public void update() {
        if (!model.isWall(x, y-1)) {
            y--;
        }
    }

}