import java.awt.*;
import javax.swing.*;

public class Main {
	static JFrame frame = new JFrame();// PARENT FRAME

	public static void main(String[] args) throws Exception {

		Interfaces i = new Interfaces();
		Interfaces.parentPanel.setLayout(Interfaces.c);	

		Interfaces.parentPanel.add(i.HomePanel(), "home");
		Interfaces.parentPanel.add(i.AdminPanel(), "admin");
		Interfaces.parentPanel.add(i.HistoryPanel(), "histo");
		Interfaces.parentPanel.add(i.DemandePanel(), "demande");

		i.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		i.frame.setSize(1000, 600);
		i.frame.setLayout(new BorderLayout());
		i.frame.add(Interfaces.parentPanel);
		i.frame.setBounds(50,60,1000,600);
		i.frame.setVisible(true);
		i.frame.setTitle("GetDoc");
		i.frame.setResizable(false);
	}

}
