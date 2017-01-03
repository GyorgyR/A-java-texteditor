import java.awt.*;
import javax.swing.*;

public class Help extends JFrame{

	public Help() {
		setTitle("HEEEELP");
		Container myCont = getContentPane();

		JTabbedPane tabs = new JTabbedPane();
		tabs.add(new TextTab(this));

		myCont.add(tabs);

		myCont.setPreferredSize(new Dimension(600,800));
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	public static void main(String[] args) {
		new Help().setVisible(true);
	}
}