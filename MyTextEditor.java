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
		Container myContainer = getContentPane();
		
		JFrame frame = new JFrame();
		
		//Need a menubar
		menuBar = new JMenuBar();
		
		//menus on the bar
		//file
		file = new JMenu("File");
		
		//Menu items in file
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
	}
	
	public void actionPerformed(ActionEvent event)
	{
		
	}
}