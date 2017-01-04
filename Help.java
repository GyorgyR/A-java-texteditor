import java.awt.*;
import javax.swing.*;
import java.io.*;

public class Help extends JFrame{

	public Help() {
		setTitle("HEEEELP");
		Container myCont = getContentPane();

		JTabbedPane tabs = new JTabbedPane();
		tabs.add(new TextTab(this,null,tabs));
		tabs.add(new TextTab(this,new File("Test/test.txt"),tabs));

		JScrollPane scrollPane = new JScrollPane(tabs);

		myCont.add(scrollPane);

		myCont.setPreferredSize(new Dimension(600,800));
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	public static void main(String[] args) {
		new Help().setVisible(true);
	}
}