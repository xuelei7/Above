import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Controller implements ActionListener{

	//変数
	private final static int DELAY = 200;
	private Timer timer;
	private Model model;
	

	/*---------------
	 * 基本構築
	 * 
	 * Modelとの関連付け
	 * 実行
	 * 
	 * --------------*/
	public Controller(Model m) {
		model = m;
		timer = new Timer(DELAY, this);
	}
	public void run() throws IOException, InterruptedException {
		timer.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while ((line = reader.readLine()) != null)
			model.process(line);
	}
	
	public void actionPerformed(ActionEvent e) {
		model.process("TIME_ELAPSED");
	}
	
}