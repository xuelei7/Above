interface Fall {
	
}

interface Add {
	void add();
}

interface Sub {
	void sub();
}

public class Move implements Add, Sub {
	public void add() {
		System.out.println(111);
	}
	public void sub() {
		System.out.println(222);
	}
}

class Main {
	public static void main(String[] args) {
		Move calc = new Move();
		calc.add();
		calc.sub();
	}
}