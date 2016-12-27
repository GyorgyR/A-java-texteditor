import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This a simple java text editor so I will not forget everything that I learned.
*******************************************************************************/
public class MyTextEditor extends JFrame implements ActionListener
{
	//This array holds everything that the user writes
	private String[] input;
	
	private JMenuBar menuBar;
	
	private JMenu file, help, credits;
	
	private JMenuItem open, save, exit;
	
	public MyTextEditor()
	{
		setTitle("AWESOME text editor");
		
		Container myContainer = getContentPane();
		
		JFrame frame = new JFrame();
		//myContainer.add(frame);
		
		//Need a menubar
		menuBar = new JMenuBar();
		
		//menus on the bar
		//file
		file = new JMenu("File");
		menuBar.add(file);
		
		//Menu items in file
		open = new JMenuItem("Open");
		file.add(open);
		save = new JMenuItem("Save");
		file.add(save);
		exit = new JMenuItem("Exit");
		file.add(exit);
		
		frame.setJMenuBar(menuBar);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	public void actionPerformed(ActionEvent event)
	{
		
	}
	public static void main(String[] args)
	{
		new MyTextEditor().setVisible(true);
	}
}