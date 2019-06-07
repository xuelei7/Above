import java.util.Random;

public class UndestroyablePart {
    int length, y, x;
    Random r = new Random();
    char undestroy[];
    char c = 'x';
    Model model;

    UndestroyablePart(Model m, int y) {
        length = r.nextInt(m.get_number().get_mx_undestroy_length());
        this.y = y;
        model = m;
        undestroy = new char[length];
        for (int i = 0; i < length; i++) {
            undestroy[i] = c;
        }
        x = r.nextInt(model.get_view().get_width());
    }
    public int get_length() {
        return length;
    }
    public int get_y() {
        return y;
    }
    public int get_x() {
        return x;
    }
    public String get_String() {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += undestroy[i];
        }
        return s;
    }
}
