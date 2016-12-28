import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*******************************************************************************
Author: Gyorgy Rethy
Date: 27/12/2016
--------------------------------------------------------------------------------
This a simple java text editor so I will not forget everything that I learned.
*******************************************************************************/
public class MyTextEditor extends JFrame implements ActionListener
{
	//This is the current file the user is working on
	private File currentFile;
	
	//just a jmenubar
	private JMenuBar menuBar;
	
	//jmenus user can choose
	//TODO options help credits
	private JMenu file, options, help, credits;
	
	//jmenuitems in file
	private JMenuItem open, save, saveas, exit;
	
	//the jtextarea where the input is
	private JTextArea textArea;
	
	
	
	public MyTextEditor()
	{
		setTitle("AWESOME text editor - New File");
		
		Container myContainer = getContentPane();
		myContainer.setLayout(new BorderLayout());
		
		
		//initializing the text area 
		textArea = new JTextArea(32, 80);
		
		//adding in the scroll pane
		JScrollPane scrollPane = new JScrollPane(textArea);
		myContainer.add(scrollPane,BorderLayout.CENTER);
		
		
		//Need a menubar
		menuBar = new JMenuBar();
		
		//menus on the bar
		//file
		file = new JMenu("File");
		menuBar.add(file);
		
		//Menu items in file
		//adding them to file and adding a action listener
		//open
		open = new JMenuItem("Open");
		file.add(open);
		open.addActionListener(this);
		
		//save
		save = new JMenuItem("Save");
		file.add(save);
		save.addActionListener(this);
		
		//save as..
		saveas = new JMenuItem("Save as..");
		file.add(saveas);
		saveas.addActionListener(this);
		
		//exit
		exit = new JMenuItem("Exit");
		file.add(exit);
		exit.addActionListener(this);
		
		//setting the jmenubar
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	public void actionPerformed(ActionEvent event)
	{
	        if(event.getSource() == open)
	                open();
	                
	        if(event.getSource() == save)
	                save();
	                
	        if(event.getSource() == saveas)
	                saveas();
	                
	        if(event.getSource() == exit)
	                this.dispose();
	}
	
	//the function that does save
	public void save()
	{
	        //if this is the first save need to know where to save
	        if (currentFile == null)
	                saveas();
	        else
	        {
	                try(FileWriter writer = new FileWriter(currentFile))
	                { 
	                     textArea.write(writer);
	                }
	                catch (Exception e)
	                {
	              
	                }
	        }
	        
	        
	}
	
	//the function that does save as
	public void saveas()
	{
	        JFileChooser fileBrowser = new JFileChooser();
	        
	                //the return value of the jfilechooser
	                int returnValue = fileBrowser.showSaveDialog(this);
	        
	                if(returnValue == JFileChooser.APPROVE_OPTION)
	                {
	                      try(FileWriter writer = new FileWriter(fileBrowser.getSelectedFile()))
	                        { 
	                                textArea.write(writer);
	                                currentFile = fileBrowser.getSelectedFile();
	                                setTitle("AWESOME text editor - "+currentFile.getName());
	                        }
	                      catch (Exception e)
	                      {
	              
	                      }
	                }
	}
	
	public void open()
	{
                //deleting the things already in the textarea
                textArea.setText("");
                
	        JFileChooser fileBrowser = new JFileChooser();
	        
	        //valami változós dolog amit nem értek de asszem emiatt jelenik meg
	        //áááh értem 
	        //visszatérőérték
	        //és ez megmondja milyen értékei vannak
	        //mint pl approve button
	        int returnValue = fileBrowser.showOpenDialog(this);
	        
	        if(returnValue == JFileChooser.APPROVE_OPTION)
	        {
	                currentFile = fileBrowser.getSelectedFile();
	                
	                //reading the file
	                try
	                {
	                        BufferedReader reader = new BufferedReader(
	                                                        new FileReader(currentFile));
	                
	                        //reading a string then appending it to t5he text area
	                        String line = reader.readLine();
	                        textArea.append(line);
	                
	                        //while there is a next line append it to the textarea
	                        //with a lineseparator
	                        while(line != null)
	                        {
	                                //add lineseparator
	                                textArea.append(System.lineSeparator()); 
	                                //append the line
	                                textArea.append(line);
	                                //read the next line
	                                line = reader.readLine();
	                        }
	                        
	                        
	                        reader.close();
	                        
	                        setTitle("AWESOME text editor - "+currentFile.getName());
	                }
	                catch(Exception e)
	                {
	                
	                }
	        }
	        
	        
	}
	
	public static void main(String[] args)
	{
		new MyTextEditor().setVisible(true);
	}
}
